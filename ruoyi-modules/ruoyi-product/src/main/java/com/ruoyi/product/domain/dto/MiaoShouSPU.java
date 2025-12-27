package com.ruoyi.product.domain.dto;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;

//import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 妙手商品-SPU 导入 DTO
 */
@Data
//@Builder
public class MiaoShouSPU implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "商品ID", type = Type.IMPORT)
    private String productId;

    @Excel(name = "导购短标题", type = Type.IMPORT)
    private String guideShortTitle;

    @Excel(name = "SKUID", type = Type.IMPORT)
    private String skuId;

    @Excel(name = "商品标题", type = Type.IMPORT)
    private String title;

    @Excel(name = "分类", type = Type.IMPORT)
    private String category;

    @Excel(name = "商品链接", type = Type.IMPORT)
    private String productUrl;

    @Excel(name = "推荐语", type = Type.IMPORT)
    private String recommendation;

    @Excel(name = "库存", type = Type.IMPORT)
    private Integer stock;

    @Excel(name = "售价", type = Type.IMPORT)
    private String priceStr;

    @Excel(name = "最低售价", type = Type.IMPORT)
    private BigDecimal lowestPrice;

    @Excel(name = "最高售价", type = Type.IMPORT)
    private BigDecimal highestPrice;

    @Excel(name = "货号", type = Type.IMPORT)
    private String itemNo;

    @Excel(name = "状态", type = Type.IMPORT)
    private String status;

    @Excel(name = "客服手机", type = Type.IMPORT)
    private String customerPhone;

    @Excel(name = "搬家来源", type = Type.IMPORT)
    private String migrateSource;

    @Excel(name = "来源店铺名称", type = Type.IMPORT)
    private String sourceShopName;

    @Excel(name = "来源ID", type = Type.IMPORT)
    private String sourceId;

    @Excel(name = "来源标题", type = Type.IMPORT)
    private String sourceTitle;

    @Excel(name = "来源链接", type = Type.IMPORT)
    private String sourceUrl;

    @Excel(name = "来源类目", type = Type.IMPORT)
    private String sourceCategory;

    @Excel(name = "品牌", type = Type.IMPORT)
    private String brand;

    @Excel(name = "运费模板", type = Type.IMPORT)
    private String freightTemplate;

    @Excel(name = "商品属性", type = Type.IMPORT)
    private String attributes;

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

    @Excel(name = "详情图链接", type = Type.IMPORT)
    private String detailImageUrls;

    @Excel(name = "最低价SKUID", type = Type.IMPORT)
    private String lowestPriceSkuId;

    @Excel(name = "最高价SKUID", type = Type.IMPORT)
    private String highestPriceSkuId;

    @Excel(name = "尺码表模板名称", type = Type.IMPORT)
    private String sizeChartTemplateName;

    @Excel(name = "尺码表尺码标题", type = Type.IMPORT)
    private String sizeChartSizeTitles;

    @Excel(name = "销量", type = Type.IMPORT)
    private Integer sales;

    @Excel(name = "复制状态", type = Type.IMPORT)
    private String copyStatus;

    @Excel(name = "复制失败原因", type = Type.IMPORT)
    private String copyErrorReason;

    @Excel(name = "审核状态", type = Type.IMPORT)
    private String reviewStatus;

    @Excel(name = "发货模式", type = Type.IMPORT)
    private String shippingMode;

    @Excel(name = "现货发货时间", type = Type.IMPORT)
    private String inStockShipTime;

    @Excel(name = "预售发货时间", type = Type.IMPORT)
    private String presaleShipTime;

    @Excel(name = "七天无理由", type = Type.IMPORT)
    private String sevenDayNoReasonReturn;

    public MiaoShouSPU() {
    }

    
}