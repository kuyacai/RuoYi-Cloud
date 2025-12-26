package com.ruoyi.product.app.display;

import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.GoodsRevisionImage;

public class SkuBundle {

    private GoodsRevisionItem sku;
    private GoodsRevisionImage specImage;   // 可能 null

    public SkuBundle() {}

    public SkuBundle(GoodsRevisionItem sku, GoodsRevisionImage specImage) {
        this.sku = sku;
        this.specImage = specImage;
    }

    public GoodsRevisionItem getSku() { return sku; }
    public void setSku(GoodsRevisionItem sku) { this.sku = sku; }

    public GoodsRevisionImage getSpecImage() { return specImage; }
    public void setSpecImage(GoodsRevisionImage specImage) { this.specImage = specImage; }
}