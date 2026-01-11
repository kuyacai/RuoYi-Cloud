package com.ruoyi.product.domain;

import java.time.Instant;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.ActivityProductStatus;
import com.ruoyi.product.enums.SyncStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 复购券活动商品对象 repurchase_coupon_product
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "repurchase_coupon_product", autoResultMap = true)
public class RepurchaseCouponProduct extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String activityId;

    private String shopProductId;

    private String shopId;

    private ActivityProductStatus itemStatus;

    private Instant addedTimeUtc;

    private Instant removedTimeUtc;

    private SyncStatus platformSyncStatus;

    private String platformErrorMsg;

}
