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
@TableName(value = "douyin_keywords", autoResultMap = true)
public class DouyinKeywords extends ProductBaseEntity {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String keyword;

    private String status;

    private Integer oppCount;

}
