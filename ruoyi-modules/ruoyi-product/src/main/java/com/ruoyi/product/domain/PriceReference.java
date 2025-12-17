package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 价格参考对象 price_reference
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class PriceReference extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long originalPrice;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long multiplier;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long originalMarkedPrice;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long effectiveMarkedPrice;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long referenceShippingFee;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long actualDiscountAmount;

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

    public void setOriginalPrice(Long originalPrice) 
    {
        this.originalPrice = originalPrice;
    }

    public Long getOriginalPrice() 
    {
        return originalPrice;
    }

    public void setMultiplier(Long multiplier) 
    {
        this.multiplier = multiplier;
    }

    public Long getMultiplier() 
    {
        return multiplier;
    }

    public void setOriginalMarkedPrice(Long originalMarkedPrice) 
    {
        this.originalMarkedPrice = originalMarkedPrice;
    }

    public Long getOriginalMarkedPrice() 
    {
        return originalMarkedPrice;
    }

    public void setEffectiveMarkedPrice(Long effectiveMarkedPrice) 
    {
        this.effectiveMarkedPrice = effectiveMarkedPrice;
    }

    public Long getEffectiveMarkedPrice() 
    {
        return effectiveMarkedPrice;
    }

    public void setReferenceShippingFee(Long referenceShippingFee) 
    {
        this.referenceShippingFee = referenceShippingFee;
    }

    public Long getReferenceShippingFee() 
    {
        return referenceShippingFee;
    }

    public void setActualDiscountAmount(Long actualDiscountAmount) 
    {
        this.actualDiscountAmount = actualDiscountAmount;
    }

    public Long getActualDiscountAmount() 
    {
        return actualDiscountAmount;
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
            .append("originalPrice", getOriginalPrice())
            .append("multiplier", getMultiplier())
            .append("originalMarkedPrice", getOriginalMarkedPrice())
            .append("effectiveMarkedPrice", getEffectiveMarkedPrice())
            .append("referenceShippingFee", getReferenceShippingFee())
            .append("actualDiscountAmount", getActualDiscountAmount())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
