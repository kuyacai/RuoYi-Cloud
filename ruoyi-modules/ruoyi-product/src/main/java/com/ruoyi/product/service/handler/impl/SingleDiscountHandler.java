package com.ruoyi.product.service.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.product.domain.DirectDiscountProduct;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.PriceReference;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.service.IDirectDiscountActivityService;
import com.ruoyi.product.service.IDirectDiscountProductService;
import com.ruoyi.product.service.IPriceReferenceService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("SINGLE_DISCOUNT_HANDLER")
public class SingleDiscountHandler extends AbstractActivityHandler {

    @Autowired
    private IPriceReferenceService priceReferenceService;
    @Autowired
    private IDirectDiscountProductService directDiscountProductService;
    @Autowired
    private IDirectDiscountActivityService directDiscountActivityService;

    @Override
    protected boolean isSkuLevelActivity() {
        return true; // 开启 SKU 维度遍历模式
    }

    @Override
    protected Object matchConfig(GoodsRevisionItem sku) {
        // 逻辑：将 SKU 标价与 price_reference 匹配
        return priceReferenceService.lambdaQuery()
                .eq(PriceReference::getEffectiveMarkedPrice, sku.getMarketPrice())
                .one();
    }

    @Override
    protected Object linkActivity(String shopId, Object config, String activityId) {
        // 单品直降可能直接根据 shopId 获取当前生效的活动
        // 或者从 config (PriceReference) 中获取配置
        return directDiscountActivityService.getById(activityId);
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config) {
        PriceReference ref = (PriceReference) config;

        DirectDiscountProduct product = new DirectDiscountProduct();
        product.setId(UUID.fastUUID().toString(true));
        product.setShopProductId(sku.getShopProductId());
        product.setShopSkuId(sku.getShopSkuId());
        product.setDeductionAmount(ref.getActualDiscountAmount());
        product.setUserLimit(2);
        product.setItemStatus("active");
        product.setAddedTime(DateUtils.getNowDate());

        directDiscountProductService.save(product);
        return ItemProcessResult.success();
    }
}