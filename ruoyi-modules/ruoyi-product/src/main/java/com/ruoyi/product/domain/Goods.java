package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 云商品根对象 goods
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "goods", autoResultMap = true)
public class Goods extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "goods_id", type = IdType.INPUT)
    private String goodsId;

    private String migrateSource;

    private String sourceShopName;

    private String sourceId;

    private String sourceTitle;

    private String sourceUrl;

    private String sourceCategory;

    private String customerPhone;

    private String sourceItemNo;

    private String brand;

    private String shopProductId;

    private String shopId;

    private String goodsStatus;

}
