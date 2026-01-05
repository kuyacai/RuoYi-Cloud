package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.annotation.Money;
import com.ruoyi.common.core.annotation.Rate;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 价格参考对象 price_reference
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "price_reference", autoResultMap = true)
public class PriceReference extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @Money
    private Long originalPrice;
    @Rate(base = 10000)
    private Integer multiplier;
    @Money
    private Long originalMarkedPrice;
    @Money
    private Long effectiveMarkedPrice;
    @Money
    private Long referenceShippingFee;
    @Money
    private Long actualDiscountAmount;

}
