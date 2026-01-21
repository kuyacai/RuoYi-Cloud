package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private String currentNodeId;

    private String creator;

}