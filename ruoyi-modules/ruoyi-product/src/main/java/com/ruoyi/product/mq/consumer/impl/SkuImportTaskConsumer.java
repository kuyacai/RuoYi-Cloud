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
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.utils.EnhancedExcelUtil;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.MiaoShouSKU;
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.impl.SkuImportService;

import lombok.extern.slf4j.Slf4j;

@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.SKU_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_SKU_GROUP, 
    consumeMode = ConsumeMode.ORDERLY,
    messageModel = MessageModel.CLUSTERING,
    consumeTimeout = 15,
    maxReconsumeTimes = 3
)
@Slf4j
public class SkuImportTaskConsumer extends AbstractImportConsumer<MiaoShouSKU> 
                                   implements RocketMQListener<AsyncTaskMsg> {
    
    @Autowired
    private SkuImportService skuImportService;
    
    @Override
    public void onMessage(AsyncTaskMsg msg) {
        handleMessage(msg);
    }
    
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