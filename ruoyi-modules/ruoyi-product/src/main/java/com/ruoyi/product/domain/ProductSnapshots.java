package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.annotation.Money;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.PlatformType;
import com.ruoyi.product.enums.ProductSnapshotStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "product_snapshots", autoResultMap = true)
public class ProductSnapshots extends ProductBaseEntity {

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String traceId;

    private PlatformType platform;

    private String originalImage;

    private String subFileName;

    private String subFilePath;

    private Long phash;

    private Integer indexInPage;

    private Integer rectX;

    private Integer rectY;

    private Integer rectW;

    private Integer rectH;

    private String title;

    @Money
    private Long price;

    private Integer salesCount;

    private String targetKeyword;

    private String localProductId;

    private ProductSnapshotStatus status;

}
