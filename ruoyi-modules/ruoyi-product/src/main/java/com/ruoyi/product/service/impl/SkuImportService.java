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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.domain.GoodsRevisionImage;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.ItemTask;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.MiaoShouSKU;
import com.ruoyi.product.enums.ImageType;
import com.ruoyi.product.enums.ItemTaskCode;
import com.ruoyi.product.enums.ItemTaskStatus;
import com.ruoyi.product.enums.RevStatus;
import com.ruoyi.product.enums.RevisionType;
import com.ruoyi.product.enums.SkuStatus;
import com.ruoyi.product.service.IGoodsRevisionImageService;
import com.ruoyi.product.service.IGoodsRevisionItemService;
import com.ruoyi.product.service.IGoodsRevisionService;
import com.ruoyi.product.service.IGoodsService;
import com.ruoyi.product.service.IItemTaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SPU导入服务
 */
@Slf4j
@Service
@RequiredArgsConstructor

public class SkuImportService {

    private final PlatformTransactionManager transactionManager;
    private final IGoodsService goodsService;
    private final IGoodsRevisionImageService goodsRevisionImageService;
    private final IGoodsRevisionService goodsRevisionService;
    private final IGoodsRevisionItemService goodsRevisionItemService;
    private final IItemTaskService itemTaskService;

    /**
     * 新方法：处理单条SKU记录
     * 
     * @return ItemProcessResult 处理结果
     */
    public ItemProcessResult processSingleSku(MiaoShouSKU sku, String shopId) {
        // 复用原有的事务管理逻辑
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            /* 1. 基础校验 */
            if (!validateSKUData(sku)) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("数据校验失败");
            }

            /* 2. 检查商品是否存在 */
            Goods goods = goodsService.selectGoodsBySourceId(sku.getSourceId());
            if (goods == null) {
                transactionManager.commit(status);
                return ItemProcessResult.skip("商品未导入");
            }

