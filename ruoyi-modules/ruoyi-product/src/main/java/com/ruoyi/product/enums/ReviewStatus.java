package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 审核状态
 */
@ExposeEnum(group = "product", description = "商品某版本审核状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewStatus implements BaseEnum, IEnum<String> {

    PENDING("pending", "待审核"),
    APPROVED("approved", "审核通过"),
    REJECTED("rejected", "审核拒绝");

    private final String code;
    private final String label;

    ReviewStatus(String code, String label) {
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

    public static ReviewStatus of(String code) {
        for (ReviewStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}