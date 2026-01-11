package com.ruoyi.product.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.CharsetDetectUtil;
import com.ruoyi.common.core.utils.file.FileUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.domain.GoodsRevisionImage;
import com.ruoyi.product.domain.GoodsRevisionSpu;
import com.ruoyi.product.domain.ItemTask;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.MiaoShouSPU;
import com.ruoyi.product.enums.CopyStatus;
import com.ruoyi.product.enums.GoodsStatus;
import com.ruoyi.product.enums.ImageType;
import com.ruoyi.product.enums.ItemTaskCode;
import com.ruoyi.product.enums.ItemTaskStatus;
import com.ruoyi.product.enums.RevStatus;
import com.ruoyi.product.enums.ReviewStatus;
import com.ruoyi.product.enums.RevisionType;
import com.ruoyi.product.service.IGoodsRevisionImageService;
import com.ruoyi.product.service.IGoodsRevisionService;
import com.ruoyi.product.service.IGoodsRevisionSpuService;
import com.ruoyi.product.service.IGoodsService;
import com.ruoyi.product.service.IItemTaskService;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SPU导入服务
 */
@Slf4j
@Service
@RequiredArgsConstructor

public class SpuImportService {

    private final PlatformTransactionManager transactionManager;
    private final IGoodsService goodsService;
    private final IGoodsRevisionImageService goodsRevisionImageService;
    private final IGoodsRevisionSpuService goodsRevisionSpuService;
    private final IGoodsRevisionService goodsRevisionService;
    private final IItemTaskService itemTaskService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AjaxResult importData(MultipartFile file, String shopId) {
        List<String> errorMessages = new ArrayList<>();

        try {
            String filename = file.getOriginalFilename();
            String suffix = FileUtils.getFileSuffix(filename);

            if (StringUtils.isNotEmpty(suffix)) {
                suffix = suffix.toLowerCase();
                if (".csv".equals(suffix) || ".xlsx".equals(suffix) || ".xls".equals(suffix)) {
                    return importData(file, shopId, errorMessages);
                } else {
                    return AjaxResult.error("不支持的文件格式: " + suffix);
                }
            } else {
                return AjaxResult.error("无法识别文件格式");
            }

        } catch (Exception e) {
            log.error("导入SPU数据失败", e);
            throw new RuntimeException("导入失败：" + e.getMessage());
        }
    }

