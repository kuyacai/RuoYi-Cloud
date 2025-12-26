// SpuImportTaskConsumer.java
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
import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.MiaoShouSPU;
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.impl.SpuImportService;

import lombok.extern.slf4j.Slf4j;

@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.SPU_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_SPU_GROUP, // Group不同
    consumeMode = ConsumeMode.ORDERLY,
    messageModel = MessageModel.CLUSTERING,
    consumeTimeout = 15,
    maxReconsumeTimes = 3
)
@Slf4j
public class SpuImportTaskConsumer extends AbstractImportConsumer<MiaoShouSPU> 
                                   implements RocketMQListener<AsyncTaskMsg> {
    
    @Autowired
    private SpuImportService spuImportService;
    
    @Override
    public void onMessage(AsyncTaskMsg msg) {
        handleMessage(msg);
    }
    
    @Override
    protected List<MiaoShouSPU> parseExcelFile(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            EnhancedExcelUtil<MiaoShouSPU> util = new EnhancedExcelUtil<>(MiaoShouSPU.class);
            return util.importExcel(is);
        }
    }
    
    @Override
    protected ItemProcessResult processSingleItem(MiaoShouSPU spu, String shopId) {
        return spuImportService.processSingleSpu(spu, shopId);
        //return null;
    }
    
    @Override
    protected String formatErrorMessage(MiaoShouSPU spu, String status, String reason) {
        String msg = String.format("SPUID:%s, 名称:%s, 状态:%s, 原因:%s",
                spu.getProductId(), spu.getTitle(), status, reason);
        log.info(msg);
        return msg;
    }
}