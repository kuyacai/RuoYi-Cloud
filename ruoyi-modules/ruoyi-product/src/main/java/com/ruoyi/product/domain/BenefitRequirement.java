package com.ruoyi.product.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("benefit_requirement")
public class BenefitRequirement extends ProductBaseEntity {

    @TableId(value = "req_id", type = IdType.INPUT)
    private String reqId;

    private String benefitId;
    private String attrValue;
    private String attrSymbol;

    /** 解析后边界 */
    private BigDecimal minNumeric;
    private BigDecimal maxNumeric;

    /** 1 包含 0 不包含 */
    private Integer minInclusive;
    private Integer maxInclusive;
}