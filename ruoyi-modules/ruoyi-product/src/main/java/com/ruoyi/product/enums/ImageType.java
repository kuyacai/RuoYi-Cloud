package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 图片类型
 */
@ExposeEnum(group = "product", description = "图片类型") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ImageType implements BaseEnum, IEnum<String> {

    MAIN("main", "主图"),
    MAIN34("main34", "3:4主图"),
    WHITE("white", "白底图"),
    GUIDE("guide", "导购图"),
    DETAIL("detail", "详情图"),
    SPEC("spec", "规格图");

    private final String code;
    private final String label;

    ImageType(String code, String label) {
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

    public static ImageType of(String code) {
        for (ImageType e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}