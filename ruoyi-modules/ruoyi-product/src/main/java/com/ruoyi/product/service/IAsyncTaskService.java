package com.ruoyi.product.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.product.constant.AsyncTaskCode;
import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.AsyncTask;

/**
 * 异步任务Service接口
 * 
 * @author Rupert
 * @date 2025-12-16
 */
public interface IAsyncTaskService extends IBaseService<AsyncTask>{
    

    String createTask(AsyncTaskCode taskCode, String shopID,
            String fileName, String fileUrl, Map<String, Object> ext);

    void updateProgress(String taskId, int successIncr, int skipIncr, int failureIncr);

    void finish(String taskId, String failFileUrl, String status);

    void retry(String taskId);
}
