package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.product.mapper.TaskCounterMapper;
import com.ruoyi.product.domain.TaskCounter;
import com.ruoyi.product.service.ITaskCounterService;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import java.util.Date;

/**
 * 任务计数器Service业务层处理
 */
@Service
public class TaskCounterServiceImpl extends BaseServiceImpl<TaskCounterMapper, TaskCounter> implements ITaskCounterService {

    @Override
    @Transactional
    public void incrementTotal(String bizId) {
        // 使用原生 SQL 处理 ON DUPLICATE KEY 逻辑，或先查询判断
        // 鉴于 MP 常规写法的限制，此处推荐使用 setSql 保证原子累加
        boolean exists = this.getById(bizId) != null;
        if (!exists) {
            TaskCounter counter = new TaskCounter();
            counter.setBizId(bizId);
            counter.setTotalTasks(1);
            counter.setCompletedTasks(0);
            this.save(counter);
        } else {
            this.update()
                .setSql("total_tasks = total_tasks + 1")
                .eq("biz_id", bizId)
                .update();
        }
    }

    @Override
    @Transactional
    public void decrementTotal(String bizId) {
        this.update()
            .setSql("total_tasks = GREATEST(total_tasks - 1, 0)")
            .eq("biz_id", bizId)
            .update();
    }

    @Override
    @Transactional
    public void incrementCompleted(String bizId) {
        this.update()
            .setSql("completed_tasks = LEAST(completed_tasks + 1, total_tasks)")
            .eq("biz_id", bizId)
            .update();
    }

    @Override
    @Transactional
    public void decrementCompleted(String bizId) {
        this.update()
            .setSql("completed_tasks = GREATEST(completed_tasks - 1, 0)")
            .eq("biz_id", bizId)
            .update();
    }

    @Override
    @Transactional
    public void updateNotifiedStatus(String bizId, String status, Date notifiedTime) {
        this.update()
            .set("notified_status", status)
            .set("notified_time", notifiedTime)
            .eq("biz_id", bizId)
            .update();
    }

    @Override
    public TaskCounter selectByBizId(String bizId) {
        return this.getById(bizId);
    }
}