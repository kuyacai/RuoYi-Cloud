package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 复购券活动商品对象 repurchase_coupon_product
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class RepurchaseCouponProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String activityId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopProductId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String itemStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date addedTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date removedTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String platformSyncStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String platformErrorMsg;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtCreate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtModified;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    public void setActivityId(String activityId) 
    {
        this.activityId = activityId;
    }

    public String getActivityId() 
    {
        return activityId;
    }

    public void setShopProductId(String shopProductId) 
    {
        this.shopProductId = shopProductId;
    }

    public String getShopProductId() 
    {
        return shopProductId;
    }

    public void setItemStatus(String itemStatus) 
    {
        this.itemStatus = itemStatus;
    }

    public String getItemStatus() 
    {
        return itemStatus;
    }

    public void setAddedTime(Date addedTime) 
    {
        this.addedTime = addedTime;
    }

    public Date getAddedTime() 
    {
        return addedTime;
    }

    public void setRemovedTime(Date removedTime) 
    {
        this.removedTime = removedTime;
    }

    public Date getRemovedTime() 
    {
        return removedTime;
    }

    public void setPlatformSyncStatus(String platformSyncStatus) 
    {
        this.platformSyncStatus = platformSyncStatus;
    }

    public String getPlatformSyncStatus() 
    {
        return platformSyncStatus;
    }

    public void setPlatformErrorMsg(String platformErrorMsg) 
    {
        this.platformErrorMsg = platformErrorMsg;
    }

    public String getPlatformErrorMsg() 
    {
        return platformErrorMsg;
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
            .append("id", getId())
            .append("activityId", getActivityId())
            .append("shopProductId", getShopProductId())
            .append("itemStatus", getItemStatus())
            .append("addedTime", getAddedTime())
            .append("removedTime", getRemovedTime())
            .append("platformSyncStatus", getPlatformSyncStatus())
            .append("platformErrorMsg", getPlatformErrorMsg())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
