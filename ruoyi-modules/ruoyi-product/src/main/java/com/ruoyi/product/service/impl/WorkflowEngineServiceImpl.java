package com.ruoyi.product.service.impl;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.domain.WfNodeInstance;
import com.ruoyi.product.domain.WfWorkflowInstance;
import com.ruoyi.product.enums.HandlerType;
import com.ruoyi.product.enums.NodeInstanceStatus;
import com.ruoyi.product.enums.WorkflowStatus;
import com.ruoyi.product.rq.AgentTaskProducer; // 假设这是发送 MQ 的类
import com.ruoyi.product.service.IWfNodeInstanceService;
import com.ruoyi.product.service.IWfWorkflowInstanceService;
import com.ruoyi.product.service.IWorkflowEngineService;
import com.ruoyi.product.workflow.engine.parser.WorkflowParameterParser;
import com.ruoyi.product.workflow.event.WorkflowTaskEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowEngineServiceImpl implements IWorkflowEngineService {

    private final IWfNodeInstanceService nodeInstanceService;
    private final IWfWorkflowInstanceService workflowInstanceService;
    private final WorkflowParameterParser parameterParser;
    private final AgentTaskProducer agentTaskProducer;

    @Override
    public void getNextNode(String currentNodeInstanceId) {
        // 1. 获取当前节点信息
        WfNodeInstance currentNode = nodeInstanceService.getById(currentNodeInstanceId);
        String instanceId = currentNode.getWorkflowInstanceId();

        // 2. 寻找同一个实例下，序号比当前节点大 1 的下一个节点
        WfNodeInstance nextNode = nodeInstanceService.getOne(
                new LambdaQueryWrapper<WfNodeInstance>()
                        .eq(WfNodeInstance::getWorkflowInstanceId, instanceId)
                        .eq(WfNodeInstance::getNodeOrder, currentNode.getNodeOrder() + 1));

        if (nextNode != null) {
            log.info("⏭️ 找到下一个节点: [{}], Order: {}", nextNode.getCapabilityId(), nextNode.getNodeOrder());

            // 更新工作流实例当前运行到的节点 ID
            WfWorkflowInstance instance = workflowInstanceService.getById(instanceId);
            instance.setCurrentNodeId(nextNode.getNodeInstanceId());
            workflowInstanceService.updateById(instance);

            // 执行下一个节点
            this.executeNode(nextNode.getNodeInstanceId());
        } else {
            // 3. 没有下一个节点了，标记整个工作流完成
            log.info("🏁 工作流实例 {} 已跑完所有节点，任务结束。", instanceId);
            WfWorkflowInstance instance = workflowInstanceService.getById(instanceId);
            instance.setStatus(WorkflowStatus.COMPLETED); // 假设你有 COMPLETED 枚举
            workflowInstanceService.updateById(instance);
        }
    }

    @Override
    public void executeNode(String nodeInstanceId) {
        WfNodeInstance node = nodeInstanceService.getById(nodeInstanceId);

        // 1. 更新节点状态为运行中
        node.setStatus(NodeInstanceStatus.RUNNING);
        nodeInstanceService.updateById(node);

        log.info("🚀 准备执行节点: {} (ID: {})", node.getCapabilityId(), nodeInstanceId);

        // 2. 核心：解析 SpEL 变量得到最终执行参数
        Map<String, Object> resolvedParams = parameterParser.parse(nodeInstanceId, node.getInputParams());

        // 3. 使用枚举进行路由判断，消除硬编码
        if (HandlerType.PYTHON_AGENT.equals(node.getHandlerType())) {
            // 调用重构后的 sendTask，传入解析后的参数
            agentTaskProducer.sendTask(node, resolvedParams);
        } else if (HandlerType.JAVA_LOCAL.equals(node.getHandlerType())) {
            log.info("☕ 执行 Java 本地算子...");
            // TODO: 实现本地执行逻辑
        }
    }

    // 这是一个桥接方法：将“事件信号”转化为“执行动作”
    @EventListener
    public void handleWorkflowTask(WorkflowTaskEvent event) {
        // 这里的 this.executeNode 就是你类中原有的那个 executeNode 方法
        this.executeNode(event.getNodeInstanceId());
    }
}