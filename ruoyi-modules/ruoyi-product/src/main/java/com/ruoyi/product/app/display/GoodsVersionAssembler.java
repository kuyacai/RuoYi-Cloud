package com.ruoyi.product.app.display;

import com.ruoyi.product.constant.ImageType;
import com.ruoyi.product.domain.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GoodsVersionAssembler {

    /**
     * 将任意版本的所有快照装配成“页面直接可用”对象
     * 全部图片类型通过 ImageType 枚举获取，零硬编码
     */
    public GoodsVersionDetail assemble(
            Goods goods,
            GoodsRevision revision,
            GoodsRevisionSpu spu,
            List<GoodsRevisionItem> items,
            List<GoodsRevisionImage> images) {

        /* 1. 按 ImageType 分组（code -> List<GoodsRevisionImage>） */
        Map<String, List<GoodsRevisionImage>> byCode = images.stream()
                .filter(img -> img.getImageType() != null)
                .collect(Collectors.groupingBy(GoodsRevisionImage::getImageType));

        /* 2. SKU 维度：规格图最多一张，按 shopSkuId 做 Map 方便快速查找 */
        Map<String, GoodsRevisionImage> specMap = byCode.getOrDefault(ImageType.SPEC.getCode(), Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(GoodsRevisionImage::getGoodsSkuId,
                        v -> v,
                        (a, b) -> a)); // 重复取第一条

        /* 3. 组装 SKU 包 */
        List<SkuBundle> skuBundles = new ArrayList<>();
        for (GoodsRevisionItem sku : items) {
            skuBundles.add(new SkuBundle(sku, specMap.get(sku.getShopSkuId())));
        }

        /* 4. 返回 */
        return GoodsVersionDetail.builder()
                .goods(goods)
                .revision(revision)
                .spu(spu)

                .mainImages(byCode.getOrDefault(ImageType.MAIN.getCode(), Collections.emptyList()))
                .main34Images(byCode.getOrDefault(ImageType.MAIN34.getCode(), Collections.emptyList()))
                .whiteImages(byCode.getOrDefault(ImageType.WHITE.getCode(), Collections.emptyList()))
                .guideImages(byCode.getOrDefault(ImageType.GUIDE.getCode(), Collections.emptyList()))
                .descImages(byCode.getOrDefault(ImageType.DETAIL.getCode(), Collections.emptyList()))

                .skuBundles(skuBundles)
                .build();
    }
}