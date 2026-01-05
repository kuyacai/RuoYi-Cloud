package com.ruoyi.product.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.ItemTask;
import com.ruoyi.product.domain.TaskCounter;
import com.ruoyi.product.enums.AsyncTaskCode;
import com.ruoyi.product.enums.ItemTaskStatus;
import com.ruoyi.product.exception.BusinessException;
import com.ruoyi.product.mapper.ItemTaskMapper;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.product.service.IItemTaskService;
import com.ruoyi.product.service.ITaskCounterService;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务实例Service业务层处理
 * * @author Rupert
 * 
 * @date 2025-12-13
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ItemTaskServiceImpl extends BaseServiceImpl<ItemTaskMapper, ItemTask> implements IItemTaskService {

    @Autowired
    private ITaskCounterService taskCounterService;

    @Autowired
    private IAsyncTaskService asyncTaskService;

    /**
     * 新增任务实例
     * 修改点：使用 MP 的 getOne 代替 Mapper 自定义查询，使用 save 代替 insert
     */
    @Override
    public boolean insertItemTask(ItemTask itemTask) {
        // 1. 参数校验
        validateItemTaskForInsert(itemTask);

        // 2. 检查是否已存在相同任务（同一biz_id + task_code）
        ItemTask existingTask = this.getByBizIdAndCode(itemTask.getBizId(), itemTask.getTaskCode().getCode());

        if (existingTask != null) {
            throw new BusinessException("同一业务ID和任务类型的任务已存在");
        }

        // 3. 插入任务记录（MP 自动处理 ID 和时间填充）
        boolean result = this.save(itemTask);

        if (result) {
            // 4. 更新计数器表（总任务数+1）
            handleTaskCreation(itemTask.getBizId(), itemTask.getTaskCode().getCode());
        }

        return result;
    }

    /**
     * 修改任务实例
     * 修改点：使用 getById 和 updateById
     */
    @Override
    public boolean updateItemTask(ItemTask itemTask) {
        // 1. 查询原任务状态
        ItemTask oldTask = this.getById(itemTask.getTaskId());
        if (oldTask == null) {
            throw new BusinessException("任务不存在，taskId: " + itemTask.getTaskId());
        }

        // 2. 如果biz_id或task_code被修改，需要特殊处理
        if (!oldTask.getBizId().equals(itemTask.getBizId()) ||
                !oldTask.getTaskCode().equals(itemTask.getTaskCode())) {
            throw new BusinessException("不允许修改业务ID或任务类型");
        }

        // 3. 更新任务记录
        boolean updated = this.updateById(itemTask);

        if (updated) {
            // 4. 根据状态变化更新计数器
            handleTaskStatusChange(oldTask, itemTask);
        }

        return updated;
    }

    /**
     * 处理任务状态变化
     */
    private void handleTaskStatusChange(ItemTask oldTask, ItemTask newTask) {
        String oldStatus = oldTask.getTaskStatus().getCode();
        String newStatus = newTask.getTaskStatus().getCode();

        if (Objects.equals(oldStatus, newStatus)) {
            return;
        }

        String bizId = newTask.getBizId();

        if (isBecomingDone(oldStatus, newStatus)) {
            handleTaskCompletion(bizId, true);
        } else if (isBecomingNotDone(oldStatus, newStatus)) {
            handleTaskCompletion(bizId, false);
        } else if (isBecomingCancelled(oldStatus, newStatus)) {
            handleTaskCancellation(bizId, oldStatus);
        }
    }

    /**
     * 处理任务完成（递增或递减已完成数）
     * 修改点：调用 taskCounterService 的原子增减方法
     */
    private void handleTaskCompletion(String bizId, boolean increment) {
        try {
            if (increment) {
                taskCounterService.incrementCompleted(bizId);
            } else {
                taskCounterService.decrementCompleted(bizId);
            }

            // 查询更新后的计数器状态
            TaskCounter counter = taskCounterService.selectByBizId(bizId);
            if (counter == null) {
                log.warn("计数器不存在，bizId: {}", bizId);
                return;
            }

            // 检查是否所有任务完成
            checkAllTasksCompleted(bizId, counter);
        } catch (Exception e) {
            log.error("处理任务完成计数器异常，bizId: {}", bizId, e);
        }
    }

    /**
     * 检查是否所有任务完成
     */
    private void checkAllTasksCompleted(String bizId, TaskCounter counter) {
        if (counter != null && counter.isAllCompleted()) {
            sendAllTasksCompletedMessage(bizId, counter.getTotalTasks());
            log.info("所有任务已完成，bizId: {}, 总任务数: {}", bizId, counter.getTotalTasks());
        } else {
            sendTasksNotAllCompletedMessage(bizId);
        }
    }

    /**
     * 处理任务取消
     */
    private void handleTaskCancellation(String bizId, String oldStatus) {
        if (!ItemTaskStatus.isCompletedStatus(oldStatus)) {
            taskCounterService.decrementTotal(bizId);
        } else {
            taskCounterService.decrementTotal(bizId);
            taskCounterService.decrementCompleted(bizId);
        }
    }

    /**
     * 处理任务创建
     */
    private void handleTaskCreation(String bizId, String taskCode) {
        // 原子更新总任务数
        taskCounterService.incrementTotal(bizId);

        TaskCounter counter = taskCounterService.selectByBizId(bizId);
        if (counter != null) {
            log.debug("任务创建计数器更新成功，bizId: {}, 总任务数: {}", bizId, counter.getTotalTasks());
        } else {
            throw new BusinessException("处理任务创建计数器异常，bizId: " + bizId);
        }
    }

    /**
     * 判定状态逻辑保持不变
     */
    private boolean isBecomingDone(String oldStatus, String newStatus) {
        return !isCompletedStatus(oldStatus) && isCompletedStatus(newStatus);
    }

    private boolean isBecomingNotDone(String oldStatus, String newStatus) {
        return isCompletedStatus(oldStatus) && !isCompletedStatus(newStatus);
    }

    private boolean isBecomingCancelled(String oldStatus, String newStatus) {
        return !ItemTaskStatus.CANCELLED.getCode().equals(oldStatus)
                && ItemTaskStatus.CANCELLED.getCode().equals(newStatus);
    }

    private boolean isCompletedStatus(String status) {
        return ItemTaskStatus.isCompletedStatus(status);
    }

    @Async
    public void sendTasksNotAllCompletedMessage(String bizId) {
    }

    private void validateItemTaskForInsert(ItemTask itemTask) {
        if (StringUtils.isBlank(itemTask.getBizId()))
            throw new IllegalArgumentException("业务ID不能为空");
        if (StringUtils.isBlank(itemTask.getTaskCode().getCode()))
            throw new IllegalArgumentException("任务类型不能为空");
        if (StringUtils.isBlank(itemTask.getTaskStatus().getCode()))
            throw new IllegalArgumentException("任务状态不能为空");
    }

    @Async
    public void sendAllTasksCompletedMessage(String bizId, Integer totalTasks) {
        Map<String, Object> ext = new HashMap<>();
        ext.put("bizId", bizId);
        ext.put("totalTasks", totalTasks.toString());
        String taskId = asyncTaskService.createTask(AsyncTaskCode.AUDIT_SPU_COMPREHENSIVE, null, null, null, ext);
        if (StringUtils.isEmpty(taskId)) {
            throw new BusinessException("sendAllTasksCompletedMessage异常，bizId: " + bizId);
        }
    }

    @Override
    public ItemTask getByBizIdAndCode(String bizId, String taskCode) {
        return this.getOne(new LambdaQueryWrapper<ItemTask>()
                .eq(ItemTask::getBizId, bizId)
                .eq(ItemTask::getTaskCode, taskCode)
                .last("LIMIT 1"));
    }

    @Override
    public int countByBizIdAndCode(String bizId, String taskCode) {
        return (int) this.count(new LambdaQueryWrapper<ItemTask>()
                .eq(ItemTask::getBizId, bizId)
                .eq(ItemTask::getTaskCode, taskCode));
    }
}