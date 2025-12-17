package com.ruoyi.product.service.impl;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.product.service.IImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据导入服务实现
 */
@Service
public class ImportServiceImpl implements IImportService {
    
    private static final Logger log = LoggerFactory.getLogger(ImportServiceImpl.class);
    
    // 允许的文件类型
    private static final String[] ALLOWED_EXTENSIONS = {"xlsx", "xls", "csv"};
    // 允许的最大文件大小（10MB）
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    
    private final SpuImportService spuImportService;
    private final SkuImportService skuImportService;
    
    public ImportServiceImpl(SpuImportService spuImportService, 
                            SkuImportService skuImportService) {
        this.spuImportService = spuImportService;
        this.skuImportService = skuImportService;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importSpuData(MultipartFile file, String shopId) {
        try {
            // 检查文件
            Map<String, Object> checkResult = checkFileFormat(file);
            if (!Boolean.TRUE.equals(checkResult.get("success"))) {
                return AjaxResult.error(checkResult.get("message").toString());
            }
            
            // 执行SPU导入逻辑
            return spuImportService.importData(file, shopId);
        } catch (Exception e) {
            log.error("导入SPU数据失败", e);
            throw new RuntimeException("导入失败：" + e.getMessage());
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importSkuData(MultipartFile file, String shopId) {
        try {
            // 检查文件
            Map<String, Object> checkResult = checkFileFormat(file);
            if (!Boolean.TRUE.equals(checkResult.get("success"))) {
                return AjaxResult.error(checkResult.get("message").toString());
            }
            
            // 执行SKU导入逻辑
            return skuImportService.importData(file, shopId);
        } catch (Exception e) {
            log.error("导入SKU数据失败", e);
            throw new RuntimeException("导入失败：" + e.getMessage());
        }
    }
    
    /**
     * 检查文件格式
     */
    public Map<String, Object> checkFileFormat(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        
        if (file == null || file.isEmpty()) {
            result.put("success", false);
            result.put("message", "上传文件不能为空");
            return result;
        }
        
        // 检查文件大小
        long size = file.getSize();
        if (size > MAX_FILE_SIZE) {
            result.put("success", false);
            result.put("message", "文件大小不能超过10MB");
            return result;
        }
        
        // 检查文件扩展名
        String filename = file.getOriginalFilename();
        
        // 使用FileUtils的新方法检查文件扩展名
        if (!FileUtils.isAllowedExtension(filename, ALLOWED_EXTENSIONS)) {
            result.put("success", false);
            result.put("message", "只支持xlsx、xls和csv格式的文件");
            return result;
        }
        
        // 获取文件后缀（带点）
        String suffix = FileUtils.getFileSuffix(filename);
        result.put("fileSuffix", suffix);
        result.put("fileName", FileUtils.getFileNameWithoutSuffix(filename));
        result.put("fileSize", size);
        result.put("success", true);
        result.put("message", "文件格式正确");
        
        return result;
    }
    
    /**
     * 根据文件后缀选择解析方式
     */
    private String getParserTypeByFilename(String filename) {
        String suffix = FileUtils.getFileSuffix(filename);
        
        if (StringUtils.isNotEmpty(suffix)) {
            suffix = suffix.toLowerCase();
            
            if (suffix.equals(".csv")) {
                return "CSV";
            } else if (suffix.equals(".xlsx") || suffix.equals(".xls")) {
                return "EXCEL";
            }
        }
        
        return "UNKNOWN";
    }
    
    @Override
    public Map<String, Object> checkFileFormat(MultipartFile file, String importType) {
        Map<String, Object> result = checkFileFormat(file);
        result.forEach((k, v) -> log.debug("{} : {}", k, v));
        if (Boolean.TRUE.equals(result.get("success"))) {
            // 可以根据importType添加额外的校验逻辑
            result.put("importType", importType);
            result.put("parserType", getParserTypeByFilename(file.getOriginalFilename()));
        }
        
        return result;
    }
    
    @Override
    public byte[] getTemplate(String type) {
        try {
            // 根据类型加载不同的模板文件
            String templateFileName;
            if ("spu".equalsIgnoreCase(type)) {
                templateFileName = "templates/spu_template.xlsx";
            } else if ("sku".equalsIgnoreCase(type)) {
                templateFileName = "templates/sku_template.xlsx";
            } else {
                throw new IllegalArgumentException("不支持的模板类型: " + type);
            }
            
            // 从resources目录读取模板文件
            ClassPathResource resource = new ClassPathResource(templateFileName);
            return FileCopyUtils.copyToByteArray(resource.getInputStream());
            
        } catch (IOException e) {
            log.error("读取模板文件失败", e);
            throw new RuntimeException("读取模板文件失败: " + e.getMessage());
        }
    }
}