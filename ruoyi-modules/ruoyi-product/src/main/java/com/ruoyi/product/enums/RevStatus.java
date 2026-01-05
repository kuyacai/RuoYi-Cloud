package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 版本状态
 */
@ExposeEnum(group = "product", description = "商品版本状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RevStatus implements BaseEnum, IEnum<String> {

    FROZEN("frozen", "冻结"),
    EDITING("editing", "编辑中"),
    AUDITING("auditing", "审核中"),
    APPROVED("approved", "已审核"),
    DISCARDED("discarded", "已废弃");

    private final String code;
    private final String label;

    RevStatus(String code, String label) {
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

    public static RevStatus of(String code) {
        for (RevStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}