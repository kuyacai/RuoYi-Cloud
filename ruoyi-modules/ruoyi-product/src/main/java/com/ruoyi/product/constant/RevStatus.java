package com.ruoyi.product.constant;
/**
 * 版本状态
 */
public enum RevStatus {

    FROZEN("frozen", "冻结"),
    EDITING("editing", "编辑中"),
    APPROVING("approving", "审核中"),
    APPROVED("approved", "已审核"),
    DISCARDED("discarded", "已废弃");

    private final String code;
    private final String label;

    RevStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static RevStatus of(String code) {
        for (RevStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}