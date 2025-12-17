package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 单品直降活动商品对象 direct_discount_product
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class DirectDiscountProduct extends BaseEntity
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
    private String shopSkuId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long fixedPrice;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long deductionAmount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long discountRate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long userLimit;

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

    public void setShopSkuId(String shopSkuId) 
    {
        this.shopSkuId = shopSkuId;
    }

    public String getShopSkuId() 
    {
        return shopSkuId;
    }

    public void setFixedPrice(Long fixedPrice) 
    {
        this.fixedPrice = fixedPrice;
    }

    public Long getFixedPrice() 
    {
        return fixedPrice;
    }

    public void setDeductionAmount(Long deductionAmount) 
    {
        this.deductionAmount = deductionAmount;
    }

    public Long getDeductionAmount() 
    {
        return deductionAmount;
    }

    public void setDiscountRate(Long discountRate) 
    {
        this.discountRate = discountRate;
    }

    public Long getDiscountRate() 
    {
        return discountRate;
    }

    public void setUserLimit(Long userLimit) 
    {
        this.userLimit = userLimit;
    }

    public Long getUserLimit() 
    {
        return userLimit;
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
            .append("shopSkuId", getShopSkuId())
            .append("fixedPrice", getFixedPrice())
            .append("deductionAmount", getDeductionAmount())
            .append("discountRate", getDiscountRate())
            .append("userLimit", getUserLimit())
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
