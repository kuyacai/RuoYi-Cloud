package com.ruoyi.product.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.AsyncTask;
import com.ruoyi.product.enums.AsyncTaskStatus;
import com.ruoyi.product.mapper.AsyncTaskMapper;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.product.service.excel.ExcelDataHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步任务Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncTaskServiceImpl extends BaseServiceImpl<AsyncTaskMapper, AsyncTask> implements IAsyncTaskService {

    private final RocketMQTemplate mqTemplate;

    @Override
    @Transactional
    public String createTask(AsyncTaskCode taskCode, String shopID,
            String fileName, String fileUrl, Map<String, Object> ext) {

        String taskId = UUID.fastUUID().toString(true);
        AsyncTask task = new AsyncTask();
        task.setTaskId(taskId);
        task.setTaskCode(taskCode.getCode());
        task.setTaskName(taskCode.getLabel());
        task.setShopId(shopID);

        if (StringUtils.isNotEmpty(fileName)) {
            task.setFileName(fileName);
        }
        if (StringUtils.isNotEmpty(fileUrl)) {
            task.setImportedFileUrl(fileUrl);
        }
        if (ext != null) {
            task.setParamsMap(ext);
        }

        task.setTaskStatus(AsyncTaskStatus.INIT);

        // 保存任务（在事务内）
        this.save(task);

        // 准备消息内容
        if (ext == null) {
            ext = new HashMap<>();
        }
        if (StringUtils.isNotEmpty(fileUrl)) {
            ext.put("fileUrl", fileUrl);
        }
        if (StringUtils.isNotEmpty(shopID)) {
            ext.put("shopId", shopID);
        }

        final Map<String, Object> finalExt = ext;
        final String finalTaskId = taskId;

        // 注册事务同步器，在事务提交后发送消息
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        // 事务提交后才执行
                        log.info("事务已提交，开始发送MQ消息: taskId={}", finalTaskId);

                        String destination = MQConstant.AsyncTaskProductTopic + ":" + taskCode.getCode();
                        mqTemplate.asyncSend(destination,
                                new AsyncTaskMsg(finalTaskId, taskCode.getCode(), finalExt),
                                new SendCallback() {
                                    @Override
                                    public void onSuccess(SendResult sendResult) {
                                        log.info("消息投递成功：{}", sendResult.getMsgId());
                                    }

                                    @Override
                                    public void onException(Throwable e) {
                                        log.error("消息投递失败，taskId: {}", finalTaskId, e);
                                        // 可以考虑记录失败，后续补偿
                                    }
                                });
                    }

                    @Override
                    public void afterCompletion(int status) {
                        // 事务完成后执行（无论提交还是回滚）
                        if (status == TransactionSynchronization.STATUS_COMMITTED) {
                            log.debug("事务提交完成: taskId={}", finalTaskId);
                        } else if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                            log.warn("事务回滚，不发送消息: taskId={}", finalTaskId);
                        }
                    }
                });

        log.info("任务创建完成（消息将在事务提交后发送）: taskId={}", taskId);
        return taskId;
    }

    @Override
    public void updateProgress(String taskId, int successIncr, int skipIncr, int failureIncr) {
        log.debug("updateProgress 开始: taskId={}, success+{}, skip+{}, failure+{}",
                taskId, successIncr, skipIncr, failureIncr);

        // 先查询当前状态
        AsyncTask before = this.getById(taskId);
        if (before != null) {
            log.debug("更新前 paramsMap: {}", before.getParamsMap());
        }
        // 使用 LambdaUpdateWrapper 实现更优雅的局部更新
        this.update(new LambdaUpdateWrapper<AsyncTask>()
                .eq(AsyncTask::getTaskId, taskId)
                // 使用 SQL 表达式进行原子递增
                .setSql("success = success + " + successIncr)
                .setSql("skip = skip + " + skipIncr)
                .setSql("failure = failure + " + failureIncr));
        // 更新后查询
        AsyncTask after = this.getById(taskId);
        if (after != null) {
            log.debug("更新后 paramsMap: {}", after.getParamsMap());
            log.debug("更新后 success: {}, skip: {}, failure: {}",
                    after.getSuccess(), after.getSkip(), after.getFailure());
        }
    }

    @Override
    public void finish(String taskId, String failFileUrl, AsyncTaskStatus status) {
        // 先查询当前状态
        AsyncTask before = this.getById(taskId);
        if (before != null) {
            log.debug("更新前 paramsMap: {}", before.getParamsMap());
        }
        // 创建 UpdateWrapper
        UpdateWrapper<AsyncTask> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("task_id", taskId)
                .set("task_status", status)
                .set("fail_file_url", failFileUrl)
                .set("finish_time", DateUtils.getNowDate());
        this.update(updateWrapper);
        // 更新后查询
        AsyncTask after = this.getById(taskId);
        if (after != null) {
            log.debug("更新后 paramsMap: {}", after.getParamsMap());
            log.debug("更新后 success: {}, skip: {}, failure: {}",
                    after.getSuccess(), after.getSkip(), after.getFailure());
        }
    }

    @Override
    public void retry(String taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retry'");
    }

    // 在 Controller 或具体的业务 Service 中调用
    public String submitExportTask(AsyncTaskCode taskCode, String shopId, Map<String, Object> params) {
        // 1. 校验参数
        ExcelDataHandler<?> handler = SpringUtils.getBean(taskCode.getCode() + "_HANDLER");
        handler.validateParams(params);

        // 2. 复用你现有的 createTask 方法
        // 它会自动生成 taskId, 保存数据库, 并发送 RocketMQ 消息
        return createTask(taskCode, shopId, null, null, params);
    }

}
