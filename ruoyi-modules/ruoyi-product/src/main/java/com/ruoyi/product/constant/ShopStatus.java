package com.ruoyi.product.constant;


/**
 * 店铺状态
 */
public enum ShopStatus {

    NORMAL("normal", "正常"),
    SUSPENDED("suspended", "暂停"),
    CLOSED("closed", "关闭");

    private final String code;
    private final String label;

    ShopStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static ShopStatus of(String code) {
        for (ShopStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}