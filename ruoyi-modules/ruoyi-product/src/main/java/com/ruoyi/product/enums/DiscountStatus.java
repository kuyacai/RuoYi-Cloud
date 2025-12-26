package com.ruoyi.product.enums;


/**
 * 优惠状态
 */
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "product", description = "优惠活动状态")
public enum DiscountStatus implements BaseEnum{

    DRAFT("draft", "草稿"),
    ACTIVE("active", "进行中"),
    ENDED("ended", "已结束");

    private final String code;
    private final String label;

    DiscountStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static DiscountStatus of(String code) {
        for (DiscountStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}