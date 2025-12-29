package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.ProductDiscountConfig;
import com.ruoyi.product.enums.ConfigStatus;
import com.ruoyi.product.mapper.ProductDiscountConfigMapper;
import com.ruoyi.product.service.IProductDiscountConfigService;

/**
 * 商品优惠配置Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ProductDiscountConfigServiceImpl extends
        BaseServiceImpl<ProductDiscountConfigMapper, ProductDiscountConfig> implements IProductDiscountConfigService {
    @Override
    public ProductDiscountConfig matchConfigByPrice(Integer markedPrice) {
        if (markedPrice == null)
            return null;
        return this.getOne(new LambdaQueryWrapper<ProductDiscountConfig>()
                .le(ProductDiscountConfig::getPriceMin, markedPrice)
                .gt(ProductDiscountConfig::getPriceMax, markedPrice)
                .eq(ProductDiscountConfig::getConfigStatus, ConfigStatus.ENABLE)
                .last("LIMIT 1"));
    }
}
