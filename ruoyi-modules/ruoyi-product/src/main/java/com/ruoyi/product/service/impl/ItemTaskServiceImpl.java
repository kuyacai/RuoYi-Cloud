package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ItemTaskMapper;
import com.ruoyi.product.domain.ItemTask;
import com.ruoyi.product.service.IItemTaskService;

/**
 * 任务实例Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ItemTaskServiceImpl implements IItemTaskService 
{
    @Autowired
    private ItemTaskMapper itemTaskMapper;

    /**
     * 查询任务实例
     * 
     * @param taskId 任务实例主键
     * @return 任务实例
     */
    @Override
    public ItemTask selectItemTaskByTaskId(String taskId)
    {
        return itemTaskMapper.selectItemTaskByTaskId(taskId);
    }

    /**
     * 查询任务实例列表
     * 
     * @param itemTask 任务实例
     * @return 任务实例
     */
    @Override
    public List<ItemTask> selectItemTaskList(ItemTask itemTask)
    {
        return itemTaskMapper.selectItemTaskList(itemTask);
    }

    /**
     * 新增任务实例
     * 
     * @param itemTask 任务实例
     * @return 结果
     */
    @Override
    public int insertItemTask(ItemTask itemTask)
    {
        return itemTaskMapper.insertItemTask(itemTask);
    }

    /**
     * 修改任务实例
     * 
     * @param itemTask 任务实例
     * @return 结果
     */
    @Override
    public int updateItemTask(ItemTask itemTask)
    {
        return itemTaskMapper.updateItemTask(itemTask);
    }

    /**
     * 批量删除任务实例
     * 
     * @param taskIds 需要删除的任务实例主键
     * @return 结果
     */
    @Override
    public int deleteItemTaskByTaskIds(String[] taskIds)
    {
        return itemTaskMapper.deleteItemTaskByTaskIds(taskIds);
    }

    /**
     * 删除任务实例信息
     * 
     * @param taskId 任务实例主键
     * @return 结果
     */
    @Override
    public int deleteItemTaskByTaskId(String taskId)
    {
        return itemTaskMapper.deleteItemTaskByTaskId(taskId);
    }
}
