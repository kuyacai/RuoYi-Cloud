package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 复制状态
 */
@ExposeEnum(group = "product", description = "复制状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CopyStatus implements BaseEnum, IEnum<String> {

    PENDING("pending", "待复制"),
    IN_PROGRESS("in_progress", "复制中"),
    DONE("done", "已复制"),
    FAILED("failed", "复制失败");

    private final String code;
    private final String label;

    CopyStatus(String code, String label) {
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

    public static CopyStatus of(String code) {
        for (CopyStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}