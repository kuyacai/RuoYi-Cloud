package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 新用户礼包配置对象 new_user_gift_config
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class NewUserGiftConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String configName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long priceMin;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long priceMax;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long avgAmount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long maxGiftAmount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long referenceAmountLow;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long referenceAmountHigh;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long discountRate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String configStatus;

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

    public void setConfigName(String configName) 
    {
        this.configName = configName;
    }

    public String getConfigName() 
    {
        return configName;
    }

    public void setPriceMin(Long priceMin) 
    {
        this.priceMin = priceMin;
    }

    public Long getPriceMin() 
    {
        return priceMin;
    }

    public void setPriceMax(Long priceMax) 
    {
        this.priceMax = priceMax;
    }

    public Long getPriceMax() 
    {
        return priceMax;
    }

    public void setAvgAmount(Long avgAmount) 
    {
        this.avgAmount = avgAmount;
    }

    public Long getAvgAmount() 
    {
        return avgAmount;
    }

    public void setMaxGiftAmount(Long maxGiftAmount) 
    {
        this.maxGiftAmount = maxGiftAmount;
    }

    public Long getMaxGiftAmount() 
    {
        return maxGiftAmount;
    }

    public void setReferenceAmountLow(Long referenceAmountLow) 
    {
        this.referenceAmountLow = referenceAmountLow;
    }

    public Long getReferenceAmountLow() 
    {
        return referenceAmountLow;
    }

    public void setReferenceAmountHigh(Long referenceAmountHigh) 
    {
        this.referenceAmountHigh = referenceAmountHigh;
    }

    public Long getReferenceAmountHigh() 
    {
        return referenceAmountHigh;
    }

    public void setDiscountRate(Long discountRate) 
    {
        this.discountRate = discountRate;
    }

    public Long getDiscountRate() 
    {
        return discountRate;
    }

    public void setConfigStatus(String configStatus) 
    {
        this.configStatus = configStatus;
    }

    public String getConfigStatus() 
    {
        return configStatus;
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
            .append("configName", getConfigName())
            .append("priceMin", getPriceMin())
            .append("priceMax", getPriceMax())
            .append("avgAmount", getAvgAmount())
            .append("maxGiftAmount", getMaxGiftAmount())
            .append("referenceAmountLow", getReferenceAmountLow())
            .append("referenceAmountHigh", getReferenceAmountHigh())
            .append("discountRate", getDiscountRate())
            .append("configStatus", getConfigStatus())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
