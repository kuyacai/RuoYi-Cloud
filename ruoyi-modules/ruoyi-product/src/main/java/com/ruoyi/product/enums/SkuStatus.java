package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * SKU 状态
 */
@ExposeEnum(group = "product", description = "SKU状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SkuStatus implements BaseEnum, IEnum<String> {

    NOMAL("nomal", "可上架"),
    DELETED("deleted", "已删除"),
    INACTIVE("inactive", "不可上架");

    private final String code;
    private final String label;

    SkuStatus(String code, String label) {
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

    public static SkuStatus of(String code) {
        for (SkuStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}