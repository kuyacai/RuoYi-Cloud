package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.GoodsRevisionItem;

/**
 * SKU 快照Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsRevisionItemService 
{
    /**
     * 查询SKU 快照
     * 
     * @param itemId SKU 快照主键
     * @return SKU 快照
     */
    public GoodsRevisionItem selectGoodsRevisionItemByItemId(String itemId);

    /**
     * 查询SKU 快照列表
     * 
     * @param goodsRevisionItem SKU 快照
     * @return SKU 快照集合
     */
    public List<GoodsRevisionItem> selectGoodsRevisionItemList(GoodsRevisionItem goodsRevisionItem);

    /**
     * 新增SKU 快照
     * 
     * @param goodsRevisionItem SKU 快照
     * @return 结果
     */
    public int insertGoodsRevisionItem(GoodsRevisionItem goodsRevisionItem);

    /**
     * 修改SKU 快照
     * 
     * @param goodsRevisionItem SKU 快照
     * @return 结果
     */
    public int updateGoodsRevisionItem(GoodsRevisionItem goodsRevisionItem);

    /**
     * 批量删除SKU 快照
     * 
     * @param itemIds 需要删除的SKU 快照主键集合
     * @return 结果
     */
    public int deleteGoodsRevisionItemByItemIds(String[] itemIds);

    /**
     * 删除SKU 快照信息
     * 
     * @param itemId SKU 快照主键
     * @return 结果
     */
    public int deleteGoodsRevisionItemByItemId(String itemId);

    /**
     * 
     * @param goodsRevisionItem
     * @return
     */
    public boolean checkUnique(GoodsRevisionItem goodsRevisionItem);

    /**
     * 检查该sku记录是否被导入过。
     * 将根据String revisionId, String shopId, String shopProductId, String shopSkuId查询。
     * @return true表示已经被导入过，false表示没有被导入过
     */
    public boolean isBeenImported(String revisionId, String shopId, String shopProductId, String shopSkuId);
}
