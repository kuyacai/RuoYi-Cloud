package com.ruoyi.product.app.display;

import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.domain.GoodsRevisionSpu;
import com.ruoyi.product.domain.GoodsRevisionImage;
import java.util.List;

/**
 * 商品版本“页面直接可用”视图
 */
public class GoodsVersionDetail {

    /* ---------- 商品基础信息[与版本无关] ---------- */
    private Goods goods;
    /* ---------- 版本元数据 ---------- */
    private GoodsRevision revision;
    private GoodsRevisionSpu spu;

    /* ---------- SPU 维度图片 ---------- */
    private List<GoodsRevisionImage> mainImages;
    private List<GoodsRevisionImage> main34Images;
    private List<GoodsRevisionImage> whiteImages;
    private List<GoodsRevisionImage> guideImages;
    private List<GoodsRevisionImage> descImages;

    /* ---------- SKU 维度 ---------- */
    private List<SkuBundle> skuBundles;

    /* ---------- 手写构造器（Builder 模式） ---------- */
    private GoodsVersionDetail(Builder b) {
        this.goods = b.goods;
        this.revision = b.revision;
        this.spu = b.spu;
        this.mainImages = b.mainImages;
        this.main34Images = b.main34Images;
        this.whiteImages = b.whiteImages;
        this.guideImages = b.guideImages;
        this.descImages = b.descImages;
        this.skuBundles = b.skuBundles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Goods goods;
        private GoodsRevision revision;
        private GoodsRevisionSpu spu;
        private List<GoodsRevisionImage> mainImages;
        private List<GoodsRevisionImage> main34Images;
        private List<GoodsRevisionImage> whiteImages;
        private List<GoodsRevisionImage> guideImages;
        private List<GoodsRevisionImage> descImages;
        private List<SkuBundle> skuBundles;

        public Builder goods(Goods v) { this.goods = v; return this; }
        public Builder revision(GoodsRevision v) { this.revision = v; return this; }
        public Builder spu(GoodsRevisionSpu v) { this.spu = v; return this; }
        public Builder mainImages(List<GoodsRevisionImage> v) { this.mainImages = v; return this; }
        public Builder main34Images(List<GoodsRevisionImage> v) { this.main34Images = v; return this; }
        public Builder whiteImages(List<GoodsRevisionImage> v) { this.whiteImages = v; return this; }
        public Builder guideImages(List<GoodsRevisionImage> v) { this.guideImages = v; return this; }
        public Builder descImages(List<GoodsRevisionImage> v) { this.descImages = v; return this; }
        public Builder skuBundles(List<SkuBundle> v) { this.skuBundles = v; return this; }

        public GoodsVersionDetail build() { return new GoodsVersionDetail(this); }
    }

    /* ---------- Getter/Setter ---------- */
    public Goods getGoods() { return goods; }
    public void setGoods(Goods goods) { this.goods = goods; }
    public GoodsRevision getRevision() { return revision; }
    public void setRevision(GoodsRevision revision) { this.revision = revision; }

    public GoodsRevisionSpu getSpu() { return spu; }
    public void setSpu(GoodsRevisionSpu spu) { this.spu = spu; }

    public List<GoodsRevisionImage> getMainImages() { return mainImages; }
    public void setMainImages(List<GoodsRevisionImage> mainImages) { this.mainImages = mainImages; }

    public List<GoodsRevisionImage> getMain34Images() { return main34Images; }
    public void setMain34Images(List<GoodsRevisionImage> main34Images) { this.main34Images = main34Images; }

    public List<GoodsRevisionImage> getWhiteImages() { return whiteImages; }
    public void setWhiteImages(List<GoodsRevisionImage> whiteImages) { this.whiteImages = whiteImages; }

    public List<GoodsRevisionImage> getGuideImages() { return guideImages; }
    public void setGuideImages(List<GoodsRevisionImage> guideImages) { this.guideImages = guideImages; }

    public List<GoodsRevisionImage> getDescImages() { return descImages; }
    public void setDescImages(List<GoodsRevisionImage> descImages) { this.descImages = descImages; }

    public List<SkuBundle> getSkuBundles() { return skuBundles; }
    public void setSkuBundles(List<SkuBundle> skuBundles) { this.skuBundles = skuBundles; }
}