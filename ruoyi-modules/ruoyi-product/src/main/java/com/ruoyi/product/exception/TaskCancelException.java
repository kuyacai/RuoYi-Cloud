package com.ruoyi.product.exception;

/**
 * 异步任务取消异常
 */
public class TaskCancelException extends RuntimeException {
    private final String taskId;

    public TaskCancelException(String taskId) {
        super("任务已取消: " + taskId);
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }
}