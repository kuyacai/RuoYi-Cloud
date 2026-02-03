package com.ruoyi.product.rq.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Python Agent 发回的进度消息实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentProgressMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    // 对应 Python 的 nodeInstanceId
    private String nodeInstanceId;

    // 进度百分比 (0-100)
    private Integer progress;

    // 当前状态 (running, success, failed 等)
    private String status;

    // 算子自定义的详细数据 (对应 Python 的 data 字典)
    private Map<String, Object> data;

    // 发生时间戳
    private String timestamp;
}