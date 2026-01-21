package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "workflow", description = "执行器类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HandlerType implements BaseEnum, IEnum<String> {
    PYTHON_AGENT("python_agent", "Python执行"),
    JAVA_LOCAL("java_local", "java执行");

    private final String code;
    private final String label;

    HandlerType(String code, String label) {
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
