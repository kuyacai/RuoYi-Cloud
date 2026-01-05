// SkuImportTaskConsumer.java
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
import com.ruoyi.product.domain.dto.MiaoShouSKU;
import com.ruoyi.product.enums.AsyncTaskCode;
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.service.impl.SkuImportService;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * 妙手系统 SKU 导入任务消费者
 * * <p>核心功能：将妙手（MiaoShou）系统导出的 SKU 数据导入至本系统，实现商品规格数据的初始化与版本构建。</p>
 * * <p>处理逻辑详解：</p>
 * <ul>
 * <li>1. <b>双版本并行生成</b>：
 * <ul>
 * <li><b>冻结版本 ({@link com.ruoyi.product.enums.RevStatus#FROZEN FROZEN})</b>：作为原始数据快照，其内容与导入数据完全一致，系统限制其不可被修改，用于后期审计与溯源。</li>
 * <li><b>编辑版本 ({@link com.ruoyi.product.enums.RevStatus#EDITING EDITING})</b>：作为初始可变副本，后续所有的标题、价格等修改任务均在此版本基础上进行。</li>
 * </ul>
 * </li>
 * <li>2. <b>存储映射</b>：解析 Excel 记录并同步保存至商品快照明细表 {@code goods_revision_item}。</li>
 * <li>3. <b>资源解析</b>：自动提取并解析 SKU 关联的图片 URL，将其持久化至 {@code goods_revision_image} 资源表。</li>
 * <li>4. <b>任务联动</b>：在导入数据的同时，系统会自动为该商品生成对应的 {@code item_task}（修改任务），触发后续的自动化处理流程。</li>
 * </ul>
 * * <p>技术要点：</p>
 * <ul>
 * <li>继承自 {@link com.ruoyi.product.mq.consumer.base.AbstractImportConsumer}，复用文件下载、进度监控及取消检查机制。</li>
 * <li>采用顺序消费模式 ({@code ConsumeMode.ORDERLY})，确保同一商品的版本构建顺序正确。</li>
 * </ul>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.SKU_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_SKU_GROUP, 
    consumeMode = ConsumeMode.ORDERLY, 
    messageModel = MessageModel.CLUSTERING, 
    consumeTimeout = 30, 
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class SkuImportTaskConsumer extends AbstractImportConsumer<MiaoShouSKU> {

    @Autowired
    private SkuImportService skuImportService;

    @Override
    protected List<MiaoShouSKU> parseExcelFile(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            EnhancedExcelUtil<MiaoShouSKU> util = new EnhancedExcelUtil<>(MiaoShouSKU.class);
            return util.importExcel(is, 0);
        }
    }

    @Override
    protected ItemProcessResult processSingleItem(MiaoShouSKU sku, String shopId) {
        return skuImportService.processSingleSku(sku, shopId);
    }

    @Override
    protected String formatErrorMessage(MiaoShouSKU sku, String status, String reason) {
        String msg = String.format("商品ID:%s, SKUID:%s, 状态:%s, 原因:%s",
                sku.getProductId(), sku.getSkuId(), status, reason);
        log.info(msg);
        return msg;
    }
}