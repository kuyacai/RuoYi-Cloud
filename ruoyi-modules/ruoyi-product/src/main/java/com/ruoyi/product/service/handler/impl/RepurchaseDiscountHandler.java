package com.ruoyi.product.service.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.RepurchaseCouponActivity;
import com.ruoyi.product.domain.RepurchaseCouponProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.service.IRepurchaseCouponActivityService;
import com.ruoyi.product.service.IRepurchaseCouponConfigService;
import com.ruoyi.product.service.IRepurchaseCouponProductService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("REPURCHASE_HANDLER")
public class RepurchaseDiscountHandler extends AbstractActivityHandler {

    @Autowired
    private IRepurchaseCouponProductService resultService;
    @Autowired
    private IRepurchaseCouponActivityService activityService;
    @Autowired
    private IRepurchaseCouponConfigService configService;

    @Override
    protected Object matchConfig(GoodsRevisionItem sku) {
        return configService.matchConfigByPrice(sku.getMarketPrice());
    }

    @Override
    protected Object linkActivity(String shopId, Object config, String activityId) {
        return activityService.getById(activityId);
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config) {
        RepurchaseCouponActivity act = (RepurchaseCouponActivity) activity;

        RepurchaseCouponProduct res = new RepurchaseCouponProduct();
        // res.setGoodsId(sku.getGoodsId());
        // res.setActivityId(act.getId());
        // res.setConfigId(((RepurchaseCouponConfig) config).getId());

        return resultService.save(res) ? ItemProcessResult.success() : ItemProcessResult.fail("复购券结果保存失败");
    }
}