package com.ruoyi.product.constant;


/**
 * 版本类型
 */
public enum RevisionType {

    SYNC("sync", "同步"),
    MANUAL("manual", "手动");

    private final String code;
    private final String label;

    RevisionType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static RevisionType of(String code) {
        for (RevisionType e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}