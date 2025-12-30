package com.ruoyi.product.domain.dto;

import java.io.Serializable;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;
import com.ruoyi.product.core.annotation.ExcelBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 必须：给 ExcelUtil 反射创建实例用
@AllArgsConstructor // 建议：在使用 @Builder 时必须配合全参构造
@ExcelBusiness(value = "product_title", exportName = "标题优化", templateName = "标题优化")
public class ProductTitle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "商品ID", type = Type.ALL)
    private String productId;

    @Excel(name = "商品标题", type = Type.ALL)
    private String title;

    @Excel(name = "商品新标题", type = Type.ALL)
    private String newTitle;

    @Excel(name = "导购短标题", type = Type.ALL)
    private String guideShortTitle;

    @Excel(name = "覆盖关键词", type = Type.ALL)
    private String searchKeywords;

    @Excel(name = "AI视频介绍文案", type = Type.ALL)
    private String videoScript;

}
