package com.ruoyi.product.service.handler.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.ProductDiscountActivity;
import com.ruoyi.product.domain.ProductDiscountProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.enums.ActivityProductStatus;
import com.ruoyi.product.service.IProductDiscountActivityService;
import com.ruoyi.product.service.IProductDiscountConfigService;
import com.ruoyi.product.service.IProductDiscountProductService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("PRODUCT_DISCOUNT_HANDLER")
public class ProductDiscountHandler extends AbstractActivityHandler {

    @Autowired
    private IProductDiscountProductService productService;
    @Autowired
    private IProductDiscountConfigService configService;
    @Autowired
    private IProductDiscountActivityService activityService;

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
                .eq(ProductDiscountProduct::getShopProductId, shopProductId)
                .eq(ProductDiscountProduct::getShopId, shopId)
                .eq(ProductDiscountProduct::getItemStatus, ActivityProductStatus.ACTIVE)
                .list().stream().map(e -> (Object) e).collect(Collectors.toList());
    }

    @Override
    protected Object findExistingRecord(String shopProductId, String shopSkuId, String shopId) {
        return productService.lambdaQuery()
                .eq(ProductDiscountProduct::getShopProductId, shopProductId)
                .eq(ProductDiscountProduct::getShopId, shopId)
                .last("LIMIT 1")
                .one();
    }

    @Override
    protected String getActivityIdFromRecord(Object record) {
        return ((ProductDiscountProduct) record).getActivityId();
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config,
            Object existingRecord) {
        ProductDiscountProduct res = (existingRecord != null)
                ? (ProductDiscountProduct) existingRecord
                : new ProductDiscountProduct();
        if (StringUtils.isEmpty(res.getId())) {
            res.setId(UUID.fastUUID().toString(true));
        }
        res.setShopProductId(sku.getShopProductId());
        res.setShopId(sku.getShopId());
        res.setActivityId(((ProductDiscountActivity) activity).getActivityId());

        if (config != null) {
            res.setItemStatus(ActivityProductStatus.ACTIVE);
        } else {
            res.setItemStatus(ActivityProductStatus.REMOVED);
        }

        return productService.saveOrUpdate(res) ? ItemProcessResult.success() : ItemProcessResult.fail("保存失败");
    }
}