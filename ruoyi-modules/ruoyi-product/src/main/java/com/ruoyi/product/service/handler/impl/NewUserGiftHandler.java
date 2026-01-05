package com.ruoyi.product.service.handler.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.NewUserGiftActivity;
import com.ruoyi.product.domain.NewUserGiftConfig;
import com.ruoyi.product.domain.NewUserGiftProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.enums.ActivityProductStatus;
import com.ruoyi.product.service.INewUserGiftActivityService;
import com.ruoyi.product.service.INewUserGiftConfigService;
import com.ruoyi.product.service.INewUserGiftProductService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

@Component("NEW_USER_GIFT_HANDLER")
public class NewUserGiftHandler extends AbstractActivityHandler {

    @Autowired
    private INewUserGiftProductService productService;
    @Autowired
    private INewUserGiftConfigService configService;
    @Autowired
    private INewUserGiftActivityService activityService;

    @Override
    protected boolean isSkuLevelActivity() {
        return false; // 新人礼金是 SPU 级
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
                .eq(NewUserGiftProduct::getShopProductId, shopProductId)
                .eq(NewUserGiftProduct::getShopId, shopId)
                .eq(NewUserGiftProduct::getItemStatus, ActivityProductStatus.ACTIVE)
                .list().stream().map(e -> (Object) e).collect(Collectors.toList());
    }

    @Override
    protected Object findExistingRecord(String shopProductId, String shopSkuId, String shopId) {
        // 新人礼金通常只存一条 SPU 记录，不带 skuId
        return productService.lambdaQuery()
                .eq(NewUserGiftProduct::getShopProductId, shopProductId)
                .eq(NewUserGiftProduct::getShopId, shopId)
                .last("LIMIT 1")
                .one();
    }

    @Override
    protected String getActivityIdFromRecord(Object record) {
        return ((NewUserGiftProduct) record).getActivityId();
    }

    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config,
            Object existingRecord) {
        NewUserGiftProduct result = (existingRecord != null)
                ? (NewUserGiftProduct) existingRecord
                : new NewUserGiftProduct();
        if (StringUtils.isEmpty(result.getId())) {
            result.setId(UUID.fastUUID().toString(true));
        }
        NewUserGiftActivity act = (NewUserGiftActivity) activity;

        // 基础信息设置
        result.setShopProductId(sku.getShopProductId());
        result.setShopId(sku.getShopId());
        result.setActivityId(act.getActivityId());

        if (config != null) {
            // 匹配成功：设置金额并激活
            NewUserGiftConfig cfg = (NewUserGiftConfig) config;
            result.setAvgAmount(cfg.getAvgAmount());
            result.setMaxGiftAmount(cfg.getMaxGiftAmount());
            result.setItemStatus(ActivityProductStatus.ACTIVE);
        } else {
            // 匹配失败（仅在 SKU 循环模式下会走到这里）：标记删除
            result.setAvgAmount(0);
            result.setMaxGiftAmount(0);
            result.setItemStatus(ActivityProductStatus.REMOVED);
        }

        return productService.saveOrUpdate(result)
                ? ItemProcessResult.success()
                : ItemProcessResult.fail("持久化失败");
    }
}