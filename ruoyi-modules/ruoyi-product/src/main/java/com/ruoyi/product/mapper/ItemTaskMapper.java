package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.ItemTask;

/**
 * 任务实例Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface ItemTaskMapper 
{
    /**
     * 查询任务实例
     * 
     * @param taskId 任务实例主键
     * @return 任务实例
     */
    public ItemTask selectItemTaskByTaskId(String taskId);

    /**
     * 查询任务实例列表
     * 
     * @param itemTask 任务实例
     * @return 任务实例集合
     */
    public List<ItemTask> selectItemTaskList(ItemTask itemTask);

    /**
     * 新增任务实例
     * 
     * @param itemTask 任务实例
     * @return 结果
     */
    public int insertItemTask(ItemTask itemTask);

    /**
     * 修改任务实例
     * 
     * @param itemTask 任务实例
     * @return 结果
     */
    public int updateItemTask(ItemTask itemTask);

    /**
     * 删除任务实例
     * 
     * @param taskId 任务实例主键
     * @return 结果
     */
    public int deleteItemTaskByTaskId(String taskId);

    /**
     * 批量删除任务实例
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteItemTaskByTaskIds(String[] taskIds);
}
