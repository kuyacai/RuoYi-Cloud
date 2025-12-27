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
import com.ruoyi.product.domain.dto.ProductTitle;
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.service.impl.ModifyTitleService;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * 商品标题及文案修改任务消费者
 * * <p>核心功能：批量修改商品的标题、导购标题、覆盖关键词以及AI视频介绍文案。</p>
 * * <p>处理流程：</p>
 * <ul>
 * <li>1. <b>解析映射</b>：解析Excel中的 {@code productId}，并在数据库中查询对应的 {@code goods_id}。</li>
 * <li>2. <b>精准定位</b>：根据查询到的 {@code goods_id}，检索状态为 {@code editing}（编辑中）的 
 * {@code goods_revision} 记录。</li>
 * <li>3. <b>任务关联</b>：通过 {@code INNER JOIN} 锁定关联的 {@code item_task}，要求任务代码为 
 * {@code edit_title} 且当前状态为 {@link com.ruoyi.product.constant.ItemTaskStatus#PENDING PENDING}。</li>
 * <li>4. <b>信息覆盖</b>：更新 {@code goods_revision_spu} 表中的 {@code title}、{@code guide_short_title}、
 * {@code search_keywords} 和 {@code video_script}。</li>
 * <li>5. <b>状态流转</b>：更新完成后，将对应的 {@code item_task} 状态设置为 
 * {@link com.ruoyi.product.constant.ItemTaskStatus#DONE DONE}。</li>
 * </ul>
 * * <p>Excel 导入项说明：</p>
 * <ul>
 * <li>{@code productId}: 商品唯一标识，用于换取内部主键。</li>
 * <li>{@code newTitle}: 准备覆盖的目标商品新标题。</li>
 * <li>{@code guideShortTitle}, {@code searchKeywords}, {@code videoScript}: 关联的营销文案修改项。</li>
 * </ul>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.MODIFY_TITLE_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_MODIFY_TITLE_GROUP, 
    consumeMode = ConsumeMode.ORDERLY, 
    messageModel = MessageModel.CLUSTERING, 
    consumeTimeout = 15, 
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class ModifyTitleTaskConsumer extends AbstractImportConsumer<ProductTitle> {

    @Autowired
    private ModifyTitleService modifyTitleService;

    @Override
    protected List<ProductTitle> parseExcelFile(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            EnhancedExcelUtil<ProductTitle> util = new EnhancedExcelUtil<>(ProductTitle.class);
            return util.importExcel(is, 0);
        }
    }

    @Override
    protected ItemProcessResult processSingleItem(ProductTitle productTitle, String shopId) {

        return modifyTitleService.processSingleSpuTitle(productTitle, shopId);
    }

    @Override
    protected String formatErrorMessage(ProductTitle productTitle, String status, String reason) {
        String msg = String.format("商品ID:%s, 状态:%s, 原因:%s",
                productTitle.getProductId(), status, reason);
        log.info(msg);
        return msg;
    }
}