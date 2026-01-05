package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 商品状态
 */
@ExposeEnum(group = "product", description = "商品状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GoodsStatus implements BaseEnum, IEnum<String> {

    NORMAL("normal", "上架"),
    OFFLINE("offline", "下架"),
    BLACKLIST("blacklist", "黑名单"),
    INFRINGEMENT("infringement", "侵权下架"),
    RECALL("recall", "召回"),
    DISCONTINUED("discontinued", "停产");

    private final String code;
    private final String label;

    GoodsStatus(String code, String label) {
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

    public static GoodsStatus of(String code) {
        for (GoodsStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}
