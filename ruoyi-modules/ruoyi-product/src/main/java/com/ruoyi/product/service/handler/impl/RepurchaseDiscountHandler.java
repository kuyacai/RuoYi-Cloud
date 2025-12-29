package com.ruoyi.product.service.handler.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.RepurchaseCouponActivity;
import com.ruoyi.product.domain.RepurchaseCouponProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.enums.ActivityProductStatus;
import com.ruoyi.product.service.IRepurchaseCouponActivityService;
import com.ruoyi.product.service.IRepurchaseCouponConfigService;
import com.ruoyi.product.service.IRepurchaseCouponProductService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("REPURCHASE_DISCOUNT_HANDLER")
public class RepurchaseDiscountHandler extends AbstractActivityHandler {

    @Autowired
    private IRepurchaseCouponProductService productService;
    @Autowired
    private IRepurchaseCouponActivityService activityService;
    @Autowired
    private IRepurchaseCouponConfigService configService;

    @Override
    protected boolean isSkuLevelActivity() {
        return false;
    }

    @Override
    protected Object matchConfig(GoodsRevisionItem sku) {
        return configService.matchConfigByPrice(sku.getMarketPrice());
    }

    @Override
    protected Object linkActivity(String shopId, Object config, String activityId) {
        return activityService.getById(activityId);
    }

    @Override
    protected List<Object> findActiveRecordsBySpu(String shopProductId, String shopId) {
        return productService.lambdaQuery()
                .eq(RepurchaseCouponProduct::getShopProductId, shopProductId)
                .eq(RepurchaseCouponProduct::getShopId, shopId)
                .eq(RepurchaseCouponProduct::getItemStatus, ActivityProductStatus.ACTIVE)
                .list().stream().map(e -> (Object) e).collect(Collectors.toList());
    }

    @Override
    protected Object findExistingRecord(String shopProductId, String shopSkuId, String shopId) {
        return productService.lambdaQuery()
                .eq(RepurchaseCouponProduct::getShopProductId, shopProductId)
                .eq(RepurchaseCouponProduct::getShopId, shopId)
                .one();
    }

    @Override
    protected String getActivityIdFromRecord(Object record) {
        return ((RepurchaseCouponProduct) record).getActivityId();
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config,
            Object existingRecord) {
        RepurchaseCouponProduct res = (existingRecord != null)
                ? (RepurchaseCouponProduct) existingRecord
                : new RepurchaseCouponProduct();

        res.setShopProductId(sku.getShopProductId());
        res.setShopId(sku.getShopId());
        res.setActivityId(((RepurchaseCouponActivity) activity).getActivityId());

        if (config != null) {
            res.setItemStatus(ActivityProductStatus.ACTIVE);
        } else {
            res.setItemStatus(ActivityProductStatus.REMOVED);
        }

        return productService.saveOrUpdate(res) ? ItemProcessResult.success() : ItemProcessResult.fail("保存失败");
    }
}