package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 店铺商品同步状态对象 shop_listing_spu
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "shop_listing_spu", autoResultMap = true)
public class ShopListingSpu extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "listing_spu_id", type = IdType.INPUT)
    private String listingSpuId;

    private String revisionId;

    private String shopId;

    private String shopProductId;

    private String category;

    private String productUrl;

    private String currStatus;

}
