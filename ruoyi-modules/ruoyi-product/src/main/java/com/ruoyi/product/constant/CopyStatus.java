package com.ruoyi.product.constant;


/**
 * 复制状态
 */
public enum CopyStatus {

    PENDING("pending", "待复制"),
    IN_PROGRESS("in_progress", "复制中"),
    DONE("done", "已复制"),
    FAILED("failed", "复制失败");

    private final String code;
    private final String label;

    CopyStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static CopyStatus of(String code) {
        for (CopyStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}