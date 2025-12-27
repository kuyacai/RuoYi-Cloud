package com.ruoyi.product.mq.consumer.impl;

import java.util.Map;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.constant.AsyncTaskStatus;
import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.constant.RevStatus;
import com.ruoyi.product.domain.AsyncTask;
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.product.service.IGoodsRevisionService;

import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * 商品审核消息消费者
 * * <p>触发时机：当商品关联的所有修改任务（ItemTask）均完成后触发。</p>
 * * <p>核心逻辑：</p>
 * <ul>
 * <li>1. 根据消息携带的 {@code bizId} 审核商品版本信息。</li>
 * <li>2. <b>审核通过</b>：若商品所有任务均通过审核，将商品 {@link com.ruoyi.product.constant.RevStatus#APPROVED APPROVED}。</li>
 * <li>3. <b>审核不通过（可修复）</b>：若某项任务审核未通过且需重新修改，将对应任务状态重置为 
 * {@link com.ruoyi.product.constant.ItemTaskStatus#PENDING PENDING}。</li>
 * <li>4. <b>审核不通过（不可修复）</b>：若认为商品不具备上架性质，则无需退回，直接将商品状态设为 
 * {@link com.ruoyi.product.constant.RevStatus#DISCARDED DISCARDED}。</li>
 * </ul>
 * * <p>注意：除需要退回修改的任务外，其余任务应保持 {@link com.ruoyi.product.constant.ItemTaskStatus#DONE DONE} 状态。</p>
 */
@Component
@RocketMQMessageListener(
    topic = MQConstant.AsyncTaskProductTopic, 
    selectorType = SelectorType.TAG, 
    selectorExpression = AsyncTaskCode.AUDIT_SPU_COMPREHENSIVE_CODE, 
    consumerGroup = MQConstant.ASYNC_TASK_AUDIT_SPU_COMPREHENSIVE_GROUP, // Group不同
    consumeMode = ConsumeMode.ORDERLY,
    messageModel = MessageModel.CLUSTERING,
    consumeTimeout = 15,
    maxReconsumeTimes = 3
)
@Slf4j
// @formatter:on
public class AuditSpuConsumer implements RocketMQListener<AsyncTaskMsg> {

    @Autowired
    protected IAsyncTaskService asyncTaskService;

    @Autowired
    private IGoodsRevisionService goodsRevisionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(AsyncTaskMsg message) {

        String taskId = message.getTaskId();
        Map<String, Object> ext = message.getExt();

        log.info("开始处理审核任务消息，taskId: {}, ext: {}", taskId, ext);

        // ==================== 1. 参数验证 ====================
        if (StringUtils.isEmpty(ext)) {
            log.error("任务未携带约定的参数ext。taskId: {}", taskId);
            return; // 返回，让消息进入重试或死信队列
        }

        // String bizId = ext.get("bizId");
        String bizId = ext != null && ext.get("bizId") != null ? String.valueOf(ext.get("bizId")) : null;
        if (StringUtils.isEmpty(bizId)) {
            log.error("任务未携带约定的参数bizId。taskId: {}", taskId);
            return;
        }
        // ==================== 2. 幂等性检查 ====================
        if (isMessageAlreadyProcessed(taskId)) {
            log.info("消息已处理过，跳过重复消费，taskId: {}", taskId);
            return;
        }

        // ==================== 3. 获取任务详情 ====================

        AsyncTask task = asyncTaskService.getById(taskId);
        if (task == null) {
            log.error("任务不存在。taskId:{}", taskId);
            return;
        }

        log.info("收到任务完成消息，bizId: {}", bizId);

        // 检查任务状态，避免重复处理
        if (AsyncTaskStatus.DONE.getCode().equals(task.getTaskStatus())) {
            log.info("任务已完成，无需重复处理，taskId: {}", taskId);
            markMessageAsProcessed(taskId); // 标记为已处理
            return;
        }

        try {

            // ==================== 4. 执行业务逻辑 ====================
            processAuditTask(bizId, task);

            // ==================== 5. 标记消息为已处理 ====================
            markMessageAsProcessed(taskId);

            log.info("审核任务处理成功，bizId: {}, taskId: {}", bizId, taskId);

        } catch (Exception e) {
            log.error("处理任务完成消息异常，bizId: {}", bizId, e);
            // 记录异常，用于监控
            // 可以根据异常类型决定是否重试
            if (shouldRetry(e)) {
                // 抛出异常，触发重试
                throw new RuntimeException("审核任务处理失败，需要重试", e);
            } else {
                // 业务异常，不需要重试，更新任务状态为失败
                handleFailure(task, e.getMessage());
                markMessageAsProcessed(taskId); // 标记为已处理（失败状态）
            }
        }
    }

    /**
     * 幂等性检查
     */
    private boolean isMessageAlreadyProcessed(String taskId) {
        // TODO，待业务量大的时候再实现
        // String key = String.format(PROCESSED_MESSAGE_KEY, taskId);
        // return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        return true;
    }

    /**
     * 标记消息为已处理
     */
    private void markMessageAsProcessed(String taskId) {
        // TODO，待业务量大的时候再实现
        // String key = String.format(PROCESSED_MESSAGE_KEY, taskId);
        // 设置24小时过期，防止Redis中积累过多key
        // redisTemplate.opsForValue().set(key, "1", java.time.Duration.ofHours(24));
    }

    @Transactional(rollbackFor = Exception.class)
    protected void processAuditTask(String bizId, AsyncTask task) {
        // 1. 获取商品修订版本
        GoodsRevision goodsRevision = goodsRevisionService.getById(bizId);
        if (goodsRevision == null) {
            throw new RuntimeException("错误的bizId参数，找不到对应的 GoodsRevision，bizId: " + bizId);
        }

        // 2. 验证当前状态是否允许设置为 APPROVING
        validateRevisionStatus(goodsRevision);

        // 3. 更新商品修订状态为审核中
        goodsRevision.setRevStatus(RevStatus.APPROVING.getCode());
        goodsRevision.setUpdateTime(DateUtils.getNowDate());

        boolean updated = goodsRevisionService.updateById(goodsRevision);
        if (!updated) {
            throw new RuntimeException("更新 GoodsRevision 状态失败，bizId: " + bizId);
        }

        // 4. 更新异步任务状态为完成
        task.setTaskStatus(AsyncTaskStatus.DONE.getCode());
        task.setFinishTime(DateUtils.getNowDate());

        boolean taskUpdated = asyncTaskService.updateById(task);
        if (!taskUpdated) {
            throw new RuntimeException("更新 AsyncTask 状态失败，taskId: " + task.getTaskId());
        }

        log.info("业务处理完成，商品修订状态更新为审核中，bizId: {}", bizId);
    }

    /**
     * 验证商品修订状态
     */
    private void validateRevisionStatus(GoodsRevision goodsRevision) {
        String currentStatus = goodsRevision.getRevStatus();

        // 只能从特定状态转为 APPROVING
        if (!RevStatus.EDITING.getCode().equals(currentStatus)) {
            throw new RuntimeException(
                    String.format("当前状态不允许设置为审核中，当前状态: %s，bizId: %s",
                            currentStatus, goodsRevision.getRevisionId()));
        }
    }

    /**
     * 判断是否应该重试
     */
    private boolean shouldRetry(Exception e) {
        // 网络异常、数据库连接异常等可以重试
        // 业务逻辑异常不应该重试
        if (e instanceof RuntimeException && e.getMessage().contains("业务")) {
            return false;
        }
        return true;
    }

    /**
     * 处理失败情况
     */
    private void handleFailure(AsyncTask task, String errorMsg) {
        try {
            // task.setTaskStatus(AsyncTaskStatus.FAILED.getCode());
            // task.setFinishTime(DateUtils.getNowDate());
            // 可以添加错误信息字段（如果 AsyncTask 表有的话）
            // task.setErrorMsg(errorMsg);

            // asyncTaskService.updateAsyncTask(task);
            log.error("任务处理失败，taskId: {}, 错误信息: {}", task.getTaskId(), errorMsg);

        } catch (Exception ex) {
            log.error("更新任务失败状态异常，taskId: {}", task.getTaskId(), ex);
        }
    }

}
