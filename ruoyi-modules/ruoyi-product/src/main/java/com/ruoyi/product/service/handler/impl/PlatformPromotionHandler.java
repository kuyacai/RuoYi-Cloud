package com.ruoyi.product.service.handler.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.PlatformPromotionActivity;
import com.ruoyi.product.domain.PlatformPromotionProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.enums.ActivityProductStatus;
import com.ruoyi.product.service.IPlatformPromotionActivityService;
import com.ruoyi.product.service.IPlatformPromotionConfigService;
import com.ruoyi.product.service.IPlatformPromotionProductService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("PLATFORM_PROMOTION_HANDLER")
public class PlatformPromotionHandler extends AbstractActivityHandler {

    @Autowired
    private IPlatformPromotionProductService productService;
    @Autowired
    private IPlatformPromotionActivityService activityService;
    @Autowired
    private IPlatformPromotionConfigService configService;

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
                .eq(PlatformPromotionProduct::getShopProductId, shopProductId)
                .eq(PlatformPromotionProduct::getShopId, shopId)
                .eq(PlatformPromotionProduct::getItemStatus, ActivityProductStatus.ACTIVE)
                .list().stream().map(e -> (Object) e).collect(Collectors.toList());
    }

    @Override
    protected Object findExistingRecord(String shopProductId, String shopSkuId, String shopId) {
        return productService.lambdaQuery()
                .eq(PlatformPromotionProduct::getShopProductId, shopProductId)
                .eq(PlatformPromotionProduct::getShopId, shopId)
                .one();
    }

    @Override
    protected String getActivityIdFromRecord(Object record) {
        return ((PlatformPromotionProduct) record).getActivityId();
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config,
            Object existingRecord) {
        PlatformPromotionProduct res = (existingRecord != null)
                ? (PlatformPromotionProduct) existingRecord
                : new PlatformPromotionProduct();

        res.setShopProductId(sku.getShopProductId());
        res.setShopId(sku.getShopId());
        res.setActivityId(((PlatformPromotionActivity) activity).getActivityId());

        if (config != null) {
            res.setItemStatus(ActivityProductStatus.ACTIVE);
            // 这里根据实际 PlatformPromotionProduct 字段填充金额或配置ID
        } else {
            res.setItemStatus(ActivityProductStatus.REMOVED);
        }

        return productService.saveOrUpdate(res) ? ItemProcessResult.success() : ItemProcessResult.fail("保存失败");
    }
}