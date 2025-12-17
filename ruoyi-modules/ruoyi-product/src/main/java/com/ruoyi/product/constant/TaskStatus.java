package com.ruoyi.product.constant;


/**
 * 任务状态
 */
public enum TaskStatus {

    PENDING("pending", "待处理"),
    DONE("done", "已完成");

    private final String code;
    private final String label;

    TaskStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static TaskStatus of(String code) {
        for (TaskStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}