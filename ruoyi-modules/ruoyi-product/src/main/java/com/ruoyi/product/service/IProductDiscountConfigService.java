package com.ruoyi.product.service;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.ProductDiscountConfig;

/**
 * 商品优惠配置Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IProductDiscountConfigService extends IBaseService<ProductDiscountConfig> {
    /**
     * 根据标价匹配配置：priceMin <= price < priceMax
     */
    ProductDiscountConfig matchConfigByPrice(Integer markedPrice);
}
