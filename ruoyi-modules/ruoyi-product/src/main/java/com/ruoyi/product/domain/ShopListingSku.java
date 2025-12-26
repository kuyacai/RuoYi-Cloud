package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 店铺商品SKU同步状态对象 shop_listing_sku
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "shop_listing_sku", autoResultMap = true)
public class ShopListingSku extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "listing_sku_id", type = IdType.INPUT)
    private String listingSkuId;

    private String listingSpuId;

    private String shopId;

    private String shopProductId;

    private String shopSkuId;

    private String itemId;

    private Integer marketPrice;

    private Integer channelStock;

    private String skuStatus;

}
