package com.ruoyi.product.domain;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.WorkflowStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "wf_workflow_instance", autoResultMap = true)
public class WfWorkflowInstance extends ProductBaseEntity {

    @TableId(value = "workflow_instance_id")
    private String workflowInstanceId;

    private String definitionId;

    private String workflowName;

    private String businessTag;

    private WorkflowStatus status;

    /**
     * 全局账本：存储初始参数和各节点输出
     * 结构示例：
     * {
     * "START": {"keyword": "手机"},
     * "node_001": {"result": "ok"}
     * }
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> runtimeContext;

    private String currentNodeId;

    private String creator;

    // WfWorkflowInstance.java 中添加
    @TableField(exist = false)
    private String progressText; // 存储 "2/5"

    @TableField(exist = false)
    private String currentNodeName; // 存储当前节点的名称

}