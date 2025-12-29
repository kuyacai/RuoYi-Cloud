package com.ruoyi.product.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.SpringUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.constant.AsyncTaskStatus;
import com.ruoyi.product.constant.MQConstant;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.AsyncTask;
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
        if (StringUtils.isNotEmpty(fileName)) {
            task.setFileName(fileName);
        }
        if (StringUtils.isNotEmpty(fileUrl)) {
            task.setImportedFileUrl(fileUrl);
        }
        if (ext != null)
            task.setParamsMap(ext);

        task.setTaskStatus(AsyncTaskStatus.INIT.getCode());
        // task.setGmtCreate(DateUtils.getNowDate());

        // 直接使用继承自 BaseServiceImpl 的 save 方法
        this.save(task);

        // 保存文件到 minio/oss
        // String fileUrl = ProductFileUtils.upload(file, taskId);
        if (ext == null)
            ext = new HashMap<>();
        if (StringUtils.isNotEmpty(fileUrl)) {
            ext.put("fileUrl", fileUrl);
        }
        if (StringUtils.isNotEmpty(shopID)) {
            ext.put("shopId", shopID);
        }
        String destination = MQConstant.AsyncTaskProductTopic + ":" + taskCode.getCode(); // topic:tag 格式
        mqTemplate.asyncSend(destination,
                new AsyncTaskMsg(taskId, taskCode.getCode(), ext),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("消息投递成功：{}", sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        log.error("消息投递失败", e);
                    }
                });

        return taskId;
    }

    @Override
    public void updateProgress(String taskId, int successIncr, int skipIncr, int failureIncr) {
        // 使用 LambdaUpdateWrapper 实现更优雅的局部更新
        this.update(new LambdaUpdateWrapper<AsyncTask>()
                .eq(AsyncTask::getTaskId, taskId)
                .set(AsyncTask::getSuccess, successIncr)
                .set(AsyncTask::getSkip, skipIncr)
                .set(AsyncTask::getFailure, failureIncr));
    }

    @Override
    public void finish(String taskId, String failFileUrl, String status) {
        AsyncTask t = new AsyncTask();
        t.setTaskId(taskId);
        t.setTaskStatus(status);
        t.setFailFileUrl(failFileUrl);
        t.setFinishTime(DateUtils.getNowDate());
        // updateById 会忽略 null 字段，只更新赋值了的字段
        this.updateById(t);
    }

    @Override
    public void retry(String taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retry'");
    }

    // 在 Controller 或具体的业务 Service 中调用
    public String submitExportTask(AsyncTaskCode taskCode, String shopId, Map<String, Object> params) {
        // 1. 校验参数
        ExcelDataHandler<?> handler = SpringUtils.getBean(taskCode.getCode() + "Handler");
        handler.validateParams(params);

        // 2. 复用你现有的 createTask 方法
        // 它会自动生成 taskId, 保存数据库, 并发送 RocketMQ 消息
        return createTask(taskCode, shopId, null, null, params);
    }

}
