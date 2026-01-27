package com.ruoyi.product.workflow.event;

import org.springframework.context.ApplicationEvent;

public class NodeCompletedEvent extends ApplicationEvent {
    private final String nodeInstanceId;

    public NodeCompletedEvent(Object source, String nodeInstanceId) {
        super(source);
        this.nodeInstanceId = nodeInstanceId;
    }

    public String getNodeInstanceId() {
        return nodeInstanceId;
    }
}