package com.ruoyi.product.service.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.PlatformPromotionActivity;
import com.ruoyi.product.domain.PlatformPromotionProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.service.IPlatformPromotionActivityService;
import com.ruoyi.product.service.IPlatformPromotionConfigService;
import com.ruoyi.product.service.IPlatformPromotionProductService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("PLATFORM_PROMOTION_HANDLER")
public class PlatformPromotionHandler extends AbstractActivityHandler {

    @Autowired
    private IPlatformPromotionProductService resultService;
    @Autowired
    private IPlatformPromotionActivityService activityService;
    @Autowired
    private IPlatformPromotionConfigService configService;

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
        PlatformPromotionActivity act = (PlatformPromotionActivity) activity;

        PlatformPromotionProduct res = new PlatformPromotionProduct();
        // res.setGoodsId(sku.getGoodsId());
        // res.setActivityId(act.getId());
        // res.setConfigId(((PlatformPromotionConfig) config).getId());

        return resultService.save(res) ? ItemProcessResult.success() : ItemProcessResult.fail("平台促销结果保存失败");
    }
}