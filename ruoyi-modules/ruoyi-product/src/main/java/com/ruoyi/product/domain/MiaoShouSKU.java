package com.ruoyi.product.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;
import com.ruoyi.common.core.web.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 妙手商品-SKU 导入 DTO
 */
public class MiaoShouSKU extends BaseEntity {

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSellerSku() {
        return sellerSku;
    }

    public void setSellerSku(String sellerSku) {
        this.sellerSku = sellerSku;
    }

    public String getSpec1() {
        return spec1;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    public String getSpec1Note() {
        return spec1Note;
    }

    public void setSpec1Note(String spec1Note) {
        this.spec1Note = spec1Note;
    }

    public String getSpec2() {
        return spec2;
    }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }

    public String getSpec2Note() {
        return spec2Note;
    }

    public void setSpec2Note(String spec2Note) {
        this.spec2Note = spec2Note;
    }

    public String getSpec3OrLeadTime() {
        return spec3OrLeadTime;
    }

    public void setSpec3OrLeadTime(String spec3OrLeadTime) {
        this.spec3OrLeadTime = spec3OrLeadTime;
    }

    public String getSpec3Note() {
        return spec3Note;
    }

    public void setSpec3Note(String spec3Note) {
        this.spec3Note = spec3Note;
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

    public String getSpecImage() {
        return specImage;
    }

    public void setSpecImage(String specImage) {
        this.specImage = specImage;
    }

    public Integer getInStockQty() {
        return inStockQty;
    }

    public void setInStockQty(Integer inStockQty) {
        this.inStockQty = inStockQty;
    }

    public Integer getFullPrepayQty() {
        return fullPrepayQty;
    }

    public void setFullPrepayQty(Integer fullPrepayQty) {
        this.fullPrepayQty = fullPrepayQty;
    }

    public Integer getShip3dQty() {
        return ship3dQty;
    }

    public void setShip3dQty(Integer ship3dQty) {
        this.ship3dQty = ship3dQty;
    }

    public Integer getShip4dQty() {
        return ship4dQty;
    }

    public void setShip4dQty(Integer ship4dQty) {
        this.ship4dQty = ship4dQty;
    }

    public Integer getShip5dQty() {
        return ship5dQty;
    }

    public void setShip5dQty(Integer ship5dQty) {
        this.ship5dQty = ship5dQty;
    }

    public Integer getShip7dQty() {
        return ship7dQty;
    }

    public void setShip7dQty(Integer ship7dQty) {
        this.ship7dQty = ship7dQty;
    }

    public Integer getShip10dQty() {
        return ship10dQty;
    }

    public void setShip10dQty(Integer ship10dQty) {
        this.ship10dQty = ship10dQty;
    }

    public Integer getShip15dQty() {
        return ship15dQty;
    }

    public void setShip15dQty(Integer ship15dQty) {
        this.ship15dQty = ship15dQty;
    }

    public Integer getShip20dQty() {
        return ship20dQty;
    }

    public void setShip20dQty(Integer ship20dQty) {
        this.ship20dQty = ship20dQty;
    }

    public Integer getShip25dQty() {
        return ship25dQty;
    }

    public void setShip25dQty(Integer ship25dQty) {
        this.ship25dQty = ship25dQty;
    }

    public Integer getShip30dQty() {
        return ship30dQty;
    }

    public void setShip30dQty(Integer ship30dQty) {
        this.ship30dQty = ship30dQty;
    }

    public Integer getShip35dQty() {
        return ship35dQty;
    }

    public void setShip35dQty(Integer ship35dQty) {
        this.ship35dQty = ship35dQty;
    }

    public Integer getShip45dQty() {
        return ship45dQty;
    }

    public void setShip45dQty(Integer ship45dQty) {
        this.ship45dQty = ship45dQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceSkuCategory() {
        return sourceSkuCategory;
    }

    public void setSourceSkuCategory(String sourceSkuCategory) {
        this.sourceSkuCategory = sourceSkuCategory;
    }

    public String getSkuMainImage() {
        return skuMainImage;
    }

    public void setSkuMainImage(String skuMainImage) {
        this.skuMainImage = skuMainImage;
    }

    public String getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(String skuStatus) {
        this.skuStatus = skuStatus;
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

    public String getGuideShortTitle() {
        return guideShortTitle;
    }

    public void setGuideShortTitle(String guideShortTitle) {
        this.guideShortTitle = guideShortTitle;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDetailImageUrls() {
        return detailImageUrls;
    }

    public void setDetailImageUrls(String detailImageUrls) {
        this.detailImageUrls = detailImageUrls;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getSizeChartTemplateName() {
        return sizeChartTemplateName;
    }

    public void setSizeChartTemplateName(String sizeChartTemplateName) {
        this.sizeChartTemplateName = sizeChartTemplateName;
    }

    public String getSizeChartSizeTitle() {
        return sizeChartSizeTitle;
    }

    public void setSizeChartSizeTitle(String sizeChartSizeTitle) {
        this.sizeChartSizeTitle = sizeChartSizeTitle;
    }

    public String getSourcePlatform() {
        return sourcePlatform;
    }

    public void setSourcePlatform(String sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }

    public String getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(String productAttributes) {
        this.productAttributes = productAttributes;
    }

    public String getSevenDayNoReasonReturn() {
        return sevenDayNoReasonReturn;
    }

    public void setSevenDayNoReasonReturn(String sevenDayNoReasonReturn) {
        this.sevenDayNoReasonReturn = sevenDayNoReasonReturn;
    }

    public String getFreightTemplate() {
        return freightTemplate;
    }

    public void setFreightTemplate(String freightTemplate) {
        this.freightTemplate = freightTemplate;
    }

    
    
}