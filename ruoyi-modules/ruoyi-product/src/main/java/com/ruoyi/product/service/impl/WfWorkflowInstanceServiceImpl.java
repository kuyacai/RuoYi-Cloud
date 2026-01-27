package com.ruoyi.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
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
import com.ruoyi.product.workflow.event.NodeCompletedEvent;
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
        // private final IWorkflowEngineService workflowEngineService;

        @Override
        @Transactional(rollbackFor = Exception.class)
        public String startWorkflow(String definitionId, Map<String, Object> variables, String creator) {
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
                instance.setBusinessTag(definition.getBusinessTag());
                instance.setStatus(WorkflowStatus.RUNNING);
                instance.setCreator(creator);

                // 【关键修改】：初始化全局账本，放入启动参数
                Map<String, Object> context = new HashMap<>();
                context.put("START", variables != null ? variables : new HashMap<>());
                instance.setRuntimeContext(context);

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
                        // ni.setNodeDefId(def.getNodeDefId());
                        ni.setCapabilityId(def.getCapabilityId());
                        ni.setNodeOrder(def.getNodeOrder());
                        ni.setNodeName(def.getNodeName());
                        ni.setStatus(NodeInstanceStatus.INIT);

                        // 关键：从能力仓库继承 handlerType (python_agent / java_local)
                        // 这样用户在配置 NodeDefinition 时就不需要重复填这个字段了
                        ni.setHandlerType(cap.getHandlerType());

                        // 继承人工/自动状态
                        ni.setManualStatus(def.getManualStatus());

                        Map<String, Object> finalParams = new HashMap<>();
                        // 1. 先放默认参数 (优先级最低)
                        if (def.getDefaultParams() != null) {
                                finalParams.putAll(def.getDefaultParams());
                        }
                        // 2. 覆盖输入映射参数 (优先级高，这里包含你填写的 "测试001" 或 SpEL 表达式)
                        Map<String, Object> mapping = def.getInputMapping();
                        if (mapping != null && !mapping.isEmpty()) {
                                finalParams.putAll(mapping);
                                log.debug("节点 [{}] 使用了输入映射覆盖参数: {}", def.getNodeName(), mapping);
                        }

                        // 3. 将启动时传入的 variables 也合并进来（优先级最高）
                        if (variables != null && !variables.isEmpty()) {
                                finalParams.putAll(variables);
                        }

                        // 将合并后的参数存入实例
                        ni.setInputParams(finalParams);

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

        @Override
        public List<WfWorkflowInstance> selectWfWorkflowInstanceList(WfWorkflowInstance query) {
                // 1. 构造基础查询条件
                LambdaQueryWrapper<WfWorkflowInstance> lqw = new LambdaQueryWrapper<>();
                lqw.like(StringUtils.isNotBlank(query.getWorkflowName()), WfWorkflowInstance::getWorkflowName,
                                query.getWorkflowName());
                lqw.eq(query.getStatus() != null, WfWorkflowInstance::getStatus, query.getStatus());
                lqw.orderByDesc(WfWorkflowInstance::getCreatedAtUtc);

                // 2. 执行基础分页查询
                List<WfWorkflowInstance> list = this.list(lqw);

                // 3. 循环填充进度数据
                for (WfWorkflowInstance instance : list) {
                        // 获取当前实例的总节点数
                        long totalNodes = nodeInstanceService.count(new LambdaQueryWrapper<WfNodeInstance>()
                                        .eq(WfNodeInstance::getWorkflowInstanceId, instance.getWorkflowInstanceId()));

                        // 获取当前节点的详情（利用冗余的 nodeName 字段）
                        if (StringUtils.isNotBlank(instance.getCurrentNodeId())) {
                                WfNodeInstance currentNode = nodeInstanceService.getById(instance.getCurrentNodeId());
                                if (currentNode != null) {
                                        // 设置当前节点名称
                                        instance.setCurrentNodeName(currentNode.getNodeName());
                                        // 设置进度文本 "2/5"
                                        instance.setProgressText(currentNode.getNodeOrder() + "/" + totalNodes);
                                }
                        } else {
                                instance.setProgressText("0/" + totalNodes);
                                instance.setCurrentNodeName("尚未开始");
                        }
                }
                return list;
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public void retryNode(String nodeInstanceId) {
                // 1. 获取当前节点实例
                WfNodeInstance targetNode = nodeInstanceService.getById(nodeInstanceId);
                Assert.notNull(targetNode, "未找到指定的节点实例");

                String instanceId = targetNode.getWorkflowInstanceId();
                WfWorkflowInstance instance = this.getById(instanceId);

                // 2. 获取该流程下所有序号 >= 当前节点的实例
                List<WfNodeInstance> followUpNodes = nodeInstanceService.list(
                                new LambdaQueryWrapper<WfNodeInstance>()
                                                .eq(WfNodeInstance::getWorkflowInstanceId, instanceId)
                                                .ge(WfNodeInstance::getNodeOrder, targetNode.getNodeOrder()));

                // 3. 批量重置状态
                for (WfNodeInstance node : followUpNodes) {
                        node.setStatus(NodeInstanceStatus.INIT); // 重置为初始状态
                        node.setOutputData(null); // 清空旧产出，防止旧数据干扰全局账本
                        node.setErrorMsg(null); // 清空错误信息
                }
                nodeInstanceService.updateBatchById(followUpNodes);

                // 4. 更新工作流实例的当前节点指针
                instance.setCurrentNodeId(nodeInstanceId);
                instance.setStatus(WorkflowStatus.RUNNING); // 确保流程状态不是 FAILED
                this.updateById(instance);

                log.info("🔄 节点重试：实例 {}, 节点 {} ({})", instanceId, targetNode.getNodeName(), nodeInstanceId);

                // 5. 重新发布执行信号
                eventPublisher.publishEvent(new WorkflowTaskEvent(this, nodeInstanceId));
        }

        @Override
        @Transactional(rollbackFor = Exception.class)
        public void completeManualNode(String nodeInstanceId, Map<String, Object> manualData) {
                WfNodeInstance node = nodeInstanceService.getById(nodeInstanceId);
                Assert.notNull(node, "未找到节点实例");

                // 1. 更新人工回填的数据（若有）
                if (manualData != null && !manualData.isEmpty()) {
                        Map<String, Object> input = node.getInputParams();
                        input.putAll(manualData);
                        node.setInputParams(input);
                }

                // 2. 标记成功并持久化
                node.setStatus(NodeInstanceStatus.SUCCESS);
                node.setErrorMsg(null);
                nodeInstanceService.updateById(node);

                // 3. 驱动引擎寻找下一个节点
                // 【解耦点】：发布节点完成事件，让监听器去触发 getNextNode
                log.info("📢 人工节点 {} 已标记成功，发布流转信号", nodeInstanceId);
                eventPublisher.publishEvent(new NodeCompletedEvent(this, nodeInstanceId));
        }
}