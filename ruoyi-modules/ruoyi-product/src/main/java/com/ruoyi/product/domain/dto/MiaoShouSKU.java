package com.ruoyi.product.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;
import com.ruoyi.product.core.annotation.ExcelBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 妙手商品-SKU 导入 DTO
 */
@Data
@Builder
@NoArgsConstructor // 必须：给 ExcelUtil 反射创建实例用
@AllArgsConstructor // 建议：在使用 @Builder 时必须配合全参构造
@ExcelBusiness(value = "miao_shou_sku", exportName = "导出SKU", templateName = "导出SKU")
public class MiaoShouSKU implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "商品ID", type = Type.ALL)
    private String productId;

    @Excel(name = "商品标题", type = Type.ALL)
    private String title;

    @Excel(name = "SKUID", type = Type.ALL)
    private String skuId;

    @Excel(name = "导购短标题", type = Type.ALL)
    private String guideShortTitle;

    @Excel(name = "SKU商家编码", type = Type.ALL)
    private String sellerSku;

    @Excel(name = "规格1", type = Type.ALL)
    private String spec1;

    @Excel(name = "规格1备注", type = Type.ALL)
    private String spec1Note;

    @Excel(name = "规格2", type = Type.ALL)
    private String spec2;

    @Excel(name = "规格2备注", type = Type.ALL)
    private String spec2Note;

    @Excel(name = "规格3或发货时效", type = Type.ALL)
    private String spec3OrLeadTime;

    @Excel(name = "规格3备注", type = Type.ALL)
    private String spec3Note;

    @Excel(name = "主图1", type = Type.ALL)
    private String mainImage1;

    @Excel(name = "主图2", type = Type.ALL)
    private String mainImage2;

    @Excel(name = "主图3", type = Type.ALL)
    private String mainImage3;

    @Excel(name = "主图4", type = Type.ALL)
    private String mainImage4;

    @Excel(name = "主图5", type = Type.ALL)
    private String mainImage5;

    @Excel(name = "3:4主图1", type = Type.ALL)
    private String mainImage341;

    @Excel(name = "3:4主图2", type = Type.ALL)
    private String mainImage342;

    @Excel(name = "3:4主图3", type = Type.ALL)
    private String mainImage343;

    @Excel(name = "3:4主图4", type = Type.ALL)
    private String mainImage344;

    @Excel(name = "3:4主图5", type = Type.ALL)
    private String mainImage345;

    @Excel(name = "规格图", type = Type.ALL)
    private String specImage;

    @Excel(name = "现货库存", type = Type.ALL)
    private Integer inStockQty;

    @Excel(name = "全款预售库存", type = Type.ALL)
    private Integer fullPrepayQty;

    @Excel(name = "3天内发货库存", type = Type.ALL)
    private Integer ship3dQty;

    @Excel(name = "4天内发货库存", type = Type.ALL)
    private Integer ship4dQty;

    @Excel(name = "5天内发货库存", type = Type.ALL)
    private Integer ship5dQty;

    @Excel(name = "7天内发货库存", type = Type.ALL)
    private Integer ship7dQty;

    @Excel(name = "10天内发货库存", type = Type.ALL)
    private Integer ship10dQty;

    @Excel(name = "15天内发货库存", type = Type.ALL)
    private Integer ship15dQty;

    @Excel(name = "20天内发货库存", type = Type.ALL)
    private Integer ship20dQty;

    @Excel(name = "25天内发货库存", type = Type.ALL)
    private Integer ship25dQty;

    @Excel(name = "30天内发货库存", type = Type.ALL)
    private Integer ship30dQty;

    @Excel(name = "35天内发货库存", type = Type.ALL)
    private Integer ship35dQty;

    @Excel(name = "45天内发货库存", type = Type.ALL)
    private Integer ship45dQty;

    @Excel(name = "售价", type = Type.ALL)
    private BigDecimal price;

    @Excel(name = "最低价", type = Type.ALL)
    private BigDecimal lowestPrice;

    @Excel(name = "最高价", type = Type.ALL)
    private BigDecimal highestPrice;

    @Excel(name = "SKU条形码", type = Type.ALL)
    private String barcode;

    @Excel(name = "货号", type = Type.ALL)
    private String itemNo;

    @Excel(name = "分类", type = Type.ALL)
    private String category;

    @Excel(name = "商品状态", type = Type.ALL)
    private String status;

    @Excel(name = "来源店铺名称", type = Type.ALL)
    private String sourceShopName;

    @Excel(name = "来源ID", type = Type.ALL)
    private String sourceId;

    @Excel(name = "来源标题", type = Type.ALL)
    private String sourceTitle;

    @Excel(name = "来源链接", type = Type.ALL)
    private String sourceUrl;

    @Excel(name = "来源SKU分类", type = Type.ALL)
    private String sourceSkuCategory;

    @Excel(name = "详情图链接", type = Type.ALL)
    private String detailImageUrls;

    @Excel(name = "商品链接", type = Type.ALL)
    private String productUrl;

    @Excel(name = "SKU主图", type = Type.ALL)
    private String skuMainImage;

    @Excel(name = "SKU状态", type = Type.ALL)
    private String skuStatus;

    @Excel(name = "尺码表模板名称", type = Type.ALL)
    private String sizeChartTemplateName;

    @Excel(name = "尺码表尺码标题", type = Type.ALL)
    private String sizeChartSizeTitle;

    @Excel(name = "来源平台", type = Type.ALL)
    private String sourcePlatform;

    @Excel(name = "商品属性", type = Type.ALL)
    private String productAttributes;

    @Excel(name = "七天无理由", type = Type.ALL)
    private String sevenDayNoReasonReturn;

    @Excel(name = "运费模板", type = Type.ALL)
    private String freightTemplate;

    @Excel(name = "发货模式", type = Type.ALL)
    private String shippingMode;

    @Excel(name = "现货发货时间", type = Type.ALL)
    private String inStockShipTime;

    @Excel(name = "预售发货时间", type = Type.ALL)
    private String presaleShipTime;

    /**
     * public MiaoShouSKU() {
     * }
     */

}