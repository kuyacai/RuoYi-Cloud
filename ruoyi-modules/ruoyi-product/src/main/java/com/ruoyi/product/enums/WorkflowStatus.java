package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "workflow", description = "工作流实例状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WorkflowStatus implements BaseEnum, IEnum<String> {
    RUNNING("running", "运行中"),
    COMPLETED("completed", "已完成"),
    FAILED("failed", "已失败"),
    SUSPENDED("suspended", "已挂起");

    private final String code;
    private final String label;

    WorkflowStatus(String code, String label) {
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