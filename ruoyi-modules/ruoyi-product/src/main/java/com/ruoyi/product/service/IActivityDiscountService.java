package com.ruoyi.product.service;

import com.ruoyi.product.domain.dto.ItemProcessResult;

/**
 * 通用活动折扣核算服务接口
 * 负责调度不同的活动处理器执行线性核算逻辑
 */
public interface IActivityDiscountService {

    /**
     * 执行活动核算
     *
     * @param productId    外部商品ID
     * @param shopId       店铺ID
     * @param activityId   活动ID（来自消息扩展参数）
     * @param activityType 活动类型（用于定位具体的Handler）
     * @return 核算处理结果
     */
    ItemProcessResult processActivityDiscount(String productId, String shopId, String activityId, String activityType);
}