package com.ruoyi.product.constant;


/**
 * 任务状态
 */
public enum ItemTaskStatus {

    PENDING("pending", "待处理"),
    DONE("done", "已完成"),
    FAILED("failed", "已失败"),
    CANCELLED("cancelled", "已取消");

    private final String code;
    private final String label;

    ItemTaskStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static ItemTaskStatus of(String code) {
        for (ItemTaskStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 判断是否为最终状态
     */
    public static boolean isFinalStatus(String status) {
        return DONE.getCode().equals(status) || FAILED.getCode().equals(status) || CANCELLED.getCode().equals(status);
    }
    /**
     * 判断是否为完成状态（含成功和失败）
     */
    public static boolean isCompletedStatus(String status) {
        return DONE.getCode().equals(status) || FAILED.getCode().equals(status);
    }
    
    /**
     * 判断是否可以重新处理
     */
    public static boolean canRetry(String status) {
        return FAILED.getCode().equals(status) || CANCELLED.getCode().equals(status);
    }
}