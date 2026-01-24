package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.ActiveStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "wf_workflow_definition", autoResultMap = true)
public class WfWorkflowDefinition extends ProductBaseEntity {

    @TableId(value = "definition_id", type = IdType.INPUT)
    private String definitionId;

    private String name;

    private String description;

    private String businessTag;

    private Integer version;

    @TableField("is_active")
    private ActiveStatus activeStatus;

    private String creator;
}