package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "workflow", description = "任务节点是否需要人工处理")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum YesNoUnknown implements BaseEnum, IEnum<String> {
    YES("yes", "是"),
    NO("no", "否"),
    UNKNOWN("unknown", "未知");

    private final String code;
    private final String label;

    YesNoUnknown(String code, String label) {
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
