package com.ruoyi.product.constant;


/**
 * 优惠类型
 */
public enum DiscountType {

    FIXED_PRICE("fixed_price", "一口价"),
    DIRECT_DEDUCTION("direct_deduction", "立减"),
    DISCOUNT("discount", "折扣");

    private final String code;
    private final String label;

    DiscountType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static DiscountType of(String code) {
        for (DiscountType e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}