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
 * 单品直降活动对象 direct_discount_activity
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "direct_discount_activity", autoResultMap = true)
public class DirectDiscountActivity extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "activity_id", type = IdType.INPUT)
    private String activityId;

    private String activityName;

    private String shopId;
    // 目前仅支持立减
    private String discountType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String discountStatus;

    private String platformActivityId;

    private String notes;

}
