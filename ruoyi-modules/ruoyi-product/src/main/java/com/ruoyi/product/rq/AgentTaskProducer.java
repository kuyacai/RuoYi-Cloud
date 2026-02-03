package com.ruoyi.product.rq;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ruoyi.product.domain.WfNodeInstance;
import com.ruoyi.product.rq.dto.AgentTaskMsg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AgentTaskProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.agent.exchange:agent.workflow.exchange}")
    private String exchangeName;

    @Value("${rabbit.agent.routing-key:agent.task.python.key}")
    private String routingKey;

    /**
     * 重构后的发送方法
     * 
     * @param node           节点实例对象
     * @param resolvedParams 经过 SpEL 解析后的真实参数
     */
    public void sendTask(WfNodeInstance node, Map<String, Object> resolvedParams) {
        if (node == null) {
            log.error("任务推送失败：节点对象为空");
            return;
        }

        // 构建发送给 Python 端的消息对象
        AgentTaskMsg msg = new AgentTaskMsg();
        // msg.setTaskNodeId(node.getNodeInstanceId());
        msg.setNodeInstanceId(node.getNodeInstanceId());
        msg.setWorkflowInstanceId(node.getWorkflowInstanceId());
        // 注意：此处使用枚举的 getValue() 或 getCode()，确保 Python 端能通过此字符串找到 Handler
        msg.setCapabilityId(node.getCapabilityId());
        msg.setCapabilityVersionId(node.getCapabilityVersionId());

        // 关键点：将解析后的动态参数放入消息体，Python 端的 input_params 将拿到真值
        msg.setInputParams(resolvedParams);

        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
            log.info("Successfully sent task to MQ: NodeID=[{}], Type=[{}],VersionId=[{}]",
                    node.getNodeInstanceId(), node.getCapabilityId(), node.getCapabilityVersionId());
        } catch (Exception e) {
            log.error("MQ 任务派发失败", e);
            // 抛出异常以触发上层 executeNode 的状态回滚或错误处理
            throw new RuntimeException("发送任务到队列失败: " + e.getMessage());
        }
    }
}