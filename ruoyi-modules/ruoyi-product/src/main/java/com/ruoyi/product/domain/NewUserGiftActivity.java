package com.ruoyi.product.domain;

import java.time.Instant;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 新用户礼包活动对象 new_user_gift_activity
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "new_user_gift_activity", autoResultMap = true)
public class NewUserGiftActivity extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_id", type = IdType.INPUT)
    private String activityId;

    private String activityName;

    private String discountType;

    private Integer deductionAmount;

    private Integer discountRate;

    private String shopId;

    private Instant startTimeUtc;

    private Instant endTimeUtc;

    private String activityStatus;

    private String platformActivityId;

    private String notes;

    private Integer minOrderAmount;

}
