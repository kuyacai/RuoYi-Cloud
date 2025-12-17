package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * SPU 快照对象 goods_revision_spu
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class GoodsRevisionSpu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String revisionId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String title;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String guideShortTitle;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String recommendation;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String freightTemplate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String attributes;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sizeChartTemplateName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sizeChartSizeTitles;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long sales;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String copyStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String copyErrorReason;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String reviewStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shippingMode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String inStockShipTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String presaleShipTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtCreate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtModified;

    public void setRevisionId(String revisionId) 
    {
        this.revisionId = revisionId;
    }

    public String getRevisionId() 
    {
        return revisionId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setGuideShortTitle(String guideShortTitle) 
    {
        this.guideShortTitle = guideShortTitle;
    }

    public String getGuideShortTitle() 
    {
        return guideShortTitle;
    }

    public void setRecommendation(String recommendation) 
    {
        this.recommendation = recommendation;
    }

    public String getRecommendation() 
    {
        return recommendation;
    }

    public void setFreightTemplate(String freightTemplate) 
    {
        this.freightTemplate = freightTemplate;
    }

    public String getFreightTemplate() 
    {
        return freightTemplate;
    }

    public void setAttributes(String attributes) 
    {
        this.attributes = attributes;
    }

    public String getAttributes() 
    {
        return attributes;
    }

    public void setSizeChartTemplateName(String sizeChartTemplateName) 
    {
        this.sizeChartTemplateName = sizeChartTemplateName;
    }

    public String getSizeChartTemplateName() 
    {
        return sizeChartTemplateName;
    }

    public void setSizeChartSizeTitles(String sizeChartSizeTitles) 
    {
        this.sizeChartSizeTitles = sizeChartSizeTitles;
    }

    public String getSizeChartSizeTitles() 
    {
        return sizeChartSizeTitles;
    }

    public void setSales(Long sales) 
    {
        this.sales = sales;
    }

    public Long getSales() 
    {
        return sales;
    }

    public void setCopyStatus(String copyStatus) 
    {
        this.copyStatus = copyStatus;
    }

    public String getCopyStatus() 
    {
        return copyStatus;
    }

    public void setCopyErrorReason(String copyErrorReason) 
    {
        this.copyErrorReason = copyErrorReason;
    }

    public String getCopyErrorReason() 
    {
        return copyErrorReason;
    }

    public void setReviewStatus(String reviewStatus) 
    {
        this.reviewStatus = reviewStatus;
    }

    public String getReviewStatus() 
    {
        return reviewStatus;
    }

    public void setShippingMode(String shippingMode) 
    {
        this.shippingMode = shippingMode;
    }

    public String getShippingMode() 
    {
        return shippingMode;
    }

    public void setInStockShipTime(String inStockShipTime) 
    {
        this.inStockShipTime = inStockShipTime;
    }

    public String getInStockShipTime() 
    {
        return inStockShipTime;
    }

    public void setPresaleShipTime(String presaleShipTime) 
    {
        this.presaleShipTime = presaleShipTime;
    }

    public String getPresaleShipTime() 
    {
        return presaleShipTime;
    }

    public void setGmtCreate(Date gmtCreate) 
    {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() 
    {
        return gmtCreate;
    }
    

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("revisionId", getRevisionId())
            .append("title", getTitle())
            .append("guideShortTitle", getGuideShortTitle())
            .append("recommendation", getRecommendation())
            .append("freightTemplate", getFreightTemplate())
            .append("attributes", getAttributes())
            .append("sizeChartTemplateName", getSizeChartTemplateName())
            .append("sizeChartSizeTitles", getSizeChartSizeTitles())
            .append("sales", getSales())
            .append("copyStatus", getCopyStatus())
            .append("copyErrorReason", getCopyErrorReason())
            .append("reviewStatus", getReviewStatus())
            .append("shippingMode", getShippingMode())
            .append("inStockShipTime", getInStockShipTime())
            .append("presaleShipTime", getPresaleShipTime())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
