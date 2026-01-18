package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 店铺对象 shop
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "shop", autoResultMap = true)
public class Shop extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "shop_id", type = IdType.INPUT)
    private String shopId;

    private String shopName;

    private String platform;

    private String platShopId;

    private String ownerId;

    private String shopStatus;

    private String shopDescription;

}
