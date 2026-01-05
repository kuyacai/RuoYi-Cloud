// PriceImportTaskConsumer.java
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
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.service.impl.PriceImportService;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * 商品 SKU 标价更新任务消费者
 * * <p>核心功能：根据导入的商品及 SKU 信息，结合系统定价参考表（price_reference）自动计算并更新 SKU 标价。</p>
 * * <p>处理流程：</p>
 * <ul>
 * <li>1. <b>身份识别</b>：通过 Excel 中的 {@code productId} 检索内部 {@code goods_id}。</li>
 * <li>2. <b>待处理项检索</b>：
 * <ul>
 * <li>基于 {@code goods_id} 锁定状态为 {@code editing} 的版本记录。</li>
 * <li>通过 {@code INNER JOIN} 关联任务代码为 {@code edit_price} 且状态为 
 * {@link com.ruoyi.product.enums.ItemTaskStatus#PENDING PENDING} 的 {@code item_task}。</li>
 * <li>过滤掉已删除（{@code sku_status != 'deleted'}）的 SKU 记录。</li>
 * </ul>
 * </li>
 * <li>3. <b>定价逻辑</b>：
 * <ul>
 * <li>获取当前 SKU 的原价（成本价 {@code originalPrice}）。</li>
 * <li>在 {@code price_reference} 表中检索匹配的定价规则。</li>
 * <li><b>边界处理</b>：若未查询到对应原价的定价参考，视为超出系统处理范围，该 SKU 将被跳过不做处理。</li>
 * </ul>
 * </li>
 * <li>4. <b>状态流转</b>：标价更新成功后，将对应的 {@code item_task} 状态设置为 
 * {@link com.ruoyi.product.enums.ItemTaskStatus#DONE DONE}。</li>
 * </ul>
 * * <p>Excel 导入项说明：</p>
 * <ul>
 * <li>{@code productId}: 用于定位商品的外部 ID。</li>
 * <li>{@code skuId}: 用于定位具体规格项的 ID。</li>
 * <li>{@code title}: 辅助参考项，不参与核心计算逻辑。</li>
 * </ul>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.PRICE_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_PRICE_GROUP, 
    consumeMode = ConsumeMode.ORDERLY, 
    messageModel = MessageModel.CLUSTERING, 
    consumeTimeout = 15, 
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class PriceImportTaskConsumer extends AbstractImportConsumer<SimpleProduct> {

    @Autowired
    private PriceImportService priceImportService;

    @Override
    protected List<SimpleProduct> parseExcelFile(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            EnhancedExcelUtil<SimpleProduct> util = new EnhancedExcelUtil<>(SimpleProduct.class);
            List<SimpleProduct> list = util.importExcel(is);
            if (list == null || list.isEmpty()) {
                log.error("the excel file is null");
            } else {
                log.info("the rows is {}", list.size());
                // 详细检查每个元素
                for (int i = 0; i < list.size(); i++) {
                    SimpleProduct sp = list.get(i);
                    if (sp == null) {
                        log.error("Row {} is NULL", i);
                    } else {
                        log.info("Row {}: productId={}, skuId={}, title={}",
                                i, sp.getProductId(), sp.getSkuId(), sp.getTitle());
                    }
                }
                for (SimpleProduct sp : list) {
                    log.info("the product id is {}", sp.getProductId());
                    log.info("the product skuid is {}", sp.getSkuId());
                    log.info("the product title is {}", sp.getProductId());
                }
            }
            return list;
        }
    }

    @Override
    protected ItemProcessResult processSingleItem(SimpleProduct price, String shopId) {
        if (price == null)
            log.error("CSV Row is null");

        return priceImportService.processSingleSpuPrice(price, shopId);
    }

    @Override
    protected String formatErrorMessage(SimpleProduct sp, String status, String reason) {
        String msg = String.format("商品ID:%s, 状态:%s, 原因:%s",
                sp.getProductId(), status, reason);
        log.info(msg);
        return msg;
    }
}