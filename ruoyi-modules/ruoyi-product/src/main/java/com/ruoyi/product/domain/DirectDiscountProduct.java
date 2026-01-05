package com.ruoyi.product.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Money;
import com.ruoyi.common.core.annotation.Rate;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.ActivityProductStatus;
import com.ruoyi.product.enums.SyncStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 单品直降活动商品对象 direct_discount_product
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "direct_discount_product", autoResultMap = true)
public class DirectDiscountProduct extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String activityId;

    private String shopProductId;

    private String shopSkuId;

    private String shopId;
    // 一口价
    private Integer fixedPrice;
    // 立减金额
    // TODO: 目前仅支持立减模式 (actualDiscountAmount)，后续需扩展“一口价”和“折扣率”核算逻辑。
    @Money
    private Long deductionAmount;
    /**
     * 折扣率（存储万分位）
     * 数据库存：7500 (代表 75% 或 7.5折)
     * 前端展示：75.00 (通过 scale=2 保持两位小数)
     */
    @Rate(base = 10000, scale = 2)
    private Integer discountRate;
    // 默认限制2
    private Integer userLimit;
    // 对应商品sku状态(active/removed)
    private ActivityProductStatus itemStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addedTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date removedTime;

    private SyncStatus platformSyncStatus;

    private String platformErrorMsg;

}
