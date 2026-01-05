package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.SkuStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * SKU 快照对象 goods_revision_item
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "goods_revision_item", autoResultMap = true)
public class GoodsRevisionItem extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "item_id", type = IdType.INPUT)
    private String itemId;

    private String revisionId;

    private String shopId;

    private String shopProductId;

    private String shopSkuId;

    private String skuCode;

    private String sellerSku;

    private String spec1;

    private String spec1Note;

    private String spec2;

    private String spec2Note;

    private String spec3OrLeadTime;

    private String spec3Note;

    private Integer inStockQty;

    private Integer fullPrepayQty;

    @TableField("ship_3d_qty")
    private Integer ship3dQty;
    @TableField("ship_4d_qty")
    private Integer ship4dQty;
    @TableField("ship_5d_qty")
    private Integer ship5dQty;
    @TableField("ship_7d_qty")
    private Integer ship7dQty;
    @TableField("ship_10d_qty")
    private Integer ship10dQty;
    @TableField("ship_15d_qty")
    private Integer ship15dQty;
    @TableField("ship_20d_qty")
    private Integer ship20dQty;
    @TableField("ship_25d_qty")
    private Integer ship25dQty;
    @TableField("ship_30d_qty")
    private Integer ship30dQty;
    @TableField("ship_35d_qty")
    private Integer ship35dQty;
    @TableField("ship_45d_qty")
    private Integer ship45dQty;

    private Integer orignialPrice;

    private Integer marketPrice;

    private Integer lowestPrice;

    private Integer highestPrice;

    private SkuStatus skuStatus;

    private String barcode;

    // 关联查询字段 - 用于查询需要修改标题导购标题的spu
    /** 关联任务ID (非数据库表字段) */
    @TableField(exist = false)
    private String taskId;

    /** 关联任务代码 (非数据库表字段) */
    @TableField(exist = false)
    private String taskCode;

    /** 关联任务状态 (非数据库表字段) */
    @TableField(exist = false)
    private String taskStatus;

}
