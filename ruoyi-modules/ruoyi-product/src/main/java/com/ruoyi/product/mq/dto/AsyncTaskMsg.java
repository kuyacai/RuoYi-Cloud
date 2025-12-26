// com.ruoyi.product.mq.dto.AsyncTaskMsg
package com.ruoyi.product.mq.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor  // 必须：用于反序列化
@AllArgsConstructor // 必须：用于@Builder
public class AsyncTaskMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskId;
    private String taskCode;
    @Builder.Default  // 设置默认值
    private Map<String, Object> ext = new HashMap<>();
    
    // 便捷的构建方法
    public static AsyncTaskMsg create(String taskId, String taskCode, String fileUrl) {
        return AsyncTaskMsg.builder()
            .taskId(taskId)
            .taskCode(taskCode)
            .ext(Map.of("fileUrl", fileUrl))
            .build();
    }
    
}