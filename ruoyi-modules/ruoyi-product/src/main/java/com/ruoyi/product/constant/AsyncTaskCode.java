package com.ruoyi.product.constant;

public enum AsyncTaskCode {

    SKU_IMPORT("sku_import", "导入SKU"),
    SPU_IMPORT("spu_import", "导入SPU"),
    PRICE_IMPORT("price_import", "批量计算价格"),
    SINGLE_DISCOUNT_IMPORT("single_discount", "导入单品直降"),
    PRODUCT_DISCOUNT_IMPORT("product_discount", "导入商品优惠"),
    NEW_USER_GIFT_IMPORT("new_user_gift", "导入新人礼金"),
    REPURCHASE_DISCOUNT_IMPORT("repurchase_discount", "导入复购券"),
    PLATFORM_PROMOTION_IMPORT("platform_promotion", "导入平台大促优惠"),


    MODIFY_TITLE_IMPORT("modify_title", "导入修改标题"),
    MODIFY_IMAGES_IMPORT("modify_images", "导入修改图片信息"),


    /**
     * 综合审核（一次性审核所有修改）
     */
    AUDIT_SPU_COMPREHENSIVE("audit_spu_comprehensive", "综合审核商品信息"),
    
    DOUDIAN_SYNC("doudian_sync", "抖店商品同步");



    // 公共常量
    public static final String SKU_IMPORT_CODE = "sku_import";
    public static final String SPU_IMPORT_CODE = "spu_import";
    public static final String PRICE_IMPORT_CODE = "price_import";
    public static final String SINGLE_DISCOUNT_IMPORT_CODE = "single_discount";
    public static final String PRODUCT_DISCOUNT_IMPORT_CODE = "product_discount";
    public static final String NEW_USER_GIFT_IMPORT_CODE = "new_user_gift";
    public static final String REPURCHASE_DISCOUNT_IMPORT_CODE = "repurchase_discount";
    public static final String PLATFORM_PROMOTION_IMPORT_CODE = "platform_promotion";
    public static final String MODIFY_TITLE_IMPORT_CODE = "modify_title";
    public static final String MODIFY_IMAGES_IMPORT_CODE = "modify_images";

    public static final String AUDIT_SPU_COMPREHENSIVE_CODE = "audit_spu_comprehensive";

    public static final String DOUDIAN_SYNC_CODE = "doudian_sync";


    public static final String SKU_IMPORT_LABEL = "导入SKU";
    public static final String SPU_IMPORT_LABEL = "导入SPU";
    public static final String PRICE_IMPORT_LABEL = "批量计算价格";
    public static final String SINGLE_DISCOUNT_IMPORT_LABEL = "导入单品直降";
    public static final String PRODUCT_DISCOUNT_IMPORT_LABEL = "导入商品优惠";
    public static final String NEW_USER_GIFT_IMPORT_LABEL = "导入新人礼金";
    public static final String REPURCHASE_DISCOUNT_IMPORT_LABEL = "导入复购券";
    public static final String PLATFORM_PROMOTION_IMPORT_LABEL = "导入平台大促优惠";
    public static final String MODIFY_TITLE_IMPORT_LABEL = "导入修改标题";
    public static final String MODIFY_IMAGES_IMPORT_LABEL = "导入修改图片信息";
    public static final String AUDIT_SPU_COMPREHENSIVE_LABEL = "综合审核商品信息";
    public static final String DOUDIAN_SYNC_LABEL = "抖店商品同步";





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