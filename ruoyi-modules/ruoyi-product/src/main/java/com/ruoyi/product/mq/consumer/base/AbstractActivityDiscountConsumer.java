package com.ruoyi.product.mq.consumer.base;

import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.service.IActivityDiscountService;

public abstract class AbstractActivityDiscountConsumer<T> extends AbstractImportConsumer<T> {

    @Override
    protected ItemProcessResult processSingleItem(T item, String shopId) {
        String activityId = getActivityId(); // 从 currentMsg 获取 activityId
        String productId = getProductIdFromDto(item);

        // 调用通用 Service 执行“最低价 SKU -> 配置 -> 活动”的链路
        return getDiscountService().processActivityDiscount(
                productId,
                shopId,
                activityId,
                getActivityType());
    }

    // 由子类告知 DTO 中的 productId
    protected abstract String getProductIdFromDto(T item);

    // 由子类告知活动类型（用于 Service 区分查询哪张配置表）
    protected abstract String getActivityType();

    protected abstract IActivityDiscountService getDiscountService();
}
