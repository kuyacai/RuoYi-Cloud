package com.ruoyi.product.domain;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.ActiveStatus;
import com.ruoyi.product.enums.HandlerType;
import com.ruoyi.product.enums.ManualStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "wf_node_capability", autoResultMap = true)
public class WfNodeCapability extends ProductBaseEntity {

    /**
     * 这里不能随意赋值，需要一个明确带有语义的字符串。
     * 例如：这个能力为关键词导入，则这里应该设置为 keyword_importer
     * 注意，在进行新增的时候，要检查是否已经存在。
     */
    @TableId(value = "capability_id", type = IdType.INPUT)
    private String capabilityId;

    /** 能力名称 (如: 关键词导入) */
    private String name;

    /** 执行器类型 (python_agent/java_local) */
    private HandlerType handlerType;

    /** 功能描述 */
    private String description;

    @TableField("is_manual")
    private ManualStatus manualStatus;

    /**
     * * 配置项定义 (JSON)
     * 用于描述该能力需要哪些输入参数，例如：{"fields": [{"name": "prompt", "type": "textarea"}]}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> configSchema;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> outputSchema;

    @TableField("is_active")
    private ActiveStatus activeStatus;
}