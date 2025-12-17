package com.ruoyi.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.product.utils.ProductFileUtils;
import com.ruoyi.product.mapper.AsyncTaskMapper;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.constant.AsyncTaskStatus;
import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.domain.AsyncTask;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.common.core.utils.uuid.UUID;

/**
 * 异步任务Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-16
 */
@Service
public class AsyncTaskServiceImpl implements IAsyncTaskService {

    private static final Logger log = LoggerFactory.getLogger(AsyncTaskServiceImpl.class);

    private AsyncTaskMapper asyncTaskMapper;
    private final RocketMQTemplate mqTemplate;

    @Autowired
    public AsyncTaskServiceImpl(AsyncTaskMapper asyncTaskMapper,
            RocketMQTemplate mqTemplate) {
        this.asyncTaskMapper = asyncTaskMapper;
        this.mqTemplate = mqTemplate;
    }

    /**
     * 查询异步任务
     * 
     * @param taskId 异步任务主键
     * @return 异步任务
     */
    @Override
    public AsyncTask selectAsyncTaskByTaskId(String taskId) {
        return asyncTaskMapper.selectAsyncTaskByTaskId(taskId);
    }

    /**
     * 查询异步任务列表
     * 
     * @param asyncTask 异步任务
     * @return 异步任务
     */
    @Override
    public List<AsyncTask> selectAsyncTaskList(AsyncTask asyncTask) {
        return asyncTaskMapper.selectAsyncTaskList(asyncTask);
    }

    /**
     * 新增异步任务
     * 
     * @param asyncTask 异步任务
     * @return 结果
     */
    @Override
    public int insertAsyncTask(AsyncTask asyncTask) {
        asyncTask.setCreateTime(DateUtils.getNowDate());
        return asyncTaskMapper.insertAsyncTask(asyncTask);
    }

    /**
     * 修改异步任务
     * 
     * @param asyncTask 异步任务
     * @return 结果
     */
    @Override
    public int updateAsyncTask(AsyncTask asyncTask) {
        return asyncTaskMapper.updateAsyncTask(asyncTask);
    }

    /**
     * 批量删除异步任务
     * 
     * @param taskIds 需要删除的异步任务主键
     * @return 结果
     */
    @Override
    public int deleteAsyncTaskByTaskIds(String[] taskIds) {
        return asyncTaskMapper.deleteAsyncTaskByTaskIds(taskIds);
    }

    /**
     * 删除异步任务信息
     * 
     * @param taskId 异步任务主键
     * @return 结果
     */
    @Override
    public int deleteAsyncTaskByTaskId(String taskId) {
        return asyncTaskMapper.deleteAsyncTaskByTaskId(taskId);
    }

    @Override
    @Transactional
    public String createTask(String taskCode, String taskName,
        String fileName, String fileUrl, Map<String, String> ext) {
        String taskId = UUID.fastUUID().toString(true);
        AsyncTask task = new AsyncTask();
        task.setTaskId(taskId);
        task.setTaskCode(taskCode);
        task.setTaskName(taskName);
        task.setFileName(fileUrl);
        task.setTaskStatus(AsyncTaskStatus.INIT.getCode());
        task.setCreateTime(DateUtils.getNowDate());
        asyncTaskMapper.insertAsyncTask(task);

        // 保存文件到 minio/oss
        //String fileUrl = ProductFileUtils.upload(file, taskId);
        if (ext == null)
            ext = new HashMap<>();
        ext.put("fileUrl", fileUrl);
        String destination = "async-task-product-topic:sku_import";  // topic:tag 格式
        mqTemplate.asyncSend(destination,
                new AsyncTaskMsg(taskId, taskCode, ext),
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
        asyncTaskMapper.incrProgress(taskId, successIncr, skipIncr, failureIncr);
    }

    @Override
    public void finish(String taskId, String failFileUrl, String status) {
        AsyncTask t = new AsyncTask();
        t.setTaskId(taskId);
        t.setTaskStatus(status);
        t.setFailFileUrl(failFileUrl);
        t.setFinishTime(DateUtils.getNowDate());
        asyncTaskMapper.updateAsyncTask(t);
    }

    @Override
    public void retry(String taskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retry'");
    }

}
