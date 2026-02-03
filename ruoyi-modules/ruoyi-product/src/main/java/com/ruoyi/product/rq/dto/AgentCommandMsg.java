package com.ruoyi.product.rq.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentCommandMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    // 指令类型：STOP_TASK
    private String command;

    // 目标节点实例 ID
    private String nodeInstanceId;

    // 扩展参数（如断点策略等）
    private Map<String, Object> extraArgs;

    private Long timestamp = System.currentTimeMillis();
}