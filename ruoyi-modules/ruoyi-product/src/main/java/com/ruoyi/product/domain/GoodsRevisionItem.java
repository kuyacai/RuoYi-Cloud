package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * SKU 快照对象 goods_revision_item
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public class GoodsRevisionItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String itemId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String revisionId;

    /** 仅用于导入sku时去重使用 */
    @Excel(name = "店铺ID",readConverterExp = "$column.readConverterExp()")
    private String shopId;
    /** 仅用于导入sku时去重使用 */
    @Excel(name = "店铺商品ID",readConverterExp = "$column.readConverterExp()")
    private String shopProductId;
    /** 仅用于导入sku时去重使用 */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shopSkuId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String skuCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sellerSku;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String spec1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String spec1Note;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String spec2;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String spec2Note;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String spec3OrLeadTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String spec3Note;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long inStockQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long fullPrepayQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship3dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship4dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship5dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship7dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship10dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship15dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship20dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship25dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship30dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship35dQty;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long ship45dQty;

    /** 分 */
    @Excel(name = "分")
    private Long orignialPrice;

    /** 分 */
    @Excel(name = "分")
    private Long marketPrice;

    /** 分 */
    @Excel(name = "分")
    private Long lowestPrice;

    /** 分 */
    @Excel(name = "分")
    private Long highestPrice;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String skuStatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String barcode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtCreate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date gmtModified;

    public void setItemId(String itemId) 
    {
        this.itemId = itemId;
    }

    public String getItemId() 
    {
        return itemId;
    }

    public void setRevisionId(String revisionId) 
    {
        this.revisionId = revisionId;
    }

    public String getRevisionId() 
    {
        return revisionId;
    }

    

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopProductId() {
        return shopProductId;
    }

    public void setShopProductId(String shopProductId) {
        this.shopProductId = shopProductId;
    }

    public String getShopSkuId() {
        return shopSkuId;
    }

    public void setShopSkuId(String shopSkuId) {
        this.shopSkuId = shopSkuId;
    }

    public void setSkuCode(String skuCode) 
    {
        this.skuCode = skuCode;
    }

    public String getSkuCode() 
    {
        return skuCode;
    }

    public void setSellerSku(String sellerSku) 
    {
        this.sellerSku = sellerSku;
    }

    public String getSellerSku() 
    {
        return sellerSku;
    }

    public void setSpec1(String spec1) 
    {
        this.spec1 = spec1;
    }

    public String getSpec1() 
    {
        return spec1;
    }

    public void setSpec1Note(String spec1Note) 
    {
        this.spec1Note = spec1Note;
    }

    public String getSpec1Note() 
    {
        return spec1Note;
    }

    public void setSpec2(String spec2) 
    {
        this.spec2 = spec2;
    }

    public String getSpec2() 
    {
        return spec2;
    }

    public void setSpec2Note(String spec2Note) 
    {
        this.spec2Note = spec2Note;
    }

    public String getSpec2Note() 
    {
        return spec2Note;
    }

    public void setSpec3OrLeadTime(String spec3OrLeadTime) 
    {
        this.spec3OrLeadTime = spec3OrLeadTime;
    }

    public String getSpec3OrLeadTime() 
    {
        return spec3OrLeadTime;
    }

    public void setSpec3Note(String spec3Note) 
    {
        this.spec3Note = spec3Note;
    }

    public String getSpec3Note() 
    {
        return spec3Note;
    }

    public void setInStockQty(Long inStockQty) 
    {
        this.inStockQty = inStockQty;
    }

    public Long getInStockQty() 
    {
        return inStockQty;
    }

    public void setFullPrepayQty(Long fullPrepayQty) 
    {
        this.fullPrepayQty = fullPrepayQty;
    }

    public Long getFullPrepayQty() 
    {
        return fullPrepayQty;
    }

    public void setShip3dQty(Long ship3dQty) 
    {
        this.ship3dQty = ship3dQty;
    }

    public Long getShip3dQty() 
    {
        return ship3dQty;
    }

    public void setShip4dQty(Long ship4dQty) 
    {
        this.ship4dQty = ship4dQty;
    }

    public Long getShip4dQty() 
    {
        return ship4dQty;
    }

    public void setShip5dQty(Long ship5dQty) 
    {
        this.ship5dQty = ship5dQty;
    }

    public Long getShip5dQty() 
    {
        return ship5dQty;
    }

    public void setShip7dQty(Long ship7dQty) 
    {
        this.ship7dQty = ship7dQty;
    }

    public Long getShip7dQty() 
    {
        return ship7dQty;
    }

    public void setShip10dQty(Long ship10dQty) 
    {
        this.ship10dQty = ship10dQty;
    }

    public Long getShip10dQty() 
    {
        return ship10dQty;
    }

    public void setShip15dQty(Long ship15dQty) 
    {
        this.ship15dQty = ship15dQty;
    }

    public Long getShip15dQty() 
    {
        return ship15dQty;
    }

    public void setShip20dQty(Long ship20dQty) 
    {
        this.ship20dQty = ship20dQty;
    }

    public Long getShip20dQty() 
    {
        return ship20dQty;
    }

    public void setShip25dQty(Long ship25dQty) 
    {
        this.ship25dQty = ship25dQty;
    }

    public Long getShip25dQty() 
    {
        return ship25dQty;
    }

    public void setShip30dQty(Long ship30dQty) 
    {
        this.ship30dQty = ship30dQty;
    }

    public Long getShip30dQty() 
    {
        return ship30dQty;
    }

    public void setShip35dQty(Long ship35dQty) 
    {
        this.ship35dQty = ship35dQty;
    }

    public Long getShip35dQty() 
    {
        return ship35dQty;
    }

    public void setShip45dQty(Long ship45dQty) 
    {
        this.ship45dQty = ship45dQty;
    }

    public Long getShip45dQty() 
    {
        return ship45dQty;
    }

    public void setOrignialPrice(Long orignialPrice) 
    {
        this.orignialPrice = orignialPrice;
    }

    public Long getOrignialPrice() 
    {
        return orignialPrice;
    }

    public void setMarketPrice(Long marketPrice) 
    {
        this.marketPrice = marketPrice;
    }

    public Long getMarketPrice() 
    {
        return marketPrice;
    }

    public void setLowestPrice(Long lowestPrice) 
    {
        this.lowestPrice = lowestPrice;
    }

    public Long getLowestPrice() 
    {
        return lowestPrice;
    }

    public void setHighestPrice(Long highestPrice) 
    {
        this.highestPrice = highestPrice;
    }

    public Long getHighestPrice() 
    {
        return highestPrice;
    }

    public void setSkuStatus(String skuStatus) 
    {
        this.skuStatus = skuStatus;
    }

    public String getSkuStatus() 
    {
        return skuStatus;
    }

    public void setBarcode(String barcode) 
    {
        this.barcode = barcode;
    }

    public String getBarcode() 
    {
        return barcode;
    }

    public void setGmtCreate(Date gmtCreate) 
    {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() 
    {
        return gmtCreate;
    }

    public void setGmtModified(Date gmtModified) 
    {
        this.gmtModified = gmtModified;
    }
    public Date getGmtModified() 
    {
        return gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemId", getItemId())
            .append("revisionId", getRevisionId())
            .append("shopId", getShopId())
            .append("shopProductId", getShopProductId())
            .append("shopSkuId", getShopSkuId())
            .append("skuCode", getSkuCode())
            .append("sellerSku", getSellerSku())
            .append("spec1", getSpec1())
            .append("spec1Note", getSpec1Note())
            .append("spec2", getSpec2())
            .append("spec2Note", getSpec2Note())
            .append("spec3OrLeadTime", getSpec3OrLeadTime())
            .append("spec3Note", getSpec3Note())
            .append("inStockQty", getInStockQty())
            .append("fullPrepayQty", getFullPrepayQty())
            .append("ship3dQty", getShip3dQty())
            .append("ship4dQty", getShip4dQty())
            .append("ship5dQty", getShip5dQty())
            .append("ship7dQty", getShip7dQty())
            .append("ship10dQty", getShip10dQty())
            .append("ship15dQty", getShip15dQty())
            .append("ship20dQty", getShip20dQty())
            .append("ship25dQty", getShip25dQty())
            .append("ship30dQty", getShip30dQty())
            .append("ship35dQty", getShip35dQty())
            .append("ship45dQty", getShip45dQty())
            .append("orignialPrice", getOrignialPrice())
            .append("marketPrice", getMarketPrice())
            .append("lowestPrice", getLowestPrice())
            .append("highestPrice", getHighestPrice())
            .append("skuStatus", getSkuStatus())
            .append("barcode", getBarcode())
            .append("gmtCreate", getGmtCreate())
            .append("gmtModified", getGmtModified())
            .toString();
    }
}
