package com.ruoyi.product.mq.consumer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.SimpleProduct;
import com.ruoyi.product.enums.AsyncTaskCode;
import com.ruoyi.product.mq.consumer.base.AbstractActivityDiscountConsumer;
import com.ruoyi.product.service.IActivityDiscountService;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * 通用商品优惠券活动核算消费者
 *
 * <p>核心功能：解析导入的商品信息，通过识别 SPU 下最低标价的 SKU，匹配通用优惠券配置及活动，核算并关联商品活动力度。</p>
 *
 * <p>核算逻辑流程：</p>
 * <ul>
 * <li>1. <b>商品溯源</b>：利用 Excel 中的 {@code productId} 与 {@code shopId} 定位内部 {@code goods_id}。</li>
 * <li>2. <b>版本锁定</b>：检索该商品最新的版本记录（{@code goods_revision}）。<i>注：当前逻辑暂未强制要求状态为 APPROVED。</i></li>
 * <li>3. <b>最低价提取</b>：基于 {@code revisionId} 检索 {@code goods_revision_item}，获取该商品所有 SKU 中标价（Marked Price）最低的一项记录。</li>
 * <li>4. <b>配置梯度匹配</b>：
 * <ul>
 * <li>将最低标价与 {@code product_discount_config} 表匹配：满足 {@code priceMin <= 最低标价 < priceMax}。</li>
 * <li><b>跳过逻辑</b>：若标价不在任何配置区间内，则视为不支持通用优惠券活动，该 SPU 任务被跳过。</li>
 * </ul>
 * </li>
 * <li>5. <b>活动实例关联</b>：
 * <ul>
 * <li>根据匹配到的配置 ID（{@code configId}）与 {@code shopId}，在 {@code product_discount_activity} 表中检索。</li>
 * <li><b>时效性规则</b>：若存在多个活动，取 {@code gmt_create}（创建时间）最近的一条记录作为当前关联活动。</li>
 * </ul>
 * </li>
 * <li>6. <b>核算结果持久化</b>：构建 {@code ProductDiscountProduct} 对象，记录关联的活动及配置信息，保存至 {@code product_discount_product} 表。</li>
 * </ul>
 *
 * <p>技术特性：</p>
 * <ul>
 * <li>继承自 {@link com.ruoyi.product.mq.consumer.base.AbstractImportConsumer}，复用文件解析、进度管理及异常处理机制。</li>
 * <li>支持 {@link com.ruoyi.product.exception.TaskCancelException}，确保在复杂关联查询过程中能及时响应取消指令。</li>
 * </ul>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.PRODUCT_DISCOUNT_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_PRODUCT_DISCOUNT_GROUP, 
    consumeMode = ConsumeMode.ORDERLY, 
    messageModel = MessageModel.CLUSTERING, 
    consumeTimeout = 15, 
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class ProductDiscountTaskConsumer extends AbstractActivityDiscountConsumer<SimpleProduct> {

    @Autowired
    private IActivityDiscountService activityDiscountService;

    /**
     * 实现重复数据过滤逻辑
     * 根据 商品ID 进行去重
     */
    @Override
    protected String getUniqueKey(SimpleProduct sp) {
        return sp.getProductId();
    }

    @Override
    protected List<SimpleProduct> parseExcelFile(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            EnhancedExcelUtil<SimpleProduct> util = new EnhancedExcelUtil<>(SimpleProduct.class);
            return util.importExcel(is, 0);
        }
    }

    @Override
    protected ItemProcessResult processSingleItem(SimpleProduct item, String shopId) {
        String activityId = getActivityId();

        // 调用通用 Service，传入活动类型 NEW_USER_GIFT
        // Service 会根据此类型定位到 NEW_USER_GIFT_HANDLER 执行线性核算
        return activityDiscountService.processActivityDiscount(
                item.getProductId(),
                shopId,
                activityId,
                getActivityType());
    }

    @Override
    protected String formatErrorMessage(SimpleProduct sp, String status, String reason) {
        String msg = String.format("商品ID:%s, 状态:%s, 原因:%s",
                sp.getProductId(), status, reason);
        log.info(msg);
        return msg;
    }

    @Override
    protected String getActivityType() {

        return "PRODUCT_DISCOUNT";
    }

    @Override
    protected IActivityDiscountService getDiscountService() {
        return this.activityDiscountService;
    }

    @Override
    protected String getProductIdFromDto(SimpleProduct item) {
        return item.getProductId();
    }

}