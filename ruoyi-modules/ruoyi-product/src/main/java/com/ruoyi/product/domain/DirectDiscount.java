package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 单品直降配置对象 direct_discount
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class DirectDiscount extends BaseEntity
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
    private Long fixedPrice;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long deductionAmount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long discountRate;

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
            .append("priceMin", getPriceMin())
            .append("priceMax", getPriceMax())
            .append("fixedPrice", getFixedPrice())
            .append("deductionAmount", getDeductionAmount())
            .append("discountRate", getDiscountRate())
            .append("actualDiscountAmount", getActualDiscountAmount())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
