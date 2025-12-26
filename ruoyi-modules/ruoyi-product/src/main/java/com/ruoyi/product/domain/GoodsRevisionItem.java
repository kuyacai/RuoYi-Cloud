package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

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

    private Integer ship3dQty;

    private Integer ship4dQty;

    private Integer ship5dQty;

    private Integer ship7dQty;

    private Integer ship10dQty;

    private Integer ship15dQty;

    private Integer ship20dQty;

    private Integer ship25dQty;

    private Integer ship30dQty;

    private Integer ship35dQty;

    private Integer ship45dQty;

    private Integer orignialPrice;

    private Integer marketPrice;

    private Integer lowestPrice;

    private Integer highestPrice;

    private String skuStatus;

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
