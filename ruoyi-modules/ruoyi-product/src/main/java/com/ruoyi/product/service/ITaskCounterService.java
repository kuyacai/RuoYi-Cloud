package com.ruoyi.product.service;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.TaskCounter;
import java.util.Date;

/**
 * 任务计数器Service接口
 */
public interface ITaskCounterService extends IBaseService<TaskCounter> {

    /**
     * 递增总任务数
     * 若记录不存在则初始化，若存在则 total_tasks + 1
     * @param bizId 业务ID
     */
    void incrementTotal(String bizId);

    /**
     * 递减总任务数
     * 确保总数不小于 0
     * @param bizId 业务ID
     */
    void decrementTotal(String bizId);

    /**
     * 递增已完成任务数
     * 确保已完成数不超过总任务数
     * @param bizId 业务ID
     */
    void incrementCompleted(String bizId);

    /**
     * 递减已完成任务数
     * 确保不小于 0
     * @param bizId 业务ID
     */
    void decrementCompleted(String bizId);

    /**
     * 更新通知状态
     * @param bizId 业务ID
     * @param status 状态代码
     * @param notifiedTime 通知时间
     */
    void updateNotifiedStatus(String bizId, String status, Date notifiedTime);

    /**
     * 根据业务ID获取计数器详情
     * @param bizId 业务ID
     * @return 计数器实体
     */
    TaskCounter selectByBizId(String bizId);
}