package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 当前商品状态
 */
@ExposeEnum(group = "product", description = "当前商品状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CurrStatus implements BaseEnum, IEnum<String> {

    ACTIVE("active", "上架"),
    INACTIVE("inactive", "下架"),
    DRAFT("draft", "草稿"),
    REVIEWING("reviewing", "审核中"),
    INACTIVE_SALE("inactive", "可上架"), // 与下架同码，按业务区分
    DELETED("deleted", "删除");

    private final String code;
    private final String label;

    CurrStatus(String code, String label) {
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

    public static CurrStatus of(String code) {
        for (CurrStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}