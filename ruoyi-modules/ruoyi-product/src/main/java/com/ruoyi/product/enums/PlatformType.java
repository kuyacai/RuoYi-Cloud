package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "workflow", description = "平台类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PlatformType implements BaseEnum, IEnum<String> {
    YES("douyin", "抖店"),
    NO("taobao", "淘宝");

    private final String code;
    private final String label;

    PlatformType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getValue() {
        return code;
    }
}
