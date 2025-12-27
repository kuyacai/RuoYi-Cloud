package com.ruoyi.product.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.service.IActivityDiscountService;
import com.ruoyi.product.service.handler.AbstractActivityHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用活动折扣核算服务实现
 */
@Slf4j
@Service
public class ActivityDiscountServiceImpl implements IActivityDiscountService {

    /**
     * Spring 会自动将所有 AbstractActivityHandler 的实现类注入到此 Map 中
     * Key 为 Bean 的名称（如 "PRODUCT_DISCOUNT_HANDLER"）
     */
    @Autowired
    private Map<String, AbstractActivityHandler> handlerMap;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ItemProcessResult processActivityDiscount(String productId, String shopId, String activityId,
            String activityType) {
        // 1. 构造 Handler 名称（约定：类型大写 + _HANDLER）
        String handlerName = activityType.toUpperCase() + "_HANDLER";

        // 2. 路由到具体的处理器
        AbstractActivityHandler handler = handlerMap.get(handlerName);

        if (handler == null) {
            log.error("未找到对应的活动处理器: {}, 支持的处理器有: {}", handlerName, handlerMap.keySet());
            return ItemProcessResult.fail("不支持的活动类型: " + activityType);
        }

        // 3. 执行线性节点核算流程
        // 模板方法 handle 会自动处理：查商品 -> 查版本 -> 查SKU -> 配规则 -> 存结果
        log.debug("开始执行活动核算逻辑: productId={}, type={}, activityId={}", productId, activityType, activityId);
        return handler.handle(productId, shopId, activityId);
    }
}