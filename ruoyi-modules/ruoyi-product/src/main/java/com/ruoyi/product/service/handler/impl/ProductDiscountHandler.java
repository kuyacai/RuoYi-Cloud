package com.ruoyi.product.service.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.ProductDiscountActivity;
import com.ruoyi.product.domain.ProductDiscountConfig;
import com.ruoyi.product.domain.ProductDiscountProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.service.IProductDiscountActivityService;
import com.ruoyi.product.service.IProductDiscountConfigService;
import com.ruoyi.product.service.IProductDiscountProductService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("PRODUCT_DISCOUNT_HANDLER")
public class ProductDiscountHandler extends AbstractActivityHandler {

    @Autowired
    private IProductDiscountProductService resultService;
    @Autowired
    private IProductDiscountConfigService configService;
    @Autowired
    private IProductDiscountActivityService activityService;

    @Override
    protected Object matchConfig(GoodsRevisionItem sku) {
        // 根据最低标价匹配配置区间 [priceMin <= price < priceMax]
        return configService.matchConfigByPrice(sku.getMarketPrice());
    }

    @Override
    protected Object linkActivity(String shopId, Object config, String activityId) {
        return activityService.getById(activityId);
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config) {
        ProductDiscountActivity act = (ProductDiscountActivity) activity;
        ProductDiscountConfig cfg = (ProductDiscountConfig) config;

        ProductDiscountProduct res = new ProductDiscountProduct();
        // res.setGoodsId(sku.getGoodsId());
        // res.setActivityId(act.getId());
        // res.setConfigId(cfg.getId());

        return resultService.save(res) ? ItemProcessResult.success() : ItemProcessResult.fail("结果保存失败");
    }
}