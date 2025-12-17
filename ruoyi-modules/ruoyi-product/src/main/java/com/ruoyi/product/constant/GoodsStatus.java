package com.ruoyi.product.constant;


/**
 * 商品状态
 */
public enum GoodsStatus {

    NORMAL("normal", "正常在售"),
    BLACKLIST("blacklist", "黑名单"),
    INFRINGEMENT("infringement", "侵权下架"),
    RECALL("recall", "召回"),
    DISCONTINUED("discontinued", "停产");

    private final String code;
    private final String label;

    GoodsStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static GoodsStatus of(String code) {
        for (GoodsStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}
