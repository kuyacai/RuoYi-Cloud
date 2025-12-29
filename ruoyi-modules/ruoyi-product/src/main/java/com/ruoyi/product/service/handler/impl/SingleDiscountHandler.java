package com.ruoyi.product.service.handler.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.domain.DirectDiscountActivity;
import com.ruoyi.product.domain.DirectDiscountProduct;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.PriceReference;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.enums.ActivityProductStatus;
import com.ruoyi.product.service.IDirectDiscountActivityService;
import com.ruoyi.product.service.IDirectDiscountProductService;
import com.ruoyi.product.service.IPriceReferenceService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("SINGLE_DISCOUNT_HANDLER")
public class SingleDiscountHandler extends AbstractActivityHandler {

    @Autowired
    private IPriceReferenceService priceReferenceService;
    @Autowired
    private IDirectDiscountProductService productService;
    @Autowired
    private IDirectDiscountActivityService activityService;

    @Override
    protected boolean isSkuLevelActivity() {
        return true; // 开启 SKU 维度核算
    }

    @Override
    protected Object matchConfig(GoodsRevisionItem sku) {
        return priceReferenceService.lambdaQuery()
                .eq(PriceReference::getEffectiveMarkedPrice, sku.getMarketPrice())
                .one();
    }

    @Override
    protected Object linkActivity(String shopId, Object config, String activityId) {
        return activityService.getById(activityId);
    }

    @Override
    protected List<Object> findActiveRecordsBySpu(String shopProductId, String shopId) {
        return productService.lambdaQuery()
                .eq(DirectDiscountProduct::getShopProductId, shopProductId)
                .eq(DirectDiscountProduct::getShopId, shopId)
                .eq(DirectDiscountProduct::getItemStatus, ActivityProductStatus.ACTIVE)
                .list().stream().map(e -> (Object) e).collect(Collectors.toList());
    }

    @Override
    protected Object findExistingRecord(String shopProductId, String shopSkuId, String shopId) {
        return productService.lambdaQuery()
                .eq(DirectDiscountProduct::getShopProductId, shopProductId)
                .eq(DirectDiscountProduct::getShopSkuId, shopSkuId)
                .eq(DirectDiscountProduct::getShopId, shopId)
                .one();
    }

    @Override
    protected String getActivityIdFromRecord(Object record) {
        return ((DirectDiscountProduct) record).getActivityId();
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config,
            Object existingRecord) {
        DirectDiscountProduct product = (existingRecord != null)
                ? (DirectDiscountProduct) existingRecord
                : new DirectDiscountProduct();

        product.setShopProductId(sku.getShopProductId());
        product.setShopSkuId(sku.getShopSkuId());
        product.setShopId(sku.getShopId());
        product.setActivityId(((DirectDiscountActivity) activity).getActivityId());

        if (config != null) {
            PriceReference ref = (PriceReference) config;
            product.setDeductionAmount(ref.getActualDiscountAmount());
            product.setItemStatus(ActivityProductStatus.ACTIVE);
        } else {
            product.setDeductionAmount(0); // 不符合配置，优惠清零
            product.setItemStatus(ActivityProductStatus.REMOVED);
        }

        return productService.saveOrUpdate(product) ? ItemProcessResult.success() : ItemProcessResult.fail("保存失败");
    }
}