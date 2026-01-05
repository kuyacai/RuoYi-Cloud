package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 版本类型
 */
@ExposeEnum(group = "product", description = "版本类型") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RevisionType implements BaseEnum, IEnum<String> {

    SYNC("sync", "同步"),
    MANUAL("manual", "手动");

    private final String code;
    private final String label;

    RevisionType(String code, String label) {
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

    public static RevisionType of(String code) {
        for (RevisionType e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}