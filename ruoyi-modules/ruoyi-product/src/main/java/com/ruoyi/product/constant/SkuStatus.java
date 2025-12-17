package com.ruoyi.product.constant;

/**
 * SKU 状态
 */
public enum SkuStatus {

    NOMAL("nomal", "可上架"),
    DELETED("deleted", "已删除"),
    INACTIVE("inactive", "不可上架");

    private final String code;
    private final String label;

    SkuStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static SkuStatus of(String code) {
        for (SkuStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}