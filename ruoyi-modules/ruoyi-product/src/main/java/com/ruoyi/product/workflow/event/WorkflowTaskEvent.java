package com.ruoyi.product.workflow.event;

import org.springframework.context.ApplicationEvent;

/**
 * 工作流任务触发事件
 */
public class WorkflowTaskEvent extends ApplicationEvent {
    private final String nodeInstanceId;

    public WorkflowTaskEvent(Object source, String nodeInstanceId) {
        super(source);
        this.nodeInstanceId = nodeInstanceId;
    }

    public String getNodeInstanceId() {
        return nodeInstanceId;
    }
}