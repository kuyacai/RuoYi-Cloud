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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.MiaoShouSPU;
import com.ruoyi.product.enums.AsyncTaskCode;
import com.ruoyi.product.mq.consumer.base.AbstractImportConsumer;
import com.ruoyi.product.service.impl.SpuImportService;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * 妙手系统 SPU 导入任务消费者
 *
 * <p>核心功能：解析妙手（MiaoShou）导出的 SPU 数据并导入系统，构建商品基础档案及初始版本快照。</p>
 *
 * <p>处理逻辑详解：</p>
 * <ul>
 * <li>1. <b>商品判重与入库</b>：
 * <ul>
 * <li>基于 {@code sourceId}（或 {@code shopId} + {@code productId} 组合）进行唯一性校验。</li>
 * <li>若商品不存在则执行新增，若已存在则根据业务规则更新或跳过，确保 {@code goods} 表数据唯一。</li>
 * </ul>
 * </li>
 * <li>2. <b>双版本机制构建</b>：
 * <ul>
 * <li><b>冻结版本 ({@link com.ruoyi.product.enums.RevStatus#FROZEN FROZEN})</b>：记录导入时的原始状态，作为后续比对与审计的基准，不可修改。</li>
 * <li><b>编辑版本 ({@link com.ruoyi.product.enums.RevStatus#EDITING EDITING})</b>：作为当前可操作副本，承载后续所有 SPU 信息变更逻辑。</li>
 * </ul>
 * </li>
 * <li>3. <b>多表联动存储</b>：
 * <ul>
 * <li>根据生成的 {@code revisionId}，将商品详情数据分别持久化至 {@code goods_revision_spu}。</li>
 * <li>解析并提取 SPU 关联图片，按版本归档至 {@code goods_revision_image} 资源表。</li>
 * </ul>
 * </li>
 * <li>4. <b>任务生命周期初始化</b>：
 * <ul>
 * <li>成功导入后，自动创建并关联对应的 {@code item_task}（修改任务），标记任务进入待处理流转。</li>
 * </ul>
 * </li>
 * </ul>
 *
 * <p>技术细节：</p>
 * <ul>
 * <li>继承自 {@link com.ruoyi.product.mq.consumer.base.AbstractImportConsumer}，支持进度实时更新与任务手动取消检查。</li>
 * <li>通过抛出 {@code TaskCancelException} 实现大数据量处理时的优雅中断。</li>
 * </ul>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.SPU_IMPORT_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_SPU_GROUP, // Group不同
    consumeMode = ConsumeMode.ORDERLY, 
    messageModel = MessageModel.CLUSTERING, 
    consumeTimeout = 30, 
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class SpuImportTaskConsumer extends AbstractImportConsumer<MiaoShouSPU> {

    @Autowired
    private SpuImportService spuImportService;

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
        // return null;
    }

    @Override
    protected String formatErrorMessage(MiaoShouSPU spu, String status, String reason) {
        String msg = String.format("SPUID:%s, 名称:%s, 状态:%s, 原因:%s",
                spu.getProductId(), spu.getTitle(), status, reason);
        log.info(msg);
        return msg;
    }
}