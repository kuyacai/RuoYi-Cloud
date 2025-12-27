package com.ruoyi.product.domain.dto;

import java.io.Serializable;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;

//import lombok.Builder;
//mport lombok.Data;

//@Data
//@Builder
public class SimpleProduct implements Serializable{

    private static final long serialVersionUID = 1L;

    @Excel(name = "商品ID", type = Type.IMPORT)
    private String productId;

    @Excel(name = "商品标题", type = Type.IMPORT)
    private String title;

    @Excel(name = "SKUID", type = Type.IMPORT)
    private String skuId;

    public SimpleProduct() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    
    
}
