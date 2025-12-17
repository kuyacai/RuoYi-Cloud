package com.ruoyi.product.constant;


/**
 * SPU 状态
 */
public enum SpuStatus {

    ACTIVE("active", "上架"),
    INACTIVE("inactive", "下架"),
    DELETED("deleted", "删除");

    private final String code;
    private final String label;

    SpuStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static SpuStatus of(String code) {
        for (SpuStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}