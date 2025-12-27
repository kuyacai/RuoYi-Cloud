package com.ruoyi.product.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 复购券活动对象 repurchase_coupon_activity
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "repurchase_coupon_activity", autoResultMap = true)
public class RepurchaseCouponActivity extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_id", type = IdType.INPUT)
    private String activityId;

    private String activityName;

    private String discountType;

    private Integer deductionAmount;

    private Integer discountRate;

    private String shopId;

    private String configId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String activityStatus;

    private String platformActivityId;

    private String notes;

    private Integer minOrderCount;

    private Integer userLimit;

}
