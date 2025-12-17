package com.ruoyi.product.constant;

public enum TaskCode {

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
    TaskCode(String code, String label) {
        this.code = code;
        this.label = label;
    }

    /* getter */
    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    /* 数据库 code -> 枚举 */
    public static TaskCode of(String code) {
        for (TaskCode c : values()) {
            if (c.code.equals(code)) {
                return c;
            }
        }
        return null;
    }
}