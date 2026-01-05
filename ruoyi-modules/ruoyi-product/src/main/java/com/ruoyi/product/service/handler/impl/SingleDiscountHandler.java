package com.ruoyi.product.service.handler.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
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

import lombok.extern.slf4j.Slf4j;

@Component("SINGLE_DISCOUNT_HANDLER")
@Slf4j
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
        // 不同的原价可能生效的市场价都一样，所以这里根据市场价来查参考价可能会有多条
        // 根据原价来查，结果准确，但是业务流程上来说，可能不恰当，但是目前标价的配置信息并不完整，
        // 所以用原价来查
        return priceReferenceService.lambdaQuery()
                // .eq(PriceReference::getEffectiveMarkedPrice, sku.getMarketPrice())
                .eq(PriceReference::getOriginalPrice, sku.getOrignialPrice())
                .last("limit 1")
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
        log.info("=== 开始查找现有记录 ===");
        log.info("参数: shopId=[{}], shopProductId=[{}], shopSkuId=[{}]",
                shopId, shopProductId, shopSkuId);

        // 打印SQL（需要开启MyBatis Plus SQL日志）
        List<DirectDiscountProduct> all = productService.lambdaQuery()
                .list();
        log.info("表中总记录数: {}", all.size());
        DirectDiscountProduct result = productService.lambdaQuery()
                .eq(DirectDiscountProduct::getShopProductId, shopProductId)
                .eq(DirectDiscountProduct::getShopSkuId, shopSkuId)
                .eq(DirectDiscountProduct::getShopId, shopId)
                .last("LIMIT 1")
                .one();
        if (result == null) {
            log.warn("未找到记录！");
            log.info("没有找奥");
            log.info("result == null 参数: shopId=[{}], shopProductId=[{}], shopSkuId=[{}]",
                    shopId, shopProductId, shopSkuId);
        } else {
            log.info("找到记录: id={}, activityId={}",
                    result.getId(), result.getActivityId());
        }

        return result;
    }

    @Override
    protected String getActivityIdFromRecord(Object record) {
        return ((DirectDiscountProduct) record).getActivityId();
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config,
            Object existingRecord) {
        if (existingRecord == null) {
            log.info("existingRecord is null");
        } else {
            log.info("existingRecord is not null");
        }
        DirectDiscountProduct product = (existingRecord != null)
                ? (DirectDiscountProduct) existingRecord
                : new DirectDiscountProduct();

        // 如果product是新建的对象，则需要手动设置id及相关参数
        if (StringUtils.isEmpty(product.getId())) {
            String id = UUID.fastUUID().toString(true);
            log.info("product obj is created. set the ");
            product.setId(id);
            product.setShopProductId(sku.getShopProductId());
            product.setShopSkuId(sku.getShopSkuId());
            product.setShopId(sku.getShopId());
            product.setActivityId(((DirectDiscountActivity) activity).getActivityId());
        } else {
            log.info("product obj has been existed. will be update amount and status ");
        }

        if (config != null) {
            PriceReference ref = (PriceReference) config;
            product.setDeductionAmount(ref.getActualDiscountAmount());
            product.setItemStatus(ActivityProductStatus.ACTIVE);
        } else {
            product.setDeductionAmount(0L); // 不符合配置，优惠清零
            product.setItemStatus(ActivityProductStatus.REMOVED);
        }

        return productService.saveOrUpdate(product) ? ItemProcessResult.success() : ItemProcessResult.fail("保存失败");
    }
}