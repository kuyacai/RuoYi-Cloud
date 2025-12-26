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
 * 新用户礼包活动商品对象 new_user_gift_product
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "new_user_gift_product", autoResultMap = true)
public class NewUserGiftProduct extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String activityId;

    private String shopProductId;

    private Integer avgAmount;

    private Integer maxGiftAmount;

    private String itemStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addedTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date removedTime;

    private String platformSyncStatus;

    private String platformErrorMsg;

}
