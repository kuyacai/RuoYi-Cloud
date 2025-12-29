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

import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.SimpleProduct;
import com.ruoyi.product.mq.consumer.base.AbstractActivityDiscountConsumer;
import com.ruoyi.product.service.IActivityDiscountService;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * 复购券活动商品核算消费者
 *
 * <p>核心功能：解析导入的商品信息，通过识别 SPU 下标价最低的 SKU，匹配复购券配置及活动，核算并关联商品的复购优惠力度。</p>
 *
 * <p>核算逻辑流程：</p>
 * <ul>
 * <li>1. <b>归属识别</b>：利用 Excel 中的 {@code productId} 与 {@code shopId} 定位系统内部 {@code goods_id}。</li>
 * <li>2. <b>版本追溯</b>：检索该商品最新的版本记录（{@code goods_revision}）。<i>注：当前逻辑暂未强制要求版本状态为 APPROVED。</i></li>
 * <li>3. <b>价格基准提取</b>：基于 {@code revisionId} 检索 {@code goods_revision_item}，锁定该商品所有 SKU 中标价（Marked Price）最低的一项。</li>
 * <li>4. <b>阶梯配置匹配</b>：
 * <ul>
 * <li>将最低标价与 {@code repurchase_coupon_config} 表进行区间匹配：满足 {@code priceMin <= 最低标价 < priceMax}。</li>
 * <li><b>跳过逻辑</b>：若标价不在任何配置区间内，视为该商品单价不支持复购券活动，该 SPU 任务被跳过。</li>
 * </ul>
 * </li>
 * <li>5. <b>活动实例挂载</b>：
 * <ul>
 * <li>根据匹配到的配置 ID（{@code configId}）与 {@code shopId}，在 {@code repurchase_coupon_activity} 表中检索。</li>
 * <li><b>最优选取规则</b>：若存在多个符合条件的活动，选取 {@code gmt_create}（创建时间）最近的一条活跃记录。</li>
 * </ul>
 * </li>
 * <li>6. <b>核算结果持久化</b>：构建 {@code RepurchaseCouponProduct} 对象，记录关联的活动及配置信息，保存至 {@code repurchase_coupon_product} 表。</li>
 * </ul>
 *
 * <p>技术特性：</p>
 * <ul>
 * <li>继承自 {@link com.ruoyi.product.mq.consumer.base.AbstractImportConsumer}，复用成熟的文件处理、进度同步及取消检查机制。</li>
 * <li>处理过程中通过 {@link com.ruoyi.product.exception.TaskCancelException} 实时响应异步任务的终止指令。</li>
 * </ul>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.REPURCHASE_DISCOUNT_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_REPURCHASE_GROUP, 
    consumeMode = ConsumeMode.ORDERLY, 
    messageModel = MessageModel.CLUSTERING, 
    consumeTimeout = 15, 
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class RepurchaseTaskConsumer extends AbstractActivityDiscountConsumer<SimpleProduct> {

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
        return "REPURCHASE_DISCOUNT";
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