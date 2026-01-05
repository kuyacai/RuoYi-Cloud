package com.ruoyi.product.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.product.core.annotation.ExcelBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 妙手商品-SPU 导入 DTO
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor // 必须：给 ExcelUtil 反射创建实例用
@AllArgsConstructor // 建议：在使用 @Builder 时必须配合全参构造
@ExcelBusiness(value = "miao_shou_spu", exportName = "导出SPU", templateName = "导出SPU")
public class MiaoShouSPU implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "商品ID", type = Type.ALL)
    private String productId;

    @Excel(name = "导购短标题", type = Type.ALL)
    private String guideShortTitle;

    /**
     * 这里的skuId，是该商品的所有skuid，以逗号分割。
     */
    @Excel(name = "SKUID", type = Type.ALL)
    private String skuIds;

    @Excel(name = "商品标题", type = Type.ALL)
    private String title;

    @Excel(name = "分类", type = Type.ALL)
    private String category;

    @Excel(name = "商品链接", type = Type.ALL)
    private String productUrl;

    @Excel(name = "推荐语", type = Type.ALL)
    private String recommendation;

    /**
     * 这里的库存是该商品所有sku的库存之和。
     */
    @Excel(name = "库存", type = Type.ALL)
    private Integer stock;

    /**
     * 该商品的售价，如果该商品的所有sku的售价一致，这里就是一个价格。
     * 如果该商品的sku的售价不一致，则是'最低价-最高价'形式。
     * 所以这里是字符串
     */
    @Excel(name = "售价", type = Type.ALL)
    private String priceStr;

    /**
     * 该商品售价最低的sku的价格。
     */
    @Excel(name = "最低售价", type = Type.ALL)
    private BigDecimal lowestPrice;

    /**
     * 该商品售价最高的sku的价格
     */
    @Excel(name = "最高售价", type = Type.ALL)
    private BigDecimal highestPrice;

    @Excel(name = "货号", type = Type.ALL)
    private String itemNo;

    @Excel(name = "状态", type = Type.ALL)
    private String status;

    @Excel(name = "客服手机", type = Type.ALL)
    private String customerPhone;

    @Excel(name = "搬家来源", type = Type.ALL)
    private String migrateSource;

    @Excel(name = "来源店铺名称", type = Type.ALL)
    private String sourceShopName;

    @Excel(name = "来源ID", type = Type.ALL)
    private String sourceId;

    @Excel(name = "来源标题", type = Type.ALL)
    private String sourceTitle;

    @Excel(name = "来源链接", type = Type.ALL)
    private String sourceUrl;

    @Excel(name = "来源类目", type = Type.ALL)
    private String sourceCategory;

    @Excel(name = "品牌", type = Type.ALL)
    private String brand;

    @Excel(name = "运费模板", type = Type.ALL)
    private String freightTemplate;

    @Excel(name = "商品属性", type = Type.ALL)
    private String attributes;

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

    /**
     * 注意，在excel文件中并不是以','分割额，而是回车换行符号分割的。
     * 我们在ExcelUtil的子类EnhancedExcelUtil来解决这个问题。
     * 注意，这里不要定义成List
     */
    @Excel(name = "详情图链接", type = Type.ALL)
    private String detailImageUrls;

    @Excel(name = "最低价SKUID", type = Type.ALL)
    private String lowestPriceSkuId;

    @Excel(name = "最高价SKUID", type = Type.ALL)
    private String highestPriceSkuId;

    @Excel(name = "尺码表模板名称", type = Type.ALL)
    private String sizeChartTemplateName;

    @Excel(name = "尺码表尺码标题", type = Type.ALL)
    private String sizeChartSizeTitles;

    /**
     * 该商品所有sku的销量之和
     */
    @Excel(name = "销量", type = Type.ALL)
    private Integer sales;

    /**
     * 指从来源平台复制到目标平台，例如从1688复制到抖店
     */
    @Excel(name = "复制状态", type = Type.ALL)
    private String copyStatus;

    @Excel(name = "复制失败原因", type = Type.ALL)
    private String copyErrorReason;

    /**
     * 这里指目标平台的审核状态，例如从1688复制到抖店，抖店的审核状态，注意，如果是复制到草稿箱，那么
     * 抖店是不会审核的，只有复制为直接上架，抖店才审核。
     */
    @Excel(name = "审核状态", type = Type.ALL)
    private String reviewStatus;

    @Excel(name = "发货模式", type = Type.ALL)
    private String shippingMode;

    @Excel(name = "现货发货时间", type = Type.ALL)
    private String inStockShipTime;

    @Excel(name = "预售发货时间", type = Type.ALL)
    private String presaleShipTime;

    @Excel(name = "七天无理由", type = Type.ALL)
    private String sevenDayNoReasonReturn;

    /**
     * 给业务逻辑使用的“快捷 Getter”
     */
    public List<String> getDetailImageUrlList() {
        if (StringUtils.isEmpty(this.detailImageUrls)) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.detailImageUrls.split(","));
    }

    /**
     * 给业务逻辑使用的“快捷 Setter”
     */
    public void setDetailImageUrlList(List<String> list) {
        if (list != null && !list.isEmpty()) {
            this.detailImageUrls = StringUtils.join(list, ",");
        } else {
            this.detailImageUrls = "";
        }
    }

    /**
     * 给业务逻辑使用的“快捷 Getter”
     */
    public List<String> getSkuIdList() {
        if (StringUtils.isEmpty(this.skuIds)) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.skuIds.split(","));
    }

    /**
     * 给业务逻辑使用的“快捷 Setter”
     */
    public void setSkuIdList(List<String> list) {
        if (list != null && !list.isEmpty()) {
            this.skuIds = StringUtils.join(list, ",");
        } else {
            this.skuIds = "";
        }
    }
}