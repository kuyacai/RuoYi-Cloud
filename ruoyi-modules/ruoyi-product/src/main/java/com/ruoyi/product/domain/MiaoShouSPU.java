package com.ruoyi.product.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;
import com.ruoyi.common.core.web.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 妙手商品-SPU 导入 DTO
 */
public class MiaoShouSPU extends BaseEntity {

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getGuideShortTitle() {
        return guideShortTitle;
    }

    public void setGuideShortTitle(String guideShortTitle) {
        this.guideShortTitle = guideShortTitle;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getMigrateSource() {
        return migrateSource;
    }

    public void setMigrateSource(String migrateSource) {
        this.migrateSource = migrateSource;
    }

    public String getSourceShopName() {
        return sourceShopName;
    }

    public void setSourceShopName(String sourceShopName) {
        this.sourceShopName = sourceShopName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceCategory() {
        return sourceCategory;
    }

    public void setSourceCategory(String sourceCategory) {
        this.sourceCategory = sourceCategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFreightTemplate() {
        return freightTemplate;
    }

    public void setFreightTemplate(String freightTemplate) {
        this.freightTemplate = freightTemplate;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getMainImage1() {
        return mainImage1;
    }

    public void setMainImage1(String mainImage1) {
        this.mainImage1 = mainImage1;
    }

    public String getMainImage2() {
        return mainImage2;
    }

    public void setMainImage2(String mainImage2) {
        this.mainImage2 = mainImage2;
    }

    public String getMainImage3() {
        return mainImage3;
    }

    public void setMainImage3(String mainImage3) {
        this.mainImage3 = mainImage3;
    }

    public String getMainImage4() {
        return mainImage4;
    }

    public void setMainImage4(String mainImage4) {
        this.mainImage4 = mainImage4;
    }

    public String getMainImage5() {
        return mainImage5;
    }

    public void setMainImage5(String mainImage5) {
        this.mainImage5 = mainImage5;
    }

    public String getMainImage341() {
        return mainImage341;
    }

    public void setMainImage341(String mainImage341) {
        this.mainImage341 = mainImage341;
    }

    public String getMainImage342() {
        return mainImage342;
    }

    public void setMainImage342(String mainImage342) {
        this.mainImage342 = mainImage342;
    }

    public String getMainImage343() {
        return mainImage343;
    }

    public void setMainImage343(String mainImage343) {
        this.mainImage343 = mainImage343;
    }

    public String getMainImage344() {
        return mainImage344;
    }

    public void setMainImage344(String mainImage344) {
        this.mainImage344 = mainImage344;
    }
    public String getMainImage345() {
        return mainImage345;
    }
    public void setMainImage345(String mainImage345) {
        this.mainImage345 = mainImage345;
    }

    public String getDetailImageUrls() {
        return detailImageUrls;
    }

    public void setDetailImageUrls(String detailImageUrls) {
        this.detailImageUrls = detailImageUrls;
    }

    public String getLowestPriceSkuId() {
        return lowestPriceSkuId;
    }

    public void setLowestPriceSkuId(String lowestPriceSkuId) {
        this.lowestPriceSkuId = lowestPriceSkuId;
    }

    public String getHighestPriceSkuId() {
        return highestPriceSkuId;
    }

    public void setHighestPriceSkuId(String highestPriceSkuId) {
        this.highestPriceSkuId = highestPriceSkuId;
    }

    public String getSizeChartTemplateName() {
        return sizeChartTemplateName;
    }

    public void setSizeChartTemplateName(String sizeChartTemplateName) {
        this.sizeChartTemplateName = sizeChartTemplateName;
    }

    public String getSizeChartSizeTitles() {
        return sizeChartSizeTitles;
    }

    public void setSizeChartSizeTitles(String sizeChartSizeTitles) {
        this.sizeChartSizeTitles = sizeChartSizeTitles;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getCopyStatus() {
        return copyStatus;
    }

    public void setCopyStatus(String copyStatus) {
        this.copyStatus = copyStatus;
    }

    public String getCopyErrorReason() {
        return copyErrorReason;
    }

    public void setCopyErrorReason(String copyErrorReason) {
        this.copyErrorReason = copyErrorReason;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(String shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getInStockShipTime() {
        return inStockShipTime;
    }

    public void setInStockShipTime(String inStockShipTime) {
        this.inStockShipTime = inStockShipTime;
    }

    public String getPresaleShipTime() {
        return presaleShipTime;
    }

    public void setPresaleShipTime(String presaleShipTime) {
        this.presaleShipTime = presaleShipTime;
    }
    public String getSevenDayNoReasonReturn() {
        return sevenDayNoReasonReturn;
    }
    public void setSevenDayNoReasonReturn(String sevenDayNoReasonReturn) {
        this.sevenDayNoReasonReturn = sevenDayNoReasonReturn;
    }

    // Getters and Setters
    
}