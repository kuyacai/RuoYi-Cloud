package com.ruoyi.product.service.impl;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.product.domain.WfNodeDefinition;
import com.ruoyi.product.domain.WfNodeInstance;
import com.ruoyi.product.domain.WfWorkflowInstance;
import com.ruoyi.product.enums.HandlerType;
import com.ruoyi.product.enums.ManualStatus;
import com.ruoyi.product.enums.NodeInstanceStatus;
import com.ruoyi.product.enums.WorkflowStatus;
import com.ruoyi.product.mapper.WfNodeDefinitionMapper;
import com.ruoyi.product.mapper.WfNodeInstanceMapper;
import com.ruoyi.product.mapper.WfWorkflowInstanceMapper;
import com.ruoyi.product.rq.AgentTaskProducer;
import com.ruoyi.product.service.IWorkflowEngineService;
import com.ruoyi.product.workflow.engine.parser.WorkflowParameterParser;
import com.ruoyi.product.workflow.event.NodeCompletedEvent;
import com.ruoyi.product.workflow.event.WorkflowTaskEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowEngineServiceImpl implements IWorkflowEngineService {

    // 直接注入 Mapper 避免 Service 间的循环引用
    private final WfNodeInstanceMapper nodeInstanceMapper;
    private final WfWorkflowInstanceMapper workflowInstanceMapper;
    private final WfNodeDefinitionMapper nodeDefinitionMapper;

    private final WorkflowParameterParser parameterParser;
    private final AgentTaskProducer agentTaskProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getNextNode(String workflowInstanceId) {
        WfWorkflowInstance instance = workflowInstanceMapper.selectById(workflowInstanceId);
        if (instance == null || WorkflowStatus.COMPLETED.equals(instance.getStatus())) {
            return;
        }

        // 1. 获取当前节点的 Order
        Integer currentOrder = 0;
        if (instance.getCurrentNodeId() != null) {
            WfNodeInstance currentNode = nodeInstanceMapper.selectById(instance.getCurrentNodeId());
            if (currentNode != null) {
                currentOrder = currentNode.getNodeOrder();
            }
        }

        // 2. 查找下一个节点定义
        WfNodeDefinition nextDef = nodeDefinitionMapper.selectOne(new LambdaQueryWrapper<WfNodeDefinition>()
                .eq(WfNodeDefinition::getDefinitionId, instance.getDefinitionId())
                .gt(WfNodeDefinition::getNodeOrder, currentOrder)
                .orderByAsc(WfNodeDefinition::getNodeOrder)
                .last("LIMIT 1"));

        if (nextDef == null) {
            // 没有下一个节点，流程完成
            instance.setStatus(WorkflowStatus.COMPLETED);
            workflowInstanceMapper.updateById(instance);
            log.info("🏁 工作流实例 {} 已全部执行完毕", workflowInstanceId);
            return;
        }

        // 3. 创建节点实例
        String nodeInstanceId = UUID.fastUUID().toString(true);
        WfNodeInstance nodeInstance = new WfNodeInstance();
        nodeInstance.setNodeInstanceId(nodeInstanceId);
        nodeInstance.setWorkflowInstanceId(workflowInstanceId);
        nodeInstance.setCapabilityId(nextDef.getCapabilityId());
        nodeInstance.setCapabilityVersionId(nextDef.getCapabilityVersionId());
        nodeInstance.setNodeName(nextDef.getNodeName());
        nodeInstance.setNodeOrder(nextDef.getNodeOrder());
        nodeInstance.setHandlerType(nextDef.getHandlerType());
        nodeInstance.setStatus(NodeInstanceStatus.INIT);
        nodeInstance.setManualStatus(nextDef.getManualStatus());
        // 初始拷贝：此时还是带占位符的模板
        nodeInstance.setInputParams(nextDef.getDefaultParams());

        nodeInstanceMapper.insert(nodeInstance);

        // 4. 更新流程指针并触发执行
        instance.setCurrentNodeId(nodeInstanceId);
        workflowInstanceMapper.updateById(instance);

        log.info("📌 流转至下一节点: {} (Order: {} nodeInstanceId:{})", nextDef.getNodeName(), nextDef.getNodeOrder(),
                nodeInstanceId);
        this.executeNode(nodeInstanceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeNode(String nodeInstanceId) {
        WfNodeInstance node = nodeInstanceMapper.selectById(nodeInstanceId);
        if (node == null)
            return;

        // 1. 【关键修改】：解析参数。ParameterConfig 会从上游节点的 outputData 中取值。
        // 解析后得到的 resolvedParams 是已经将 #{#node1...} 替换为真实数据的 Map
        Map<String, Object> resolvedParams = parameterParser.parse(nodeInstanceId, node.getInputParams());

        // 2. 【持久化真实参数】：将解析后的值写回数据库，方便 UI 查看和后续重试
        node.setInputParams(resolvedParams);

        // 3. 判断是否需要人工干预
        if (ManualStatus.YES.equals(node.getManualStatus())) {
            log.info("✋ 节点 {} 是人工节点，挂起等待", node.getNodeName());
            node.setStatus(NodeInstanceStatus.AWAITING_HUMAN);
            nodeInstanceMapper.updateById(node);
            return;
        }

        // 4. 更新状态为运行中并保存解析后的参数
        node.setStatus(NodeInstanceStatus.RUNNING);
        nodeInstanceMapper.updateById(node);

        log.info("🚀 准备执行算子: {} (Type: {})", node.getCapabilityVersionId(), node.getHandlerType());

        // 5. 根据处理器类型执行
        if (HandlerType.PYTHON_AGENT.equals(node.getHandlerType())) {
            // 发送给 MQ，传参使用已经解析好的真实值
            agentTaskProducer.sendTask(node, resolvedParams);
        } else {
            // 其他类型处理器（如 JAVA_LOCAL）在此扩展
            log.warn("⚠️ 暂不支持的处理器类型: {}", node.getHandlerType());
        }
    }

    @EventListener
    public void handleNodeCompleted(NodeCompletedEvent event) {
        log.info("📩 收到节点完成信号，准备寻找后续节点: {}", event.getNodeInstanceId());
        WfNodeInstance node = nodeInstanceMapper.selectById(event.getNodeInstanceId());
        if (node != null) {
            this.getNextNode(node.getWorkflowInstanceId());
        }
    }

    @EventListener
    public void handleWorkflowTask(WorkflowTaskEvent event) {
        log.info("📩 收到任务执行信号: {}", event.getNodeInstanceId());
        this.executeNode(event.getNodeInstanceId());
    }
}