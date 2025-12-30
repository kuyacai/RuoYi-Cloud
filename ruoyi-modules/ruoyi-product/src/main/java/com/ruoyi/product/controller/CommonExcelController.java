package com.ruoyi.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.core.annotation.ExcelBusiness;
import com.ruoyi.product.core.excel.ExcelDtoRegistry;
import com.ruoyi.product.feign.FileServiceClient;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.product.service.IImportService;
import com.ruoyi.product.service.excel.ExcelDataHandler;
import com.ruoyi.product.utils.EnhancedExcelUtil;
import com.ruoyi.system.api.domain.SysFile;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 数据导入控制器
 */
@RestController
@RequestMapping("/excel")
public class CommonExcelController extends BaseController {

    @Autowired
    private IImportService importService;

    @Autowired
    private FileServiceClient fileServiceClient;

    @Autowired
    private IAsyncTaskService asyncTaskService;

    @Autowired
    private ExcelDtoRegistry registry;

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
        logger.debug("shopId is {}", shopId);
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
     * 
     * @GetMapping("/template/spu")
     * public void downloadSpuTemplate(HttpServletResponse response) {
     * downloadTemplate(response, "spu", "spu_template.xlsx");
     * }
     * 
     * 
     * @GetMapping("/template/sku")
     * public void downloadSkuTemplate(HttpServletResponse response) {
     * downloadTemplate(response, "sku", "sku_template.xlsx");
     * }
     * 
     * private void downloadTemplate(HttpServletResponse response, String
     * templateType, String templateName) {
     * try {
     * byte[] data = importService.getTemplate(templateType);
     * 
     * // 设置响应头
     * setExcelResponseHeader(response, templateName);
     * 
     * // 输出文件流
     * ServletOutputStream outputStream = response.getOutputStream();
     * outputStream.write(data);
     * outputStream.flush();
     * outputStream.close();
     * 
     * } catch (Exception e) {
     * logger.error(templateName, e);
     * setErrorResponse(response, "下载模板失败: " + e.getMessage());
     * }
     * }
     * 
     * /**
     * 通用的Excel响应头设置
     * 
     * private void setExcelResponseHeader(HttpServletResponse response, String
     * filename) {
     * response.reset();
     * response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
     * response.setCharacterEncoding("utf-8");
     * 
     * // 处理中文文件名
     * String encodedFilename;
     * try {
     * encodedFilename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
     * } catch (Exception e) {
     * encodedFilename = filename;
     * }
     * 
     * response.setHeader("Content-Disposition",
     * "attachment;filename=" + encodedFilename);
     * response.setHeader("Pragma", "no-cache");
     * response.setHeader("Cache-Control", "no-cache");
     * }
     * 
     * /**
     * 设置错误响应
     * 
     * private void setErrorResponse(HttpServletResponse response, String
     * errorMessage) {
     * try {
     * response.reset();
     * response.setContentType("application/json");
     * response.setCharacterEncoding("utf-8");
     * response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
     * } catch (IOException e) {
     * logger.error("设置错误响应失败", e);
     * }
     * }
     */
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

    /**
     * 通用模板下载
     * URL 示例: /excel/common/template/product
     */
    @PostMapping("/template/{businessKey}")
    public void downloadTemplate(@PathVariable String businessKey, HttpServletResponse response) {
        logger.debug("downloadTemplate:{}", businessKey);
        Class<?> clazz = registry.getDtoClass(businessKey);
        if (clazz == null) {
            logger.debug("Cannot find the class by the key:{}", businessKey);
            throw new ServiceException("未找到对应的业务导出配置：" + businessKey);
        }
        logger.debug("Get the class :", clazz.getSimpleName());

        ExcelBusiness meta = clazz.getAnnotation(ExcelBusiness.class);
        if (meta == null) {
            logger.error("Class {} 没有 @ExcelBusiness 注解", clazz.getName());
            throw new ServiceException("业务配置错误：缺少@ExcelBusiness注解");
        }
        logger.debug("Template name from annotation: {}", meta.templateName());
        EnhancedExcelUtil<?> util = new EnhancedExcelUtil<>(clazz);
        try {
            // 添加响应头设置的日志
            logger.debug("Setting response headers...");
            logger.debug("Content-Type: {}", response.getContentType());
            logger.debug("CharacterEncoding: {}", response.getCharacterEncoding());

            // 使用注解中定义的模板名
            util.importTemplateExcel(response, meta.templateName());
            // List list = new ArrayList();
            // util.exportExcel(response, list, "用户数据");

            // List<SysUser> list = new ArrayList<>();
            // ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
            // util.exportExcel(response, list, "用户数据");

            // 检查输出流状态
            logger.debug("Excel写入完成");
            logger.debug("Response output stream: {}", response.getOutputStream());
            logger.debug("Response committed: {}", response.isCommitted());

        } catch (Exception e) {
            logger.error("生成Excel模板失败", e);
            throw new ServiceException("生成模板失败：" + e.getMessage());
        }
    }

    @PostMapping("/export/{businessKey}")
    public void export(@PathVariable String businessKey,
            @RequestBody Map<String, Object> params,
            HttpServletResponse response) {

        // 1. 从注册中心获取 DTO 类型
        Class<?> clazz = registry.getDtoClass(businessKey);
        ExcelBusiness meta = clazz.getAnnotation(ExcelBusiness.class);

        // 2. 从 Spring 容器中动态获取对应的 Handler Bean
        ExcelDataHandler<?> handler = SpringUtils.getBean(meta.handlerBean());

        // 3. 获取数据
        List<?> data = handler.getExportData(params);

        // 4. 执行导出（使用你重写的 EnhancedExcelUtil）
        EnhancedExcelUtil util = new EnhancedExcelUtil(clazz);
        util.exportExcel(response, data, meta.exportName());
    }
}