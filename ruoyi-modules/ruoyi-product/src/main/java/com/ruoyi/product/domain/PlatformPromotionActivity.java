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
 * 平台促销活动对象 platform_promotion_activity
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "platform_promotion_activity", autoResultMap = true)
public class PlatformPromotionActivity extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_id", type = IdType.INPUT)
    private String activityId;

    private String activityName;

    private Integer registrationPrice;

    private String discountType;

    private Integer fixedPrice;

    private Integer deductionAmount;

    private Integer discountRate;

    private String platformType;

    private String eventCode;

    private String shopId;

    private String configId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String discountStatus;

    private String platformActivityId;

    private String notes;

    private Date applyDeadline;

}
