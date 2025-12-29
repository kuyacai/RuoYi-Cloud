package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.ConfigStatus;
import com.ruoyi.product.enums.DiscountType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 复购券配置对象 repurchase_coupon_config
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "repurchase_coupon_config", autoResultMap = true)
public class RepurchaseCouponConfig extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "config_id", type = IdType.INPUT)
    private String configId;

    private String configName;

    private Integer priceMin;

    private Integer priceMax;

    private Integer thresholdAmount;

    private Integer discountAmount;

    private Integer discountRate;

    private Integer discountStrength;

    private Integer priority;

    private ConfigStatus configStatus;

    private DiscountType discountType;

    private Integer recommendedQuantity;

}
