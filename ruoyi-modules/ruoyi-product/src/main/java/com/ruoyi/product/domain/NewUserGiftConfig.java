package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.ConfigStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 新用户礼包配置对象 new_user_gift_config
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "new_user_gift_config", autoResultMap = true)
public class NewUserGiftConfig extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "config_id", type = IdType.INPUT)
    private String configId;

    private String configName;

    private Integer priceMin;

    private Integer priceMax;

    private Integer avgAmount;

    private Integer maxGiftAmount;

    private Integer referenceAmountLow;

    private Integer referenceAmountHigh;

    private Integer discountRate;

    private ConfigStatus configStatus;

}
