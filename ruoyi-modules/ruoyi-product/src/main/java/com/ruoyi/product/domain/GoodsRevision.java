package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商品版本对象 goods_revision
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class GoodsRevision extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String revisionId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String goodsId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String revStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String revisionType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer isCurrent;

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

    public void setGoodsId(String goodsId) 
    {
        this.goodsId = goodsId;
    }

    public String getGoodsId() 
    {
        return goodsId;
    }

    public void setRevStatus(String revStatus) 
    {
        this.revStatus = revStatus;
    }

    public String getRevStatus() 
    {
        return revStatus;
    }

    public void setRevisionType(String revisionType) 
    {
        this.revisionType = revisionType;
    }

    public String getRevisionType() 
    {
        return revisionType;
    }

    public void setIsCurrent(Integer isCurrent) 
    {
        this.isCurrent = isCurrent;
    }

    public Integer getIsCurrent() 
    {
        return isCurrent;
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
            .append("revisionId", getRevisionId())
            .append("goodsId", getGoodsId())
            .append("revStatus", getRevStatus())
            .append("revisionType", getRevisionType())
            .append("isCurrent", getIsCurrent())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
