package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * goods 图片对象 goods_revision_image
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "goods_revision_image", autoResultMap = true)
public class GoodsRevisionImage extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "image_id", type = IdType.INPUT)
    private String imageId;

    private String revisionId;

    private String goodsId;

    private String goodsSkuId;

    private String imageType;

    private String sourceUrl;

    private String selfUrl;

    private String localUri;

    private Integer position;

}
