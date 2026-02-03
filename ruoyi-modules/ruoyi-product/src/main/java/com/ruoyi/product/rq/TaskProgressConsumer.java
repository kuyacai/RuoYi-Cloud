package com.ruoyi.product.rq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ruoyi.product.rq.dto.AgentProgressMsg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TaskProgressConsumer {

    /**
     * 直接接收实体对象。
     * Spring AMQP 会自动调用配置好的 MessageConverter (通常是 Jackson2JsonMessageConverter)
     * 将 RabbitMQ 中的 JSON 字符串转换为 AgentProgressMsg 对象。
     */
    @RabbitListener(queues = "agent.task.progress.queue")
    public void handleProgress(AgentProgressMsg progressMsg) {
        if (progressMsg == null || progressMsg.getNodeInstanceId() == null) {
            log.warn("⚠️ 收到空的进度消息，跳过处理");
            return;
        }

        // 1. 打印结构化日志
        log.info("📥 [进度报到] 实例: {}, 进度: {}%, 状态: {}, 数据摘要: {}",
                progressMsg.getNodeInstanceId(),
                progressMsg.getProgress(),
                progressMsg.getStatus(),
                progressMsg.getData() != null ? progressMsg.getData().keySet() : "empty");

        // 2. 详细日志（调试用）
        if (log.isDebugEnabled()) {
            log.debug("🔍 完整进度详情: {}", progressMsg);
        }

        try {
            // 3. TODO: 调用业务 Service 更新数据库
            // workflowService.updateInstanceProgress(
            // progressMsg.getNodeInstanceId(),
            // progressMsg.getProgress(),
            // progressMsg.getStatus(),
            // progressMsg.getData()
            // );
        } catch (Exception e) {
            log.error("❌ 更新实例 [{}] 进度到数据库失败: {}",
                    progressMsg.getNodeInstanceId(), e.getMessage());
        }
    }
}