            /* 3. 检查修订版本 */
            List<GoodsRevision> frozenRevList = goodsRevisionService.listFrozenByGoodsId(goods.getGoodsId());
            if (frozenRevList == null || frozenRevList.isEmpty()) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("无冻结版本");
            }

            List<GoodsRevision> editingRevList = goodsRevisionService.listEditingByGoodsId(goods.getGoodsId());
            if (editingRevList == null || editingRevList.isEmpty()) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("无编辑版本");
            }
            GoodsRevision frozenRev = frozenRevList.get(0);
            GoodsRevision editingRev = editingRevList.get(0);

            /* 4. SKU去重检查 */
            boolean skuExists = goodsRevisionItemService.isBeenImported(
                    editingRev.getRevisionId(), shopId, sku.getProductId(), sku.getSkuId());
            if (skuExists) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("SKU已存在");
            }

            /* 5. 写入数据（复用原有逻辑） */
            insertSkuAndImages(sku, goods, frozenRev, shopId);
            insertSkuAndImages(sku, goods, editingRev, shopId);

            /* 6. 写任务 */
            ItemTask task = buildItemTask(ItemTaskCode.EDIT_SPEC,
                    editingRev.getRevisionId(), ItemTaskStatus.PENDING);
            itemTaskService.insertItemTask(task);

            transactionManager.commit(status);
            return ItemProcessResult.success();

        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("处理SKU失败: productId={}, skuId={}",
                    sku.getProductId(), sku.getSkuId(), e);
            return ItemProcessResult.fail(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AjaxResult importSkuDataForTask(MultipartFile file, String shopId) {
        try {

            // 复用现有逻辑
            return importData(file, shopId, new ArrayList<>());
        } catch (Exception e) {
            log.error("导入SKU数据失败", e);
            throw new RuntimeException("导入失败：" + e.getMessage());
        }

    }

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

    /**
     * 导入CSV数据
     */
    private AjaxResult importData(MultipartFile file, String shopId, List<String> errorMessages) throws Exception {
        int successCount = 0;
        int failureCount = 0;
        int skipCount = 0;

        List<MiaoShouSKU> skuList = parseFile(file);

        for (MiaoShouSKU sku : skuList) {
            ItemProcessResult result = processSingleSku(sku, shopId);
            if (result.isSuccess()) {
                successCount++;
            } else if (result.isSkipped()) {
                skipCount++;
                errorMessages.add("跳过 SKU[" + sku.getSkuId() + "]: " + result.getErrorReason());
            } else {
                failureCount++;
                errorMessages.add("失败 SKU[" + sku.getSkuId() + "]: " + result.getErrorReason());
            }
        }

        return buildImportResult(successCount, failureCount, skipCount, errorMessages);
    }

    /* ====== 2. 私有方法：真正写 SKU + 图片 ====== */
    private void insertSkuAndImages(MiaoShouSKU sku, Goods goods, GoodsRevision goodsRev, String shopId) {
        // 1. goods_revision_item
        GoodsRevisionItem item = buildRevisionItem(sku, goodsRev.getRevisionId(), shopId, goods.getGoodsId());
        goodsRevisionItemService.save(item);

        // 2. 图片去重后写入
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage1(),
                ImageType.MAIN, 1, null);
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage2(),
                ImageType.MAIN, 2, null);
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage3(),
                ImageType.MAIN, 3, null);
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage4(),
                ImageType.MAIN, 4, null);
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage5(),
                ImageType.MAIN, 5, null);

        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage341(),
                ImageType.MAIN34, 1, null);
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage342(),
                ImageType.MAIN34, 2, null);
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage343(),
                ImageType.MAIN34, 3, null);
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage344(),
                ImageType.MAIN34, 4, null);
        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getMainImage345(),
                ImageType.MAIN34, 5, null);

        saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(), sku.getSpecImage(),
                ImageType.SPEC, 1, sku.getSkuId());

        // 3. 详情图
        List<String> detailList = listDetailImages(sku.getDetailImageUrls());
        for (int i = 0; i < detailList.size(); i++) {
            saveImageIfAbsent(goodsRev.getRevisionId(), goods.getGoodsId(),
                    detailList.get(i), ImageType.DETAIL, i + 1, null);
        }
    }

    /* ====== 3. 私有方法：图片去重写入 ====== */
    private void saveImageIfAbsent(String revisionId, String goodsId,
            String url, ImageType imageType, int position, String goodsSkuId) {
        if (StringUtils.isBlank(url)) {
            return;
        }
        // 同 revision + url 唯一即认为已存在
        int img_counter = goodsRevisionImageService
                .countByRevisionIdAndSourceUrl(revisionId, url);
        if (img_counter > 0) {
            return;
        }
        GoodsRevisionImage img = new GoodsRevisionImage();
        img.setImageId(UUID.fastUUID().toString(true));
        img.setRevisionId(revisionId);
        img.setGoodsId(goodsId);
        // TODO 注意，这里是goodsskuId，而GoodsRevisionItem 中则是shopskuid
        img.setGoodsSkuId(goodsSkuId);
        img.setImageType(imageType);
        img.setSourceUrl(url);
        img.setSelfUrl(null);
        img.setLocalUri(null);
        img.setPosition(position);
        goodsRevisionImageService.save(img);
    }

    /* ====== 4. 构造 GoodsRevisionItem ====== */
    private GoodsRevisionItem buildRevisionItem(MiaoShouSKU sku, String revisionId, String shopId, String goodsId) {
        GoodsRevisionItem item = new GoodsRevisionItem();
        item.setItemId(UUID.fastUUID().toString(true));
        item.setRevisionId(revisionId);
        item.setShopId(shopId);
        item.setShopProductId(sku.getProductId());
        item.setShopSkuId(sku.getSkuId());
        // TODO 这里sku code定义的作用待查？？？？
        item.setSkuCode(sku.getSkuId());
        item.setSellerSku(sku.getSellerSku());
        item.setSpec1(sku.getSpec1());
        item.setSpec1Note(sku.getSpec1Note());
        item.setSpec2(sku.getSpec2());
        item.setSpec2Note(sku.getSpec2Note());
        item.setSpec3OrLeadTime(sku.getSpec3OrLeadTime());
        item.setSpec3Note(sku.getSpec3Note());

        item.setInStockQty(sku.getInStockQty() == null ? 0 : sku.getInStockQty());
        item.setFullPrepayQty(sku.getFullPrepayQty() == null ? 0 : sku.getFullPrepayQty());
        item.setShip3dQty(sku.getShip3dQty() == null ? 0 : sku.getShip3dQty());
        item.setShip4dQty(sku.getShip4dQty() == null ? 0 : sku.getShip4dQty());
        item.setShip5dQty(sku.getShip5dQty() == null ? 0 : sku.getShip5dQty());
        item.setShip7dQty(sku.getShip7dQty() == null ? 0 : sku.getShip7dQty());
        item.setShip10dQty(sku.getShip10dQty() == null ? 0 : sku.getShip10dQty());
        item.setShip15dQty(sku.getShip15dQty() == null ? 0 : sku.getShip15dQty());
        item.setShip20dQty(sku.getShip20dQty() == null ? 0 : sku.getShip20dQty());
        item.setShip25dQty(sku.getShip25dQty() == null ? 0 : sku.getShip25dQty());
        item.setShip30dQty(sku.getShip30dQty() == null ? 0 : sku.getShip30dQty());
        item.setShip35dQty(sku.getShip35dQty() == null ? 0 : sku.getShip35dQty());
        item.setShip45dQty(sku.getShip45dQty() == null ? 0 : sku.getShip45dQty());

        if (log.isDebugEnabled()) {
            log.debug("MiaoShouSKU price is :{}", sku.getPrice());
            log.debug("MiaoShouSKU lowestPrice is :{}", sku.getLowestPrice());
            log.debug("MiaoShouSKU highestPrice is :{}", sku.getHighestPrice());
        }

        if (log.isDebugEnabled()) {
            log.debug("GoodsRevisionItem price is :{}", item.getOrignialPrice());
            log.debug("GoodsRevisionItem lowestPrice is :{}", item.getLowestPrice());
            log.debug("GoodsRevisionItem highestPrice is :{}", item.getHighestPrice());
        }
        item.setSkuStatus(SkuStatus.of(sku.getSkuStatus()));

        item.setBarcode(sku.getBarcode());
        return item;
    }

    /**
     * 统一入口：CSV 或 Excel → List<MiaoShouSPU>
     * 文件编码自动探测（仅 CSV 需要）
     */
    private List<MiaoShouSKU> parseFile(MultipartFile file) throws Exception {
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
            ExcelUtil<MiaoShouSKU> util = new ExcelUtil<>(MiaoShouSKU.class);
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
    private boolean validateSKUData(MiaoShouSKU sku) {

        if (StringUtils.isEmpty(sku.getSourceTitle())) {
            return false;
        }

        if (StringUtils.isEmpty(sku.getSourceId())) {
            return false;
        }
        if (StringUtils.isEmpty(sku.getSourceUrl())) {
            return false;
        }
        if (StringUtils.isEmpty(sku.getProductId())) {
            return false;
        }
        if (StringUtils.isEmpty(sku.getSkuId())) {
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
    private List<String> listMainImages(MiaoShouSKU sku) throws Exception {
        List<String> urls = new ArrayList<>(5);
        for (int i = 1; i <= 5; i++) {
            Method m = MiaoShouSKU.class.getDeclaredMethod("getMainImage" + i);
            urls.add((String) m.invoke(sku));
        }
        return urls; // 1..5
    }

    /** 反射提取 3:4 主图 URL，按 position 1~5 返回 */
    private List<String> listMain34Images(MiaoShouSKU sku) throws Exception {
        List<String> urls = new ArrayList<>(5);
        String[] fields = { "getMainImage341", "getMainImage342", "getMainImage343", "getMainImage344",
                "getMainImage345" };
        for (String f : fields) {
            Method m = MiaoShouSKU.class.getDeclaredMethod(f);
            urls.add((String) m.invoke(sku));
        }
        return urls; // 1..5
    }

    /** 解析详情图字符串 -> List<String> */
    private List<String> listDetailImages(String urls) {
        if (StringUtils.isBlank(urls)) {
            return Collections.emptyList();
        }
        return Arrays.stream(urls.split(","))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
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

    private GoodsRevision buildRevision(String revisionId, String goodsId,
            RevStatus status) {
        GoodsRevision r = new GoodsRevision();
        r.setRevisionId(revisionId);
        r.setGoodsId(goodsId);
        r.setRevStatus(status);
        r.setRevisionType(RevisionType.MANUAL);
        return r;
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
