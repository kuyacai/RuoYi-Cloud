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
import com.ruoyi.product.domain.dto.SimpleProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.impl.PriceImportService;

import lombok.extern.slf4j.Slf4j;

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
public class ProductDiscountTaskConsumer extends AbstractImportConsumer<SimpleProduct> 
                                     implements RocketMQListener<AsyncTaskMsg> {
    
    @Autowired
    private PriceImportService priceImportService;
    
    @Override
    public void onMessage(AsyncTaskMsg msg) {
        handleMessage(msg);
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