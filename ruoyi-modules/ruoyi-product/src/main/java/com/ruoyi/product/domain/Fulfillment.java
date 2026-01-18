package com.ruoyi.product.domain;

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
@TableName(value = "fulfillment", autoResultMap = true)
public class Fulfillment extends ProductBaseEntity {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String clueId;

    private String platShopId;

    private String shopName;

    private String productId;

    private String productTitle;

    private String auditStatus;

    private String auditMsg;

    private String benefitGap;
}