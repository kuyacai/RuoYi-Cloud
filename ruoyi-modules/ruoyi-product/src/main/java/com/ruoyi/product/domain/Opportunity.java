package com.ruoyi.product.domain;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ruoyi.common.core.annotation.Money;
import com.ruoyi.common.core.annotation.Rate;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "opportunities", autoResultMap = true)
public class Opportunity extends ProductBaseEntity {

    /* --- 基础标识 --- */
    @TableId(value = "clue_id", type = IdType.INPUT)
    private String clueId;

    private String queryId;
    private String platform;
    private String clueTitle;
    private String brandName;
    private String brandNameEn;
    private String productPicUrl;
    private String productPicUrlLocal;
    private String picUrlListFirst;
    private String picUrlListFirstLocal;
    private Integer relatedProductCnt;

    private Integer onlineProdCnt;
    /** 最低价（分） */
    @Money
    private Long priceMinCents;
    /** 最高价（分） */
    @Money
    private Long priceMaxCents;

    /* --- 核心评估指标 --- */
    private Integer searchHeat;

    /** 供需比（万分位） */
    @Rate
    private Long demandSupplyRate;

    private String payAmountRange;

    /**
     * 最高到手价（分）
     * 该金额特指上新扶持权益中的**商品到手价要求**
     */
    @Money
    @TableField("new_max_price_cents")
    private Long newMaxPriceCents;
    /* --- 审核/类目要求 --- */
    private String categoryPath;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> titleContains;

    private String mustSubmitSame;

    /* --- 动态数据 --- */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object benefits;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object sourceFiles;

    @TableField(exist = false)
    private List<Benefit> benefitList;
}