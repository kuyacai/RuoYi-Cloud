package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 折扣配置状态枚举
 * 例如new_user_gift_config、product_discount_config等表的config_status的值。
 */

@ExposeEnum(group = "product", description = "折扣配置状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConfigStatus implements BaseEnum, IEnum<String> {

    ENABLE("enable", "启用"),
    DISABLED("disabled", "停用"),
    DELETED("deleted", "已删除");

    private final String code;
    private final String label;

    ConfigStatus(String code, String label) {
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
        return this.code;
    }

    public static ConfigStatus of(String code) {
        for (ConfigStatus e : values()) {
            if (e.code.equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }
}