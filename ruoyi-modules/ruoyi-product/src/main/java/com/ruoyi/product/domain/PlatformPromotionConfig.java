package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 平台促销配置对象 platform_promotion_config
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "platform_promotion_config", autoResultMap = true)
public class PlatformPromotionConfig extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private Integer priceMin;

    private Integer priceMax;

    private Integer thresholdAmount;

    private Integer discountAmount;

    private Integer discountRate;

    private Integer discountStrength;

    private Integer priority;

    private String configId;

    private String configName;

    private String discountStatus;

    private String discountType;

    private Integer recommendedQuantity;

}
