package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "workflow", description = "任务节点是否需要人工处理")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ManualStatus implements BaseEnum, IEnum<String> {
    YES("yes", "人工"),
    NO("no", "自动");

    private final String code;
    private final String label;

    ManualStatus(String code, String label) {
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
