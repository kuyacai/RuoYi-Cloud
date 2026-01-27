package com.ruoyi.product.workflow.engine.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.domain.WfNodeInstance;
import com.ruoyi.product.domain.WfWorkflowInstance;
import com.ruoyi.product.service.IWfNodeInstanceService;
import com.ruoyi.product.service.IWfWorkflowInstanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkflowParameterParser {

    private final IWfNodeInstanceService nodeInstanceService;
    private final IWfWorkflowInstanceService wfWorkflowInstanceService;
    private final ExpressionParser parser = new SpelExpressionParser();

    private final TemplateParserContext templateContext = new TemplateParserContext("#{", "}");

    /**
     * 解析参数
     */
    public Map<String, Object> parse(String currentId, Map<String, Object> rawParams) {
        if (rawParams == null || rawParams.isEmpty())
            return rawParams;

        // 1. 准备上下文数据 (EvaluationContext)
        WfNodeInstance current = nodeInstanceService.getById(currentId);
        StandardEvaluationContext context = prepareContext(current);

        // 2. 递归解析
        return deepResolve(rawParams, context);
    }

    private StandardEvaluationContext prepareContext(WfNodeInstance current) {
        StandardEvaluationContext evalContext = new StandardEvaluationContext();

        // 获取工作流实例中的 RuntimeContext (里面存了 START 参数)
        WfWorkflowInstance instance = wfWorkflowInstanceService.getById(current.getWorkflowInstanceId());
        if (instance.getRuntimeContext() != null) {
            evalContext.setVariables(instance.getRuntimeContext()); // 这样就能解析 #{#START['key']}
        }

        // 查询当前实例之前所有已完成的节点
        List<WfNodeInstance> prevNodes = nodeInstanceService.list(
                new LambdaQueryWrapper<WfNodeInstance>()
                        .eq(WfNodeInstance::getWorkflowInstanceId, current.getWorkflowInstanceId())
                        .lt(WfNodeInstance::getNodeOrder, current.getNodeOrder())
                        .isNotNull(WfNodeInstance::getOutputData));

        // 将节点输出放入上下文，Key 格式为 node1, node2...
        for (WfNodeInstance ni : prevNodes) {
            evalContext.setVariable("node" + ni.getNodeOrder(), ni.getOutputData());
        }

        // 也可以放入一些系统变量
        evalContext.setVariable("instanceId", current.getWorkflowInstanceId());

        return evalContext;
    }

    private Map<String, Object> deepResolve(Map<String, Object> source, StandardEvaluationContext context) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                result.put(entry.getKey(), resolveString((String) value, context));
            } else if (value instanceof Map) {
                result.put(entry.getKey(), deepResolve((Map<String, Object>) value, context));
            } else {
                result.put(entry.getKey(), value);
            }
        }
        return result;
    }

    private Object resolveString(String template, StandardEvaluationContext context) {
        if (!template.contains("#{"))
            return template;
        try {
            // SpEL 会解析字符串中的 #{#node1['field']} 并替换
            return parser.parseExpression(template, templateContext).getValue(context, Object.class);
        } catch (Exception e) {
            log.error("❌ SpEL 解析异常，模板: {}, 原因: {}", template, e.getMessage());
            return template; // 解析失败返回原样
        }
    }
}