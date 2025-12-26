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
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.domain.dto.SimpleProduct;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.impl.PriceImportService;
import com.ruoyi.product.utils.EnhancedExcelUtil;
import lombok.extern.slf4j.Slf4j;

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
public class PriceImportTaskConsumer extends AbstractImportConsumer<SimpleProduct> 
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
            List<SimpleProduct> list = util.importExcel(is);
            if(list==null||list.isEmpty()){
                log.error("the excel file is null");
            }else{
                log.info("the rows is {}",list.size());
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
                for (SimpleProduct sp:list){
                    log.info("the product id is {}",sp.getProductId());
                    log.info("the product skuid is {}",sp.getSkuId());
                    log.info("the product title is {}",sp.getProductId());
                }
            }
            return list;
        }
    }
    
    @Override
    protected ItemProcessResult processSingleItem(SimpleProduct price, String shopId) {
        if (price==null)
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