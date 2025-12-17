package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.GoodsRevisionItem;

/**
 * SKU 快照Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface GoodsRevisionItemMapper 
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
     * 删除SKU 快照
     * 
     * @param itemId SKU 快照主键
     * @return 结果
     */
    public int deleteGoodsRevisionItemByItemId(String itemId);

    /**
     * 批量删除SKU 快照
     * 
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsRevisionItemByItemIds(String[] itemIds);
}
