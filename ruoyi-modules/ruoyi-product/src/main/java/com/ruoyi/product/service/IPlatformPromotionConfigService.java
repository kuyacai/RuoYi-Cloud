package com.ruoyi.product.service;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.PlatformPromotionConfig;

/**
 * 平台促销配置Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IPlatformPromotionConfigService extends IBaseService<PlatformPromotionConfig> {
    /**
     * 根据标价匹配配置：priceMin <= price < priceMax
     */
    PlatformPromotionConfig matchConfigByPrice(Integer markedPrice);
}
