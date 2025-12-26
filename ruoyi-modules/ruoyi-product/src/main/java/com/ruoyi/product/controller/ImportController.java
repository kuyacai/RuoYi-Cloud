package com.ruoyi.product.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.constant.ItemTaskCode;
import com.ruoyi.product.service.IImportService;
import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.system.api.domain.SysFile;
import com.ruoyi.product.feign.FileServiceClient;
import com.ruoyi.product.service.IAsyncTaskService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.product.constant.MQConstant;
import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据导入控制器
 */
@RestController
@RequestMapping("/import")
public class ImportController extends BaseController {

    @Autowired
    private IImportService importService;

    @Autowired
    private FileServiceClient fileServiceClient;

    @Autowired
    private IAsyncTaskService asyncTaskService;

    /**
     * 导入SPU数据
     */
    @RequiresPermissions("product:import:spu")
    @Log(title = AsyncTaskCode.SPU_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/spu")
    public AjaxResult importSpu(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId) {
        try {
            // 如果有shopId，可以在这里处理
            if (StringUtils.isNotEmpty(shopId)) {
                logger.info("导入SPU数据，商店ID: {}", shopId);
                // 可以将shopId传递给service层，或者在这里做一些校验
            }
            // return importService.importSpuData(file, shopId);
            return createImportTask(file, shopId, null, AsyncTaskCode.SPU_IMPORT);
        } catch (Exception e) {
            logger.error("导入SPU数据失败", e);
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 导入SKU数据
     */
    @RequiresPermissions("product:import:sku")
    @Log(title = AsyncTaskCode.SKU_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/sku")
    public AjaxResult importSku(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId) {
        return createImportTask(file, shopId, null, AsyncTaskCode.SKU_IMPORT);
    }

    @Log(title = AsyncTaskCode.PRICE_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/price")
    public AjaxResult importPrice(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId) {
        return createImportTask(file, shopId, null, AsyncTaskCode.PRICE_IMPORT);
    }

    @Log(title = AsyncTaskCode.MODIFY_TITLE_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/title")
    public AjaxResult importTitle(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId) {
        return createImportTask(file, shopId, null, AsyncTaskCode.MODIFY_TITLE_IMPORT);
    }

    @Log(title = AsyncTaskCode.SINGLE_DISCOUNT_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/single_discount")
    public AjaxResult importSingleDiscount(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId,
            @RequestParam(value = "activityId", required = false) String activityId) {
        return createImportTask(file, shopId, activityId, AsyncTaskCode.SINGLE_DISCOUNT_IMPORT);
    }

    @Log(title = AsyncTaskCode.PRODUCT_DISCOUNT_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/product-discount")
    public AjaxResult importProductDiscount(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId,
            @RequestParam(value = "activityId", required = false) String activityId) {
        return createImportTask(file, shopId, activityId, AsyncTaskCode.PRODUCT_DISCOUNT_IMPORT);
    }

    @Log(title = AsyncTaskCode.NEW_USER_GIFT_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/new-user")
    public AjaxResult importNewUserGift(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId,
            @RequestParam(value = "activityId", required = false) String activityId) {
        return createImportTask(file, shopId, activityId, AsyncTaskCode.NEW_USER_GIFT_IMPORT);
    }

    @Log(title = AsyncTaskCode.REPURCHASE_DISCOUNT_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/repurchase")
    public AjaxResult importRepurchase(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId,
            @RequestParam(value = "activityId", required = false) String activityId) {
        return createImportTask(file, shopId, activityId, AsyncTaskCode.REPURCHASE_DISCOUNT_IMPORT);
    }

    @Log(title = AsyncTaskCode.PLATFORM_PROMOTION_IMPORT_LABEL, businessType = BusinessType.IMPORT)
    @PostMapping("/promotion")
    public AjaxResult importPlatformPromotion(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "shopId", required = false) String shopId,
            @RequestParam(value = "activityId", required = false) String activityId) {
        return createImportTask(file, shopId, activityId, AsyncTaskCode.PLATFORM_PROMOTION_IMPORT);
    }

    private AjaxResult createImportTask(MultipartFile file, String shopId, String activityId, AsyncTaskCode taskCode) {

        try {
            if (StringUtils.isNotEmpty(shopId)) {
                logger.info("{}，商店ID: {}", taskCode.getLabel(), shopId);
            }
            // 1. 先上传文件到 ruoyi-file
            R<SysFile> fileRsp = fileServiceClient.uploadFile(file);
            if (fileRsp.getCode() != 200) {
                return AjaxResult.error("文件上传失败");
            }
            SysFile sysFile = fileRsp.getData();
            String fileUrl = sysFile.getUrl();
            String fileName = file.getOriginalFilename();

            // 2. 创建异步任务
            Map<String, Object> ext = new HashMap<>();
            ext.put("fileUrl", fileUrl);
            ext.put("shopId", shopId);
            if (StringUtils.isNotEmpty(activityId))
                ext.put("activityId", activityId);

            String taskId = asyncTaskService.createTask(
                    taskCode,
                    shopId,
                    fileName, // 传入文件名
                    fileUrl, // 传入文件URL
                    ext); // 传入扩展参数
            return AjaxResult.success(taskId);
        } catch (Exception e) {
            logger.error(taskCode.getLabel(), e);
            return AjaxResult.error("导入失败：" + e.getMessage());
        }

    }

    /**
     * 下载SPU模板
     */
    @GetMapping("/template/spu")
    public void downloadSpuTemplate(HttpServletResponse response) {
        downloadTemplate(response, "spu", "spu_template.xlsx");
    }

    /**
     * 下载SKU模板
     */
    @GetMapping("/template/sku")
    public void downloadSkuTemplate(HttpServletResponse response) {
        downloadTemplate(response, "sku", "sku_template.xlsx");
    }

    private void downloadTemplate(HttpServletResponse response, String templateType, String templateName) {
        try {
            byte[] data = importService.getTemplate(templateType);

            // 设置响应头
            setExcelResponseHeader(response, templateName);

            // 输出文件流
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            logger.error(templateName, e);
            setErrorResponse(response, "下载模板失败: " + e.getMessage());
        }
    }

    /**
     * 通用的Excel响应头设置
     */
    private void setExcelResponseHeader(HttpServletResponse response, String filename) {
        response.reset();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        // 处理中文文件名
        String encodedFilename;
        try {
            encodedFilename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        } catch (Exception e) {
            encodedFilename = filename;
        }

        response.setHeader("Content-Disposition",
                "attachment;filename=" + encodedFilename);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
    }

    /**
     * 设置错误响应
     */
    private void setErrorResponse(HttpServletResponse response, String errorMessage) {
        try {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
        } catch (IOException e) {
            logger.error("设置错误响应失败", e);
        }
    }

    /**
     * 检查导入状态
     */
    @GetMapping("/status/{type}")
    public AjaxResult checkImportStatus(@PathVariable String type) {
        try {
            // 这里可以添加检查导入状态的逻辑
            // 例如：检查是否有正在进行的导入任务
            return AjaxResult.success("导入服务正常");
        } catch (Exception e) {
            logger.error("检查导入状态失败", e);
            return AjaxResult.error("检查导入状态失败: " + e.getMessage());
        }
    }

    /**
     * 获取支持的导入类型
     */
    @GetMapping("/types")
    public AjaxResult getImportTypes() {
        try {
            // 这里可以返回支持的导入类型列表
            return AjaxResult.success("获取导入类型成功", new String[] { "spu", "sku" });
        } catch (Exception e) {
            logger.error("获取导入类型失败", e);
            return AjaxResult.error("获取导入类型失败: " + e.getMessage());
        }
    }
}