
package com.ruoyi.product.domain;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.HandlerType;
import com.ruoyi.product.enums.ManualStatus;
import com.ruoyi.product.enums.NodeInstanceStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "wf_node_instance", autoResultMap = true)

public class WfNodeInstance extends ProductBaseEntity {

    @TableId(value = "node_instance_id")
    private String nodeInstanceId;

    private String workflowInstanceId;

    private String capabilityId;

    private Integer nodeOrder;

    private HandlerType handlerType;

    private NodeInstanceStatus status;

    private ManualStatus isManual;

    /**
     * * 使用 MyBatis Plus 的 JacksonTypeHandler 处理 JSON 字段
     * 需要在实体类上加 @TableName(autoResultMap = true)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> inputParams;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> outputData;

    private String errorMsg;

}