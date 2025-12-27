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
import org.springframework.stereotype.Component;

import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.SimpleProduct;
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * 单品直降活动商品核算消费者
 * * <p>核心功能：解析导入的商品 SKU 信息，根据系统标价自动核算并生成单品直降活动立减金额。</p>
 * * <p>核算逻辑流程：</p>
 * <ul>
 * <li>1. <b>商品定位</b>：基于 Excel 中的 {@code productId} 与 {@code shopId} 检索内部 {@code goods_id}。</li>
 * <li>2. <b>版本溯源</b>：
 * <ul>
 * <li>根据 {@code goods_id} 获取最新的版本记录（{@code goods_revision}）。</li>
 * <li><i>注：规划中应仅匹配 {@code APPROVED} 状态版本，当前暂取最新版本。</i></li>
 * </ul>
 * </li>
 * <li>3. <b>规格匹配</b>：根据 {@code revisionId} 检索该版本下 {@code goods_revision_item} 中的所有 SKU 明细。</li>
 * <li>4. <b>优惠核算（核心）</b>：
 * <ul>
 * <li>将 SKU 标价与 {@code price_reference} 表中的 {@code effectiveMarkedPrice}（有效标价）进行匹配。</li>
 * <li><b>跳过逻辑</b>：若无法在参考表中匹配到该标价，则视为该单价暂不支持直降活动，该 SKU 将被跳过。</li>
 * <li><b>金额提取</b>：匹配成功后，获取参考表中的 {@code actualDiscountAmount}（实际立减金额）。</li>
 * </ul>
 * </li>
 * <li>5. <b>数据持久化</b>：构建 {@code DirectDiscountProduct} 对象，并将活动核算结果保存至 {@code direct_discount_product} 表。</li>
 * </ul>
 * * <p>技术要点：</p>
 * <ul>
 * <li>支持 {@link com.ruoyi.product.exception.TaskCancelException} 异常，可在处理大数据量时响应任务取消指令。</li>
 * <li>继承自 {@link com.ruoyi.product.mq.consumer.base.AbstractImportConsumer}，自动处理进度同步与 Excel 解析。</li>
 * </ul>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.SINGLE_DISCOUNT_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_SINGLE_DISCOUNT_GROUP, 
    consumeMode = ConsumeMode.ORDERLY, 
    messageModel = MessageModel.CLUSTERING, 
    consumeTimeout = 15, 
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class SingleDiscountTaskConsumer extends AbstractImportConsumer<SimpleProduct> {

    /**
     * 实现重复数据过滤逻辑
     * 根据 商品ID + SKU ID 进行去重
     */
    @Override
    protected String getUniqueKey(SimpleProduct sp) {
        return sp.getProductId() + "_" + sp.getSkuId();
    }

    @Override
    protected List<SimpleProduct> parseExcelFile(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            EnhancedExcelUtil<SimpleProduct> util = new EnhancedExcelUtil<>(SimpleProduct.class);
            return util.importExcel(is, 0);
        }
    }

    @Override
    protected ItemProcessResult processSingleItem(SimpleProduct price, String shopId) {
        // TODO 待实现
        return ItemProcessResult.success();
    }

    @Override
    protected String formatErrorMessage(SimpleProduct sp, String status, String reason) {
        String msg = String.format("商品ID:%s, 状态:%s, 原因:%s",
                sp.getProductId(), status, reason);
        log.info(msg);
        return msg;
    }
}