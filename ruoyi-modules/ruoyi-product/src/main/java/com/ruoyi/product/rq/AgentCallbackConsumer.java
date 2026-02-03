package com.ruoyi.product.rq;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.ruoyi.product.domain.WfNodeInstance;
import com.ruoyi.product.enums.NodeInstanceStatus;
import com.ruoyi.product.service.IWfNodeInstanceService;
import com.ruoyi.product.service.IWorkflowEngineService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @formatter:off

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentCallbackConsumer {


    private final IWorkflowEngineService workflowEngineService;
    private final IWfNodeInstanceService nodeInstanceService;

    /**
     * 监听 Python 端发回的任务完成回调
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "agent.task.callback.queue", durable = "true"),
            exchange = @Exchange(value = "agent.task.callback.exchange", type = ExchangeTypes.DIRECT),
            key = "agent.task.callback.key"
    ))
    public void onCallbackMessage(java.util.Map<String, Object> payload, Channel channel, Message amqpMessage) throws IOException {
        log.info("📩 收到 Python 端任务回调回执: {}", payload);
    try {
        // 从 Map 中获取字段
        String nodeInstanceId = (String) payload.get("node_instance_id");
        String status = (String) payload.get("status");
        Map<String, Object> data = (Map<String, Object>) payload.get("data");
        String errorMsg = (String) payload.get("errorMsg");

        log.info("📩 收到回调回执: Node={}, Status={}", nodeInstanceId, status);

        WfNodeInstance node = nodeInstanceService.getById(nodeInstanceId);

        if (node != null) {
                // 1. 回填输出数据 (这是 SpEL 能拿到数据的关键)
                if (data instanceof Map) {
                    node.setOutputData((Map<String, Object>) data);
                }
                // 2. 状态判断：使用枚举 code
                if (NodeInstanceStatus.SUCCESS.getCode().equalsIgnoreCase(status)) {
                    node.setStatus(NodeInstanceStatus.SUCCESS);
                    nodeInstanceService.updateById(node);
                    workflowEngineService.getNextNode(nodeInstanceId);
                    
                } else if (NodeInstanceStatus.AWAITING_HUMAN.getCode().equalsIgnoreCase(status)) {
                    // 处理人工挂起场景
                    node.setStatus(NodeInstanceStatus.AWAITING_HUMAN);
                    node.setErrorMsg(errorMsg);
                    nodeInstanceService.updateById(node);
                    log.info("⏳ 节点 {} 进入人工等待状态", nodeInstanceId);

                } else {
                    // 默认为失败
                    node.setStatus(NodeInstanceStatus.FAILED);
                    node.setErrorMsg(errorMsg);
                    nodeInstanceService.updateById(node);
                }
            }

        channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
    } catch (Exception e) {
        log.error("❌ 处理流转异常", e);
        channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, false);
    }
    }
}