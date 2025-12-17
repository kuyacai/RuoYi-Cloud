package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 店铺商品SKU同步状态对象 shop_listing_sku
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class ShopListingSku extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String listingSkuId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String listingSpuId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopProductId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopSkuId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String itemId;

    /** 分 */
    @Excel(name = "分")
    private Long marketPrice;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long channelStock;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String skuStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtCreate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtModified;

    public void setListingSkuId(String listingSkuId) 
    {
        this.listingSkuId = listingSkuId;
    }

    public String getListingSkuId() 
    {
        return listingSkuId;
    }

    public void setListingSpuId(String listingSpuId) 
    {
        this.listingSpuId = listingSpuId;
    }

    public String getListingSpuId() 
    {
        return listingSpuId;
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

    public void setShopSkuId(String shopSkuId) 
    {
        this.shopSkuId = shopSkuId;
    }

    public String getShopSkuId() 
    {
        return shopSkuId;
    }

    public void setItemId(String itemId) 
    {
        this.itemId = itemId;
    }

    public String getItemId() 
    {
        return itemId;
    }

    public void setMarketPrice(Long marketPrice) 
    {
        this.marketPrice = marketPrice;
    }

    public Long getMarketPrice() 
    {
        return marketPrice;
    }

    public void setChannelStock(Long channelStock) 
    {
        this.channelStock = channelStock;
    }

    public Long getChannelStock() 
    {
        return channelStock;
    }

    public void setSkuStatus(String skuStatus) 
    {
        this.skuStatus = skuStatus;
    }

    public String getSkuStatus() 
    {
        return skuStatus;
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
            .append("listingSkuId", getListingSkuId())
            .append("listingSpuId", getListingSpuId())
            .append("shopId", getShopId())
            .append("shopProductId", getShopProductId())
            .append("shopSkuId", getShopSkuId())
            .append("itemId", getItemId())
            .append("marketPrice", getMarketPrice())
            .append("channelStock", getChannelStock())
            .append("skuStatus", getSkuStatus())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
