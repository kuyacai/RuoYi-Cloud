package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.RepurchaseCouponProduct;
import com.ruoyi.product.mapper.RepurchaseCouponProductMapper;
import com.ruoyi.product.service.IRepurchaseCouponProductService;

/**
 * 复购券活动商品Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class RepurchaseCouponProductServiceImpl
        extends BaseServiceImpl<RepurchaseCouponProductMapper, RepurchaseCouponProduct>
        implements IRepurchaseCouponProductService {
}
