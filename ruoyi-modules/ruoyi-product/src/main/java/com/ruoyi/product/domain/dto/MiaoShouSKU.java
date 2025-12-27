package com.ruoyi.product.domain.dto;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 妙手商品-SKU 导入 DTO
 */
@Data
//@Builder
public class MiaoShouSKU implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "商品ID", type = Type.IMPORT)
    private String productId;

    @Excel(name = "商品标题", type = Type.IMPORT)
    private String title;

    @Excel(name = "SKUID", type = Type.IMPORT)
    private String skuId;

    @Excel(name = "导购短标题", type = Type.IMPORT)
    private String guideShortTitle;

    @Excel(name = "SKU商家编码", type = Type.IMPORT)
    private String sellerSku;

    @Excel(name = "规格1", type = Type.IMPORT)
    private String spec1;

    @Excel(name = "规格1备注", type = Type.IMPORT)
    private String spec1Note;

    @Excel(name = "规格2", type = Type.IMPORT)
    private String spec2;

    @Excel(name = "规格2备注", type = Type.IMPORT)
    private String spec2Note;

    @Excel(name = "规格3或发货时效", type = Type.IMPORT)
    private String spec3OrLeadTime;

    @Excel(name = "规格3备注", type = Type.IMPORT)
    private String spec3Note;

    @Excel(name = "主图1", type = Type.IMPORT)
    private String mainImage1;

    @Excel(name = "主图2", type = Type.IMPORT)
    private String mainImage2;

    @Excel(name = "主图3", type = Type.IMPORT)
    private String mainImage3;

    @Excel(name = "主图4", type = Type.IMPORT)
    private String mainImage4;

    @Excel(name = "主图5", type = Type.IMPORT)
    private String mainImage5;

    @Excel(name = "3:4主图1", type = Type.IMPORT)
    private String mainImage341;

    @Excel(name = "3:4主图2", type = Type.IMPORT)
    private String mainImage342;

    @Excel(name = "3:4主图3", type = Type.IMPORT)
    private String mainImage343;

    @Excel(name = "3:4主图4", type = Type.IMPORT)
    private String mainImage344;

    @Excel(name = "3:4主图5", type = Type.IMPORT)
    private String mainImage345;

    @Excel(name = "规格图", type = Type.IMPORT)
    private String specImage;

    @Excel(name = "现货库存", type = Type.IMPORT)
    private Integer inStockQty;

    @Excel(name = "全款预售库存", type = Type.IMPORT)
    private Integer fullPrepayQty;

    @Excel(name = "3天内发货库存", type = Type.IMPORT)
    private Integer ship3dQty;

    @Excel(name = "4天内发货库存", type = Type.IMPORT)
    private Integer ship4dQty;

    @Excel(name = "5天内发货库存", type = Type.IMPORT)
    private Integer ship5dQty;

    @Excel(name = "7天内发货库存", type = Type.IMPORT)
    private Integer ship7dQty;

    @Excel(name = "10天内发货库存", type = Type.IMPORT)
    private Integer ship10dQty;

    @Excel(name = "15天内发货库存", type = Type.IMPORT)
    private Integer ship15dQty;

    @Excel(name = "20天内发货库存", type = Type.IMPORT)
    private Integer ship20dQty;

    @Excel(name = "25天内发货库存", type = Type.IMPORT)
    private Integer ship25dQty;

    @Excel(name = "30天内发货库存", type = Type.IMPORT)
    private Integer ship30dQty;

    @Excel(name = "35天内发货库存", type = Type.IMPORT)
    private Integer ship35dQty;

    @Excel(name = "45天内发货库存", type = Type.IMPORT)
    private Integer ship45dQty;

    @Excel(name = "售价", type = Type.IMPORT)
    private BigDecimal price;

    @Excel(name = "最低售价", type = Type.IMPORT)
    private BigDecimal lowestPrice;

    @Excel(name = "最高售价", type = Type.IMPORT)
    private BigDecimal highestPrice;

    @Excel(name = "SKU条形码", type = Type.IMPORT)
    private String barcode;

    @Excel(name = "货号", type = Type.IMPORT)
    private String itemNo;

    @Excel(name = "分类", type = Type.IMPORT)
    private String category;

    @Excel(name = "商品状态", type = Type.IMPORT)
    private String status;

    @Excel(name = "来源店铺名称", type = Type.IMPORT)
    private String sourceShopName;

    @Excel(name = "来源ID", type = Type.IMPORT)
    private String sourceId;

    @Excel(name = "来源标题", type = Type.IMPORT)
    private String sourceTitle;

    @Excel(name = "来源链接", type = Type.IMPORT)
    private String sourceUrl;

    @Excel(name = "来源SKU分类", type = Type.IMPORT)
    private String sourceSkuCategory;

    @Excel(name = "详情图链接", type = Type.IMPORT)
    private String detailImageUrls;

    @Excel(name = "商品链接", type = Type.IMPORT)
    private String productUrl;

    @Excel(name = "SKU主图", type = Type.IMPORT)
    private String skuMainImage;

    @Excel(name = "SKU状态", type = Type.IMPORT)
    private String skuStatus;

    @Excel(name = "尺码表模板名称", type = Type.IMPORT)
    private String sizeChartTemplateName;

    @Excel(name = "尺码表尺码标题", type = Type.IMPORT)
    private String sizeChartSizeTitle;

    @Excel(name = "来源平台", type = Type.IMPORT)
    private String sourcePlatform;

    @Excel(name = "商品属性", type = Type.IMPORT)
    private String productAttributes;

    @Excel(name = "七天无理由", type = Type.IMPORT)
    private String sevenDayNoReasonReturn;

    @Excel(name = "运费模板", type = Type.IMPORT)
    private String freightTemplate;

    @Excel(name = "发货模式", type = Type.IMPORT)
    private String shippingMode;

    @Excel(name = "现货发货时间", type = Type.IMPORT)
    private String inStockShipTime;

    @Excel(name = "预售发货时间", type = Type.IMPORT)
    private String presaleShipTime;

    /**
    public MiaoShouSKU() {
    }
     */

    
}