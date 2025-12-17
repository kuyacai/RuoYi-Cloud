package com.ruoyi.product.constant;
/**
 * 图片类型
 */
public enum ImageType {

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

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
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