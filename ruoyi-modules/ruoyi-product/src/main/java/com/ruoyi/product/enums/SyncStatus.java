package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 某个活动下的商品的是否已同步到抖店等平台状态枚举。
 * * 规范说明：
 * 1. 采用语义化的英文字符串作为 Code，便于数据库直观排查。
 * 2. 状态采用形容词形式（ACTIVE）而非过去分词（ACTIVATED）。
 * 3. 实现 IEnum 接口是 MyBatis Plus 推荐的扫描方式
 */
@ExposeEnum(group = "product", description = "是否已同步到抖店状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SyncStatus implements BaseEnum, IEnum<String> {

    YES("yes", "已同步"),
    NO("no", "未同步");

    private final String code;
    private final String label;

    SyncStatus(String code, String label) {
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

    public static SyncStatus of(String code) {
        for (SyncStatus e : values()) {
            if (e.code.equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }
}