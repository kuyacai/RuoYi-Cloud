package com.ruoyi.product.domain;

import java.time.LocalDateTime;

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
@TableName(value = "shop_clue_relation", autoResultMap = true)
public class ShopClueRelation extends ProductBaseEntity {

    @TableId(value = "rel_id", type = IdType.INPUT)
    private String relId;

    private String clueId;

    private String platShopId;

    private String shopName;

    private Boolean isFavorite;

    private LocalDateTime favoriteTime;
}