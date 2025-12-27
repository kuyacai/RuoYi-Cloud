package com.ruoyi.product.service.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.NewUserGiftActivity;
import com.ruoyi.product.domain.NewUserGiftConfig;
import com.ruoyi.product.domain.NewUserGiftProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.service.INewUserGiftActivityService;
import com.ruoyi.product.service.INewUserGiftConfigService;
import com.ruoyi.product.service.INewUserGiftProductService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

/**
 * 新人礼金活动核算处理器
 */
@Component("NEW_USER_GIFT_HANDLER")
public class NewUserGiftHandler extends AbstractActivityHandler {

    @Autowired
    private INewUserGiftProductService newUserGiftProductService;

    @Autowired
    private INewUserGiftConfigService newUserGiftConfigService;

    @Autowired
    private INewUserGiftActivityService newUserGiftActivityService;

    /**
     * 节点：匹配配置
     * 逻辑：最低标价在 [priceMin, priceMax) 区间内
     */
    @Override
    protected Object matchConfig(GoodsRevisionItem sku) {
        return newUserGiftConfigService.matchConfigByPrice(sku.getMarketPrice());
    }

    /**
     * 节点：关联活动
     * 逻辑：新人礼金通常是全局配置或根据 shopId 关联最新的活动定义
     */
    @Override
    protected Object linkActivity(String shopId, Object config, String activityId) {
        // 如果新人礼金是基于配置 ID 关联的活动，逻辑如下：
        // NewUserGiftConfig cfg = (NewUserGiftConfig) config;
        // return newUserGiftProductService.getLatestActivity(shopId, cfg.getId());
        // TODO 新人礼金实际上不用分多个活动，这里不进行查询，直接返回一个活动对象即可。
        return newUserGiftActivityService.getById(activityId);
    }

    /**
     * 节点：保存结果
     */
    @Override
    protected ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config) {
        NewUserGiftConfig cfg = (NewUserGiftConfig) config;
        NewUserGiftActivity act = (NewUserGiftActivity) activity;
        // activity 对象根据您的业务定义，可能是活动 ID 或活动实体对象

        NewUserGiftProduct result = new NewUserGiftProduct();
        result.setId(UUID.fastUUID().toString(true));
        // result.setGoodsId(sku.getGoodsId());
        // 假设这里需要保存核算出的金额
        result.setShopProductId(sku.getShopProductId());
        result.setAvgAmount(cfg.getAvgAmount());
        result.setMaxGiftAmount(cfg.getMaxGiftAmount());
        result.setAddedTime(DateUtils.getNowDate());
        result.setActivityId(act.getActivityId());

        // 建议在 Service 中实现 save 或 update 逻辑（避免重复导入产生多条记录）
        boolean success = newUserGiftProductService.save(result);

        return success ? ItemProcessResult.success() : ItemProcessResult.fail("新人礼金结果持久化失败");
    }
}