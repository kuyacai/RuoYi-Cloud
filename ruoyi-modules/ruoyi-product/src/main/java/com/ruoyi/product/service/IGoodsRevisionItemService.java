package com.ruoyi.product.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.GoodsRevisionItem;

/**
 * SKU 快照Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsRevisionItemService extends IBaseService<GoodsRevisionItem> {
    /**
     * 根据版本ID集合批量查询SKU快照
     * * @param revisionIds 版本ID列表
     * 
     * @return SKU快照列表
     */
    List<GoodsRevisionItem> listByRevisionIds(List<String> revisionIds);

    /**
     * 根据版本ID查询SKU快照列表
     * * @param revisionId 版本ID
     * 
     * @return 该版本下的所有SKU快照
     */
    List<GoodsRevisionItem> listByRevisionId(String revisionId);

    /**
     * 
     * 查询需要修改价格的spu列表
     * 
     * @param goodsId
     * @return
     */
    List<GoodsRevisionItem> selectSkusNeedPriceUpdate(@Param("goodsId") String goodsId);

    boolean checkUnique(GoodsRevisionItem goodsRevisionItem);

    boolean isBeenImported(String revisionId, String shopId, String shopProductId, String shopSkuId);
}
