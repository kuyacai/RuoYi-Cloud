package com.ruoyi.product.enums;

/**
 * 活动配置状态枚举
 */
public enum ConfigStatus {

    ENABLE("enable", "启用"),
    DISABLED("disabled", "停用"),
    DELETED("deleted", "已删除");

    private final String code;
    private final String label;

    ConfigStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static ConfigStatus of(String code) {
        for (ConfigStatus e : values()) {
            if (e.code.equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }
}