package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 云商品根对象 goods
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class Goods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String goodsId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String migrateSource;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sourceShopName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sourceId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sourceTitle;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sourceUrl;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sourceCategory;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String customerPhone;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sourceItemNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String brand;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopProductId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtCreate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtModified;

    public void setGoodsId(String goodsId) 
    {
        this.goodsId = goodsId;
    }

    public String getGoodsId() 
    {
        return goodsId;
    }

    public void setMigrateSource(String migrateSource) 
    {
        this.migrateSource = migrateSource;
    }

    public String getMigrateSource() 
    {
        return migrateSource;
    }

    public void setSourceShopName(String sourceShopName) 
    {
        this.sourceShopName = sourceShopName;
    }

    public String getSourceShopName() 
    {
        return sourceShopName;
    }

    public void setSourceId(String sourceId) 
    {
        this.sourceId = sourceId;
    }

    public String getSourceId() 
    {
        return sourceId;
    }

    public void setSourceTitle(String sourceTitle) 
    {
        this.sourceTitle = sourceTitle;
    }

    public String getSourceTitle() 
    {
        return sourceTitle;
    }

    public void setSourceUrl(String sourceUrl) 
    {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceUrl() 
    {
        return sourceUrl;
    }

    public void setSourceCategory(String sourceCategory) 
    {
        this.sourceCategory = sourceCategory;
    }

    public String getSourceCategory() 
    {
        return sourceCategory;
    }

    public void setCustomerPhone(String customerPhone) 
    {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone() 
    {
        return customerPhone;
    }

    public void setSourceItemNo(String sourceItemNo) 
    {
        this.sourceItemNo = sourceItemNo;
    }

    public String getSourceItemNo() 
    {
        return sourceItemNo;
    }

    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }

    public void setShopProductId(String shopProductId) 
    {
        this.shopProductId = shopProductId;
    }

    public String getShopProductId() 
    {
        return shopProductId;
    }

    public void setShopId(String shopId) 
    {
        this.shopId = shopId;
    }

    public String getShopId() 
    {
        return shopId;
    }

    public void setGoodsStatus(String goodsStatus) 
    {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsStatus() 
    {
        return goodsStatus;
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
            .append("goodsId", getGoodsId())
            .append("migrateSource", getMigrateSource())
            .append("sourceShopName", getSourceShopName())
            .append("sourceId", getSourceId())
            .append("sourceTitle", getSourceTitle())
            .append("sourceUrl", getSourceUrl())
            .append("sourceCategory", getSourceCategory())
            .append("customerPhone", getCustomerPhone())
            .append("sourceItemNo", getSourceItemNo())
            .append("brand", getBrand())
            .append("shopProductId", getShopProductId())
            .append("shopId", getShopId())
            .append("goodsStatus", getGoodsStatus())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
