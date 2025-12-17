package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 店铺商品同步状态对象 shop_listing_spu
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class ShopListingSpu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String listingSpuId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String revisionId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopProductId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String category;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productUrl;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String currStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtCreate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtModified;

    public void setListingSpuId(String listingSpuId) 
    {
        this.listingSpuId = listingSpuId;
    }

    public String getListingSpuId() 
    {
        return listingSpuId;
    }

    public void setRevisionId(String revisionId) 
    {
        this.revisionId = revisionId;
    }

    public String getRevisionId() 
    {
        return revisionId;
    }

    public void setShopId(String shopId) 
    {
        this.shopId = shopId;
    }

    public String getShopId() 
    {
        return shopId;
    }

    public void setShopProductId(String shopProductId) 
    {
        this.shopProductId = shopProductId;
    }

    public String getShopProductId() 
    {
        return shopProductId;
    }

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }

    public void setProductUrl(String productUrl) 
    {
        this.productUrl = productUrl;
    }

    public String getProductUrl() 
    {
        return productUrl;
    }

    public void setCurrStatus(String currStatus) 
    {
        this.currStatus = currStatus;
    }

    public String getCurrStatus() 
    {
        return currStatus;
    }

    public void setGmtCreate(Date gmtCreate) 
    {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() 
    {
        return gmtCreate;
    }

    public void setGmtModified(Date gmtModified) 
    {
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified() 
    {
        return gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("listingSpuId", getListingSpuId())
            .append("revisionId", getRevisionId())
            .append("shopId", getShopId())
            .append("shopProductId", getShopProductId())
            .append("category", getCategory())
            .append("productUrl", getProductUrl())
            .append("currStatus", getCurrStatus())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
