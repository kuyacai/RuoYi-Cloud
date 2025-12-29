package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 优惠类型
 */
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "product", description = "优惠类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DiscountType implements BaseEnum, IEnum<String> {

    FIXED_PRICE("fixed_price", "一口价"),
    DIRECT_DEDUCTION("direct_deduction", "立减"),
    DISCOUNT("discount", "折扣");

    private final String code;
    private final String label;

    DiscountType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getValue() {
        return this.code; // MyBatis Plus 数据库存储值
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