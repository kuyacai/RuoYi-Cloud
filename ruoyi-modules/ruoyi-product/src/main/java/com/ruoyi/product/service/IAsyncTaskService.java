package com.ruoyi.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.product.domain.AsyncTask;

/**
 * 异步任务Service接口
 * 
 * @author Rupert
 * @date 2025-12-16
 */
public interface IAsyncTaskService 
{
    /**
     * 查询异步任务
     * 
     * @param taskId 异步任务主键
     * @return 异步任务
     */
    public AsyncTask selectAsyncTaskByTaskId(String taskId);

    /**
     * 查询异步任务列表
     * 
     * @param asyncTask 异步任务
     * @return 异步任务集合
     */
    public List<AsyncTask> selectAsyncTaskList(AsyncTask asyncTask);

    /**
     * 新增异步任务
     * 
     * @param asyncTask 异步任务
     * @return 结果
     */
    public int insertAsyncTask(AsyncTask asyncTask);

    /**
     * 修改异步任务
     * 
     * @param asyncTask 异步任务
     * @return 结果
     */
    public int updateAsyncTask(AsyncTask asyncTask);

    /**
     * 批量删除异步任务
     * 
     * @param taskIds 需要删除的异步任务主键集合
     * @return 结果
     */
    public int deleteAsyncTaskByTaskIds(String[] taskIds);

    /**
     * 删除异步任务信息
     * 
     * @param taskId 异步任务主键
     * @return 结果
     */
    public int deleteAsyncTaskByTaskId(String taskId);

    String createTask(String taskCode, String taskName,
        String fileName, String fileUrl, Map<String, String> ext);
    void updateProgress(String taskId, int successIncr, int skipIncr, int failureIncr);
    void finish(String taskId, String failFileUrl, String status);
    
    void retry(String taskId);
}
