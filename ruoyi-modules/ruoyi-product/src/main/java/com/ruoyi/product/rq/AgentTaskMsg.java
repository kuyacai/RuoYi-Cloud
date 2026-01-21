package com.ruoyi.product.rq;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 跨语言任务调度消息协议
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentTaskMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String taskNodeId;
    private String workflowInstanceId;
    private String capabilityId;
    /**
     * 对应 Python 端 payload.get("inputParams")
     */
    private Map<String, Object> inputParams;
}