
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
 * 新人礼金活动商品核算消费者
 * * <p>核心功能：解析导入的商品信息，通过识别 SPU 下最低标价的 SKU，匹配系统新人礼金配置（new_user_gift_config）并核算立减金参数。</p>
 * * <p>核算逻辑流程：</p>
 * <ul>
 * <li>1. <b>商品及版本识别</b>：
 * <ul>
 * <li>利用 Excel 中的 {@code productId} 与 {@code shopId} 定位内部 {@code goods_id}。</li>
 * <li>获取该商品最新的版本记录（{@code goods_revision}）。<i>注：暂未强制限定为 APPROVED 状态版本。</i></li>
 * </ul>
 * </li>
 * <li>2. <b>最低价提取</b>：根据 {@code revisionId} 检索 {@code goods_revision_item} 中该商品所有 SKU 记录，并锁定标价（Marked Price）最低的一项。</li>
 * <li>3. <b>区间梯度匹配（核心）</b>：
 * <ul>
 * <li>使用最低标价匹配 {@code new_user_gift_config} 表：满足 {@code priceMin <= 最低标价 < priceMax} 规则。</li>
 * <li><b>跳过逻辑</b>：若标价不在任何配置区间内，则视为不支持新人礼金活动，该 SPU 任务被跳过处理。</li>
 * </ul>
 * </li>
 * <li>4. <b>礼金参数获取</b>：匹配成功后，提取配置中的平均立减金（{@code avgAmount}）与最高立减金额（{@code maxGiftAmount}）。</li>
 * <li>5. <b>数据入库</b>：构建 {@code NewUserGiftProduct} 实体对象，将核算后的活动配置持久化至 {@code new_user_gift_product} 表。</li>
 * </ul>
 * * <p>技术细节：</p>
 * <ul>
 * <li>继承自 {@link com.ruoyi.product.mq.consumer.base.AbstractImportConsumer}，支持处理过程中的任务状态监控。</li>
 * <li>在 {@code processItems} 循环中通过 {@link com.ruoyi.product.exception.TaskCancelException} 响应前端取消指令。</li>
 * </ul>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.NEW_USER_GIFT_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_NEW_USER_GIFT_GROUP, 
    consumeMode = ConsumeMode.ORDERLY, 
    messageModel = MessageModel.CLUSTERING, 
    consumeTimeout = 15, 
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class NewUserGiftTaskConsumer extends AbstractActivityDiscountConsumer<SimpleProduct> {

    @Autowired
    private IActivityDiscountService activityDiscountService;

    /**
     * 根据商品ID去重
     */
    @Override
    protected String getUniqueKey(SimpleProduct item) {
        return item.getProductId();
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
        // 从 AbstractActivityDiscountConsumer 的 currentMsg 中提取 activityId
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

    /**
     * 获取 DTO 中的商品 ID
     */
    @Override
    protected String getProductIdFromDto(SimpleProduct item) {
        return item.getProductId();
    }

    /**
     * 定义活动类型，需与 Handler 的 Bean 名称前缀一致
     */
    @Override
    protected String getActivityType() {
        return "NEW_USER_GIFT";
    }

    /**
     * 返回核算服务实例
     */
    @Override
    protected IActivityDiscountService getDiscountService() {
        return this.activityDiscountService;
    }
}