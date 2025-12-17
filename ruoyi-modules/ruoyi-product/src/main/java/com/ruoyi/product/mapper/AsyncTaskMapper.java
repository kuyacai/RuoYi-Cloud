package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.AsyncTask;

/**
 * 异步任务Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-16
 */
public interface AsyncTaskMapper 
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
     * 删除异步任务
     * 
     * @param taskId 异步任务主键
     * @return 结果
     */
    public int deleteAsyncTaskByTaskId(String taskId);

    /**
     * 批量删除异步任务
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAsyncTaskByTaskIds(String[] taskIds);

    void incrProgress(String taskId, int successIncr, int skipIncr, int failureIncr);
}
