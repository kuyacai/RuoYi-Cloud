package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.CopyStatus;
import com.ruoyi.product.enums.ReviewStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * SPU 快照对象 goods_revision_spu
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "goods_revision_spu", autoResultMap = true)
public class GoodsRevisionSpu extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "revision_id", type = IdType.INPUT)
    private String revisionId;

    private String title;

    private String guideShortTitle;

    private String newTitle;

    private String newGuideShortTitle;

    private String recommendation;

    private String freightTemplate;

    private String attributes;

    private String sizeChartTemplateName;

    private String sizeChartSizeTitles;

    private Integer sales;

    private CopyStatus copyStatus;

    private String copyErrorReason;

    private ReviewStatus reviewStatus;

    private String shippingMode;

    private String inStockShipTime;

    private String presaleShipTime;

    private String searchKeywords;

    private String videoScript;

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
