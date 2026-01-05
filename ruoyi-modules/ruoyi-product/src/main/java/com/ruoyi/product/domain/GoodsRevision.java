package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.RevStatus;
import com.ruoyi.product.enums.RevisionType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 商品版本对象 goods_revision
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "goods_revision", autoResultMap = true)
public class GoodsRevision extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "revision_id", type = IdType.INPUT)
    private String revisionId;

    private String goodsId;

    private RevStatus revStatus;

    private RevisionType revisionType;

}
