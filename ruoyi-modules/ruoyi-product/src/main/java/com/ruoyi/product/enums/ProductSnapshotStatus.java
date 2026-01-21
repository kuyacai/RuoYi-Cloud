package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "workflow", description = "商品快照处理状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ProductSnapshotStatus implements BaseEnum, IEnum<String> {
    CROPPED("cropped", "已切图"),
    IDENTIFIED("identified", "AI已识别"),
    ASSOCIATED("associated", "已关联"),
    FAILED("failed", "处理失败");

    private final String code;
    private final String label;

    ProductSnapshotStatus(String code, String label) {
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
