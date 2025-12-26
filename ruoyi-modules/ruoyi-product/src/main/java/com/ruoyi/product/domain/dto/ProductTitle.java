package com.ruoyi.product.domain.dto;

import java.io.Serializable;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;

import lombok.Builder;
import lombok.Data;

@Data
//@Builder
public class ProductTitle implements Serializable{

    private static final long serialVersionUID = 1L;

    @Excel(name = "商品ID", type = Type.IMPORT)
    private String productId;

    @Excel(name = "商品标题", type = Type.IMPORT)
    private String title;

    @Excel(name = "商品新标题", type = Type.IMPORT)
    private String newTitle;

    @Excel(name = "导购短标题", type = Type.IMPORT)
    private String guideShortTitle;

    @Excel(name = "覆盖关键词", type = Type.IMPORT)
    private String searchKeywords;

    @Excel(name = "AI视频介绍文案", type = Type.IMPORT)
    private String videoScript;

    public ProductTitle() {
    }

    
}
