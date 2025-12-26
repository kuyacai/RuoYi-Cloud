package com.ruoyi.product.service;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.ItemTask;

/**
 * 任务实例Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IItemTaskService extends IBaseService<ItemTask> {

    /**
     * 新增任务实例并,同步更新task_counter
     * @param itemTask
     * @return
     */
    boolean insertItemTask(ItemTask itemTask);

    /**
     * 修改任务实例,并同步更新task_counter。
     * 若任务全部完成，将发送任务全部完成的消息。
     * @param itemTask
     * @return
     */
    boolean updateItemTask(ItemTask itemTask);
    /**
     * 根据业务ID和任务代码查询单个任务实例
     * * @param bizId 业务ID
     * 
     * @param taskCode 任务代码
     * @return 匹配的任务实例，若不存在则返回null
     */
    ItemTask getByBizIdAndCode(String bizId, String taskCode);

    /**
     * 统计指定业务ID和任务代码的任务数量
     * * @param bizId 业务ID
     * 
     * @param taskCode 任务代码
     * @return 符合条件的记录总数
     */
    int countByBizIdAndCode(String bizId, String taskCode);
}
