package com.ruoyi.product.service;

import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 数据导入服务接口
 */
public interface IImportService {
    
    /**
     * 导入SPU数据
     */
    AjaxResult importSpuData(MultipartFile file, String shopId);
    
    /**
     * 导入SKU数据
     */
    AjaxResult importSkuData(MultipartFile file, String shopId);
    
    /**
     * 检查导入文件格式
     */
    Map<String, Object> checkFileFormat(MultipartFile file, String importType);
    
    /**
     * 获取导入模板
     */
    byte[] getTemplate(String type);
}