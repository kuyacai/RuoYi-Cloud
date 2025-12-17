package com.ruoyi.product.constant;

public enum AsyncTaskCode {

    SKU_IMPORT("sku_import", "导入SKU"),
    SPU_IMPORT("spu_import", "导入SPU"),
    DOUDIAN_SYNC("doudian_sync", "抖店商品同步");

    private final String code;
    private final String label;

    /* 构造器 */
    AsyncTaskCode(String code, String label) {
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
    public static AsyncTaskCode of(String code) {
        for (AsyncTaskCode c : values()) {
            if (c.code.equals(code)) {
                return c;
            }
        }
        return null;
    }
}