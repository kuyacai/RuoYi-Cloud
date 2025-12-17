package com.ruoyi.product.domain;
/**
 * 通用的单条记录处理结果
 */

public class ItemProcessResult {
    private boolean success;     // 是否成功
    private boolean skipped;     // 是否跳过
    private String errorReason;  // 错误原因（失败或跳过时）
    
    public static ItemProcessResult success() {
        ItemProcessResult result = new ItemProcessResult();
        result.setSuccess(true);
        return result;
    }
    
    public static ItemProcessResult skip(String reason) {
        ItemProcessResult result = new ItemProcessResult();
        result.setSkipped(true);
        result.setErrorReason(reason);
        return result;
    }
    
    public static ItemProcessResult fail(String reason) {
        ItemProcessResult result = new ItemProcessResult();
        result.setSuccess(false);
        result.setErrorReason(reason);
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    
}