package com.ruoyi.product.domain.dto;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

/**
 * 通用的单条记录处理结果
 */
@Data
@Builder
public class ItemProcessResult implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean success;     // 是否成功
    private boolean skipped;     // 是否跳过
    private String errorReason;  // 错误原因（失败或跳过时）
    
    public static ItemProcessResult success() {
        return ItemProcessResult.builder()
                .success(true)
                .skipped(false)
                .build();
    }
    
    public static ItemProcessResult skip(String reason) {
        return ItemProcessResult.builder()
                .success(false)
                .skipped(true)
                .errorReason(reason)
                .build();
    }
    
    public static ItemProcessResult fail(String reason) {
        return ItemProcessResult.builder()
                .success(false)
                .skipped(false)
                .errorReason(reason)
                .build();
    }
}