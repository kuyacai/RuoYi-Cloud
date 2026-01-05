package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "product", description = "任务编码") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemTaskCode implements BaseEnum, IEnum<String> {

    EDIT_TITLE("edit_title", "修改标题"),
    EDIT_IMAGE("edit_image", "修改图片"),
    EDIT_ATTRIBUTE("edit_attribute", "修改属性"),
    EDIT_PRICE("edit_price", "修改价格"),
    EDIT_STOCK("edit_stock", "修改库存"),
    EDIT_VIDEO("edit_video", "修改视频"),
    EDIT_SPEC("edit_spec", "修改规格"),
    SYNC_LISTING("sync_listing", "同步上架"),
    SYNC_INVENTORY("sync_inventory", "同步库存");

    private final String code;
    private final String label;

    /* 构造器 */
    ItemTaskCode(String code, String label) {
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

    /* 数据库 code -> 枚举 */
    public static ItemTaskCode of(String code) {
        for (ItemTaskCode c : values()) {
            if (c.code.equals(code)) {
                return c;
            }
        }
        return null;
    }
}