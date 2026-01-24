package com.ruoyi.product.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.WfNodeCapability;
import com.ruoyi.product.domain.WfNodeDefinition;
import com.ruoyi.product.domain.WfNodeInstance;
import com.ruoyi.product.domain.WfWorkflowDefinition;
import com.ruoyi.product.domain.WfWorkflowInstance;
import com.ruoyi.product.enums.ActiveStatus;
import com.ruoyi.product.enums.NodeInstanceStatus;
import com.ruoyi.product.enums.WorkflowStatus;
import com.ruoyi.product.mapper.WfWorkflowInstanceMapper;
import com.ruoyi.product.service.IWfNodeCapabilityService;
import com.ruoyi.product.service.IWfNodeDefinitionService;
import com.ruoyi.product.service.IWfNodeInstanceService;
import com.ruoyi.product.service.IWfWorkflowDefinitionService;
import com.ruoyi.product.service.IWfWorkflowInstanceService;
import com.ruoyi.product.workflow.event.WorkflowTaskEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WfWorkflowInstanceServiceImpl extends BaseServiceImpl<WfWorkflowInstanceMapper, WfWorkflowInstance>
                implements IWfWorkflowInstanceService {

        private final IWfWorkflowDefinitionService workflowDefinitionService;
        private final IWfNodeDefinitionService nodeDefinitionService;
        private final IWfNodeInstanceService nodeInstanceService;
        private final IWfNodeCapabilityService capabilityService;
        private final ApplicationEventPublisher eventPublisher;

        @Override
        @Transactional(rollbackFor = Exception.class)
        public String startWorkflow(String definitionId, String creator) {
                log.info("🎯 开始实例化工作流: {}, 启动人: {}", definitionId, creator);

                // 1. 校验工作流蓝图
                WfWorkflowDefinition definition = workflowDefinitionService.getById(definitionId);
                Assert.notNull(definition, "未找到工作流定义");
                // 使用正确的断言：必须为 ENABLED 才能继续
                Assert.isTrue(ActiveStatus.ENABLE.equals(definition.getActiveStatus()),
                                "该工作流定义已被禁用，无法启动");

                // 2. 获取节点定义
                List<WfNodeDefinition> nodeDefs = nodeDefinitionService.list(
                                new LambdaQueryWrapper<WfNodeDefinition>()
                                                .eq(WfNodeDefinition::getDefinitionId, definitionId)
                                                .orderByAsc(WfNodeDefinition::getNodeOrder));
                Assert.notEmpty(nodeDefs, "该工作流未配置任何节点");

                // 3. 核心校验：算子仓库检查
                // 提取所有节点依赖的能力ID，并从仓库中一次性查询出来
                List<String> capabilityIds = nodeDefs.stream()
                                .map(WfNodeDefinition::getCapabilityId)
                                .distinct()
                                .collect(Collectors.toList());

                // 对应的 Map 结构也要同步确保 Key 是 String
                Map<String, WfNodeCapability> capabilityMap = capabilityService.listByIds(capabilityIds)
                                .stream()
                                .collect(Collectors.toMap(WfNodeCapability::getCapabilityId, Function.identity()));

                // 4. 创建工作流运行实例
                String instanceId = UUID.fastUUID().toString(true);
                WfWorkflowInstance instance = new WfWorkflowInstance();
                instance.setWorkflowInstanceId(instanceId);
                instance.setDefinitionId(definitionId);
                instance.setWorkflowName(definition.getName());
                instance.setStatus(WorkflowStatus.RUNNING);
                instance.setCreator(creator);
                this.save(instance);

                // 5. 实例化节点 (NodeDefinition -> NodeInstance)
                List<WfNodeInstance> nodeInstances = nodeDefs.stream().map(def -> {
                        // 安全检查：确保该节点引用的算子在仓库中可用
                        WfNodeCapability cap = capabilityMap.get(def.getCapabilityId());
                        Assert.notNull(cap, "节点 [" + def.getNodeName() + "] 引用的能力算子 " + def.getCapabilityId() + " 不存在");
                        Assert.isTrue(ActiveStatus.ENABLE.equals(cap.getActiveStatus()),
                                        "算子 [" + cap.getName() + "] 已被禁用");

                        WfNodeInstance ni = new WfNodeInstance();
                        ni.setNodeInstanceId(UUID.fastUUID().toString(true));
                        ni.setWorkflowInstanceId(instanceId);
                        ni.setCapabilityId(def.getCapabilityId());
                        ni.setNodeOrder(def.getNodeOrder());
                        ni.setStatus(NodeInstanceStatus.INIT);

                        // 关键：从能力仓库继承 handlerType (python_agent / java_local)
                        // 这样用户在配置 NodeDefinition 时就不需要重复填这个字段了
                        ni.setHandlerType(cap.getHandlerType());

                        // 继承人工/自动状态
                        ni.setManualStatus(def.getManualStatus());

                        // 拷贝参数（后期可在此处进行动态变量替换）
                        ni.setInputParams(def.getDefaultParams());

                        return ni;
                }).collect(Collectors.toList());

                // 6. 批量保存节点实例
                nodeInstanceService.saveBatch(nodeInstances);

                // 7. 寻找并启动首个节点
                WfNodeInstance firstNode = nodeInstances.get(0);
                instance.setCurrentNodeId(firstNode.getNodeInstanceId());
                this.updateById(instance);

                log.info("🎯 实例 {} 数据准备就绪，发布启动事件信号...", instanceId);

                // 8. 发布事件，不再直接调用引擎方法
                eventPublisher.publishEvent(new WorkflowTaskEvent(this, firstNode.getNodeInstanceId()));

                return instanceId;
        }
}