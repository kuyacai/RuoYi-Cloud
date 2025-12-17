package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 平台促销配置对象 platform_promotion_config
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class PlatformPromotionConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long priceMin;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long priceMax;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long thresholdAmount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long discountAmount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long discountRate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long discountStrength;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long priority;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String configId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String configName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String discountStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String discountType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long recommendedQuantity;

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

    public void setThresholdAmount(Long thresholdAmount) 
    {
        this.thresholdAmount = thresholdAmount;
    }

    public Long getThresholdAmount() 
    {
        return thresholdAmount;
    }

    public void setDiscountAmount(Long discountAmount) 
    {
        this.discountAmount = discountAmount;
    }

    public Long getDiscountAmount() 
    {
        return discountAmount;
    }

    public void setDiscountRate(Long discountRate) 
    {
        this.discountRate = discountRate;
    }

    public Long getDiscountRate() 
    {
        return discountRate;
    }

    public void setDiscountStrength(Long discountStrength) 
    {
        this.discountStrength = discountStrength;
    }

    public Long getDiscountStrength() 
    {
        return discountStrength;
    }

    public void setPriority(Long priority) 
    {
        this.priority = priority;
    }

    public Long getPriority() 
    {
        return priority;
    }

    public void setConfigId(String configId) 
    {
        this.configId = configId;
    }

    public String getConfigId() 
    {
        return configId;
    }

    public void setConfigName(String configName) 
    {
        this.configName = configName;
    }

    public String getConfigName() 
    {
        return configName;
    }

    public void setDiscountStatus(String discountStatus) 
    {
        this.discountStatus = discountStatus;
    }

    public String getDiscountStatus() 
    {
        return discountStatus;
    }

    public void setDiscountType(String discountType) 
    {
        this.discountType = discountType;
    }

    public String getDiscountType() 
    {
        return discountType;
    }

    public void setRecommendedQuantity(Long recommendedQuantity) 
    {
        this.recommendedQuantity = recommendedQuantity;
    }

    public Long getRecommendedQuantity() 
    {
        return recommendedQuantity;
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
            .append("priceMin", getPriceMin())
            .append("priceMax", getPriceMax())
            .append("thresholdAmount", getThresholdAmount())
            .append("discountAmount", getDiscountAmount())
            .append("discountRate", getDiscountRate())
            .append("discountStrength", getDiscountStrength())
            .append("priority", getPriority())
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("discountStatus", getDiscountStatus())
            .append("discountType", getDiscountType())
            .append("recommendedQuantity", getRecommendedQuantity())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
