package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 平台促销活动对象 platform_promotion_activity
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class PlatformPromotionActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String activityId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String activityName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long registrationPrice;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String discountType;

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
    private String platformType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String eventCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date startTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date endTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String discountStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String platformActivityId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String notes;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date applyDeadline;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtCreate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtModified;

    public void setActivityId(String activityId) 
    {
        this.activityId = activityId;
    }

    public String getActivityId() 
    {
        return activityId;
    }

    public void setActivityName(String activityName) 
    {
        this.activityName = activityName;
    }

    public String getActivityName() 
    {
        return activityName;
    }

    public void setRegistrationPrice(Long registrationPrice) 
    {
        this.registrationPrice = registrationPrice;
    }

    public Long getRegistrationPrice() 
    {
        return registrationPrice;
    }

    public void setDiscountType(String discountType) 
    {
        this.discountType = discountType;
    }

    public String getDiscountType() 
    {
        return discountType;
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

    public void setPlatformType(String platformType) 
    {
        this.platformType = platformType;
    }

    public String getPlatformType() 
    {
        return platformType;
    }

    public void setEventCode(String eventCode) 
    {
        this.eventCode = eventCode;
    }

    public String getEventCode() 
    {
        return eventCode;
    }

    public void setShopId(String shopId) 
    {
        this.shopId = shopId;
    }

    public String getShopId() 
    {
        return shopId;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }

    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    public void setDiscountStatus(String discountStatus) 
    {
        this.discountStatus = discountStatus;
    }

    public String getDiscountStatus() 
    {
        return discountStatus;
    }

    public void setPlatformActivityId(String platformActivityId) 
    {
        this.platformActivityId = platformActivityId;
    }

    public String getPlatformActivityId() 
    {
        return platformActivityId;
    }

    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
    }

    public void setApplyDeadline(Date applyDeadline) 
    {
        this.applyDeadline = applyDeadline;
    }

    public Date getApplyDeadline() 
    {
        return applyDeadline;
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
            .append("activityId", getActivityId())
            .append("activityName", getActivityName())
            .append("registrationPrice", getRegistrationPrice())
            .append("discountType", getDiscountType())
            .append("fixedPrice", getFixedPrice())
            .append("deductionAmount", getDeductionAmount())
            .append("discountRate", getDiscountRate())
            .append("platformType", getPlatformType())
            .append("eventCode", getEventCode())
            .append("shopId", getShopId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("discountStatus", getDiscountStatus())
            .append("platformActivityId", getPlatformActivityId())
            .append("notes", getNotes())
            .append("applyDeadline", getApplyDeadline())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
