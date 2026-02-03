package com.ruoyi.product.rq.dto;

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

    // 节点实例 ID
    private String nodeInstanceId;
    // 工作流实例 ID
    private String workflowInstanceId;
    // 能力算子逻辑 ID (如: captcha_resolver)
    private String capabilityId;
    /**
     * 物理版本 ID (对应 wf_node_capability 表的主键 id)
     * 值为 "capability_id:fingerprint"
     */
    private String capabilityVersionId;
    /**
     * 对应 Python 端 payload.get("inputParams")
     */
    private Map<String, Object> inputParams;
}