    public ItemProcessResult processSingleSpu(MiaoShouSPU spu, String shopId) {
        /* 1. 单 SPU 事务 */
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            /* 2. 基础校验 */
            if (!validateSPUData(spu)) {
                transactionManager.rollback(status);
                return ItemProcessResult.fail("商品ID: " + spu.getProductId() + "数据校验失败");
            }

            /* 3. 重复性检查（sourceId 维度） */
            if (goodsService.existsBySourceId(spu.getSourceId())) {
                transactionManager.commit(status); // 不算失败，直接跳过
                return ItemProcessResult.skip("商品ID: " + spu.getProductId() + "已经存在");
            }

            /* 4. 构建 goods */
            String goodsId = UUID.fastUUID().toString(true); // true = 去掉横杠; // hutool 工具，无 "-" 可选
            Goods goods = buildGoods(spu, goodsId, shopId);
            goodsService.save(goods);

            /* 5. 构建 V1/V2 版本 */
            String v1Id = UUID.fastUUID().toString(true); // true = 去掉横杠;
            String v2Id = UUID.fastUUID().toString(true); // true = 去掉横杠;
            GoodsRevision v1 = buildRevision(v1Id, goodsId, RevStatus.FROZEN);
            GoodsRevision v2 = buildRevision(v2Id, goodsId, RevStatus.EDITING);
            goodsRevisionService.save(v1);
            goodsRevisionService.save(v2);

            /* 6. 版本对应的 SPU 数据（V1=V2=原始数据） */
            GoodsRevisionSpu spuV1 = buildRevisionSpu(v1Id, spu);
            GoodsRevisionSpu spuV2 = buildRevisionSpu(v2Id, spu);
            goodsRevisionSpuService.save(spuV1);
            goodsRevisionSpuService.save(spuV2);

            /* 7. 图片：主图1:1 / 3:4 / 详情图 全部批量插入 */
            saveRevisionImages(v1Id, goodsId, listMainImages(spu), ImageType.MAIN);
            saveRevisionImages(v1Id, goodsId, listMain34Images(spu), ImageType.MAIN34);
            saveRevisionImages(v1Id, goodsId, spu.getDetailImageUrlList(), ImageType.DETAIL);

            /* 8. 创建待处理任务记录 */
            // 修改标题
            ItemTask task_title = buildItemTask(ItemTaskCode.EDIT_TITLE, v2Id,
                    ItemTaskStatus.PENDING);
            boolean a = itemTaskService.insertItemTask(task_title);
            log.debug(v2Id);
            log.debug("插入任务结构:" + a);
            // 修改图片
            ItemTask task_img = buildItemTask(ItemTaskCode.EDIT_IMAGE, v2Id,
                    ItemTaskStatus.PENDING);
            itemTaskService.insertItemTask(task_img);
            // 修改价格
            ItemTask task_price = buildItemTask(ItemTaskCode.EDIT_PRICE, v2Id,
                    ItemTaskStatus.PENDING);
            itemTaskService.insertItemTask(task_price);
            // 修改视频
            ItemTask task_video = buildItemTask(ItemTaskCode.EDIT_VIDEO, v2Id,
                    ItemTaskStatus.PENDING);
            itemTaskService.insertItemTask(task_video);

            /* 8. 成功提交 */
            transactionManager.commit(status);
            return ItemProcessResult.success();
        } catch (Exception e) {
            log.error("SPU 导入异常: {}", spu.getProductId(), e);
            transactionManager.rollback(status);
            return ItemProcessResult.fail(e.getMessage());
        }
    }

    /**
     * 导入CSV数据
     */
    private AjaxResult importData(MultipartFile file, String shopId, List<String> errorMessages) throws Exception {
        int successCount = 0;
        int failureCount = 0;
        int skipCount = 0;

        List<MiaoShouSPU> spuList = parseFile(file);

        for (MiaoShouSPU spu : spuList) {
            ItemProcessResult result = processSingleSpu(spu, shopId);
            if (result.isSuccess()) {
                successCount++;
            } else if (result.isSkipped()) {
                skipCount++;
                errorMessages.add(result.getErrorReason());
            } else {
                failureCount++;
                errorMessages.add(result.getErrorReason());
            }
        }

        return buildImportResult(successCount, failureCount, skipCount, errorMessages);
    }

    /**
     * 统一入口：CSV 或 Excel → List<MiaoShouSPU>
     * 文件编码自动探测（仅 CSV 需要）
     */
    private List<MiaoShouSPU> parseFile(MultipartFile file) throws Exception {
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        try (InputStream in = file.getInputStream()) {
            InputStream excelIn;
            if (originalName.toLowerCase().endsWith(".csv")) {
                // CSV：先探测编码 → 转临时 Excel
                excelIn = csvToExcel(in);
            } else {
                // Excel：直接丢给 POI
                excelIn = in;
            }

            // 统一用 RuoYi ExcelUtil 解析
            EnhancedExcelUtil<MiaoShouSPU> util = new EnhancedExcelUtil<>(MiaoShouSPU.class);
            return util.importExcel(excelIn, 0); // 0 表示表头只占一行
        }
    }

    /**
     * CSV → 临时 Excel（单 sheet）
     * 内部自动探测编码
     */
    private InputStream csvToExcel(InputStream csvIn) throws IOException {
        // 需要标记流，以便多次读取（一次探测，一次正式读）
        BufferedInputStream bis = new BufferedInputStream(csvIn);
        bis.mark(64 * 1024); // 64 KB 足够探测
        Charset charset = CharsetDetectUtil.detect(bis);
        bis.reset(); // 回到头部

        try (BufferedReader br = new BufferedReader(new InputStreamReader(bis, charset));
                ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            Workbook wb = new SXSSFWorkbook(500);
            Sheet sheet = wb.createSheet("Sheet1");
            String line;
            int rowNum = 0;
            while ((line = br.readLine()) != null) {
                Row row = sheet.createRow(rowNum++);
                // 简单拆列，支持引号内逗号可再优化
                String[] cells = line.split(",");
                for (int i = 0; i < cells.length; i++) {
                    row.createCell(i).setCellValue(cells[i]);
                }
            }
            wb.write(bos);
            wb.close();
            return new ByteArrayInputStream(bos.toByteArray());
        }
    }

    /**
     * 校验商品数据
     */
    private boolean validateSPUData(MiaoShouSPU spu) {

        if (StringUtils.isEmpty(spu.getSourceTitle())) {
            return false;
        }

        if (StringUtils.isEmpty(spu.getMigrateSource())) {
            return false;
        }
        if (StringUtils.isEmpty(spu.getSourceUrl())) {
            return false;
        }

        // 可以添加更多校验逻辑
        return true;
    }

    /**
     * 构建导入结果
     */
    private AjaxResult buildImportResult(int successCount, int failureCount, int skipCount,
            List<String> errorMessages) {
        Map<String, Object> result = new HashMap<>();
        result.put("total", successCount + failureCount + skipCount);
        result.put("success", successCount);
        result.put("failure", failureCount);
        result.put("skip", skipCount);
        result.put("errors", errorMessages);

        if (failureCount > 0) {
            return AjaxResult.success("导入完成，但有部分数据失败", result);
        } else {
            return AjaxResult.success("导入成功", result);
        }
    }

    /** 反射提取 1:1 主图 URL，按 position 1~5 返回 */
    private List<String> listMainImages(MiaoShouSPU spu) throws Exception {
        List<String> urls = new ArrayList<>(5);
        for (int i = 1; i <= 5; i++) {
            Method m = MiaoShouSPU.class.getDeclaredMethod("getMainImage" + i);
            urls.add((String) m.invoke(spu));
        }
        return urls; // 1..5
    }

    /** 反射提取 3:4 主图 URL，按 position 1~5 返回 */
    private List<String> listMain34Images(MiaoShouSPU spu) throws Exception {
        List<String> urls = new ArrayList<>(5);
        String[] fields = { "getMainImage341", "getMainImage342", "getMainImage343", "getMainImage344",
                "getMainImage345" };
        for (String f : fields) {
            Method m = MiaoShouSPU.class.getDeclaredMethod(f);
            urls.add((String) m.invoke(spu));
        }
        return urls; // 1..5
    }

    /** 批量构建 GoodsRevisionImage 并入库 */
    private void saveRevisionImages(String revisionId, String goodsId,
            List<String> urls, ImageType imageType) {
        if (CollectionUtils.isEmpty(urls))
            return;
        int pos = 1;
        for (String url : urls) {
            if (StringUtils.isBlank(url)) {
                pos++;
                continue;
            }
            GoodsRevisionImage img = new GoodsRevisionImage();
            img.setImageId(UUID.randomUUID().toString().replace("-", ""));
            img.setRevisionId(revisionId);
            img.setGoodsId(goodsId);
            img.setGoodsSkuId(null);
            img.setImageType(imageType);
            img.setSourceUrl(url);
            img.setSelfUrl(null);
            img.setLocalUri(null);
            img.setPosition(pos++);
            goodsRevisionImageService.save(img);
        }
    }

    private Goods buildGoods(MiaoShouSPU spu, String goodsId, String shopId) {
        Goods g = new Goods();
        g.setGoodsId(goodsId);
        g.setMigrateSource(spu.getMigrateSource());
        g.setSourceShopName(spu.getSourceShopName());
        g.setSourceId(spu.getSourceId());
        g.setSourceTitle(spu.getSourceTitle());
        g.setSourceUrl(spu.getSourceUrl());
        if (log.isDebugEnabled()) {
            log.debug("SourceCategory is {}", spu.getSourceCategory());
            log.debug("GoodsStatus is {}", spu.getStatus());
        }
        g.setSourceCategory(spu.getSourceCategory());
        g.setCustomerPhone(spu.getCustomerPhone());
        g.setSourceItemNo(spu.getItemNo());
        g.setBrand(spu.getBrand());
        g.setShopProductId(spu.getProductId());
        if (StringUtils.isNotEmpty(shopId)) {
            g.setShopId(shopId); // 全局变量
        }
        g.setGoodsStatus(GoodsStatus.of(spu.getStatus()));
        return g;
    }

    private GoodsRevision buildRevision(String revisionId, String goodsId,
            RevStatus status) {
        GoodsRevision r = new GoodsRevision();
        r.setRevisionId(revisionId);
        r.setGoodsId(goodsId);
        r.setRevStatus(status);
        r.setRevisionType(RevisionType.MANUAL);
        return r;
    }

    private GoodsRevisionSpu buildRevisionSpu(String revisionId, MiaoShouSPU spu) {
        GoodsRevisionSpu s = new GoodsRevisionSpu();
        s.setRevisionId(revisionId);
        s.setTitle(spu.getSourceTitle());
        s.setGuideShortTitle(spu.getGuideShortTitle());
        s.setRecommendation(spu.getRecommendation());
        s.setFreightTemplate(spu.getFreightTemplate());
        s.setAttributes(spu.getAttributes());
        s.setSizeChartSizeTitles(spu.getSizeChartSizeTitles());
        s.setSizeChartTemplateName(spu.getSizeChartTemplateName());
        s.setSales(spu.getSales() == null ? 0 : spu.getSales());
        s.setCopyStatus(CopyStatus.of(spu.getCopyStatus()));
        s.setCopyErrorReason(spu.getCopyErrorReason());
        s.setReviewStatus(ReviewStatus.of(spu.getReviewStatus()));
        s.setShippingMode(spu.getShippingMode());
        s.setInStockShipTime(spu.getInStockShipTime());
        s.setPresaleShipTime(spu.getPresaleShipTime());
        return s;
    }

    private ItemTask buildItemTask(ItemTaskCode taskCode, String bizId, ItemTaskStatus taskStatus) {
        ItemTask task = new ItemTask();
        task.setTaskId(UUID.fastUUID().toString(true));
        task.setTaskCode(taskCode);
        task.setBizId(bizId);
        task.setTaskStatus(taskStatus);
        return task;
    }
}