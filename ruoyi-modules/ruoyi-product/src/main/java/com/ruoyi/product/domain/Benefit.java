package com.ruoyi.product.domain;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("benefit")
public class Benefit extends ProductBaseEntity {

    @TableId(value = "benefit_id", type = IdType.INPUT)
    private String benefitId;

    private String clueId;
    private String profitId;
    private String profitName;
    private String profitText;
    private String profitImgUrl;
    private String profitImgLocal;
    private String profitTime;

    @TableField(exist = false)
    private List<BenefitRequirement> requirements;

}