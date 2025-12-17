package com.ruoyi.product.constant;


/**
 * 当前商品状态
 */
public enum CurrStatus {

    ACTIVE("active", "上架"),
    INACTIVE("inactive", "下架"),
    DRAFT("draft", "草稿"),
    REVIEWING("reviewing", "审核中"),
    INACTIVE_SALE("inactive", "可上架"),   // 与下架同码，按业务区分
    DELETED("deleted", "删除");

    private final String code;
    private final String label;

    CurrStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static CurrStatus of(String code) {
        for (CurrStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}