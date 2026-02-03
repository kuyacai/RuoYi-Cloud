package com.ruoyi.product.domain;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.HandlerType;
import com.ruoyi.product.enums.ManualStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "wf_node_definition", autoResultMap = true)
public class WfNodeDefinition extends ProductBaseEntity {

    @TableId(value = "node_def_id", type = IdType.INPUT)
    private String nodeDefId;

    private String definitionId;

    private String capabilityId;

    private String capabilityVersionId;

    private String nodeName;

    private Integer nodeOrder;

    private HandlerType handlerType;

    @TableField("is_manual")
    private ManualStatus manualStatus;
    /**
     * 默认配置参数 (含Prompt模板)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> defaultParams;

    /**
     * 输入数据映射关系
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> inputMapping;
}