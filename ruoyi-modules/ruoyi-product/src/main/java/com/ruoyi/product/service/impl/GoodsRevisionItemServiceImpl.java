package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.GoodsRevisionItemMapper;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.service.IGoodsRevisionItemService;
import org.springframework.util.CollectionUtils;
/**
 * SKU 快照Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsRevisionItemServiceImpl implements IGoodsRevisionItemService 
{
    @Autowired
    private GoodsRevisionItemMapper goodsRevisionItemMapper;

    /**
     * 查询SKU 快照
     * 
     * @param itemId SKU 快照主键
     * @return SKU 快照
     */
    @Override
    public GoodsRevisionItem selectGoodsRevisionItemByItemId(String itemId)
    {
        return goodsRevisionItemMapper.selectGoodsRevisionItemByItemId(itemId);
    }

    /**
     * 查询SKU 快照列表
     * 
     * @param goodsRevisionItem SKU 快照
     * @return SKU 快照
     */
    @Override
    public List<GoodsRevisionItem> selectGoodsRevisionItemList(GoodsRevisionItem goodsRevisionItem)
    {
        return goodsRevisionItemMapper.selectGoodsRevisionItemList(goodsRevisionItem);
    }

    /**
     * 新增SKU 快照
     * 
     * @param goodsRevisionItem SKU 快照
     * @return 结果
     */
    @Override
    public int insertGoodsRevisionItem(GoodsRevisionItem goodsRevisionItem)
    {
        return goodsRevisionItemMapper.insertGoodsRevisionItem(goodsRevisionItem);
    }

    /**
     * 修改SKU 快照
     * 
     * @param goodsRevisionItem SKU 快照
     * @return 结果
     */
    @Override
    public int updateGoodsRevisionItem(GoodsRevisionItem goodsRevisionItem)
    {
        return goodsRevisionItemMapper.updateGoodsRevisionItem(goodsRevisionItem);
    }

    /**
     * 批量删除SKU 快照
     * 
     * @param itemIds 需要删除的SKU 快照主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRevisionItemByItemIds(String[] itemIds)
    {
        return goodsRevisionItemMapper.deleteGoodsRevisionItemByItemIds(itemIds);
    }

    /**
     * 删除SKU 快照信息
     * 
     * @param itemId SKU 快照主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRevisionItemByItemId(String itemId)
    {
        return goodsRevisionItemMapper.deleteGoodsRevisionItemByItemId(itemId);
    }

    @Override
    public boolean checkUnique(GoodsRevisionItem goodsRevisionItem) {
        List<GoodsRevisionItem> list = goodsRevisionItemMapper.selectGoodsRevisionItemList(goodsRevisionItem);
        return CollectionUtils.isEmpty(list);
    }

    @Override
    public boolean isBeenImported(String revisionId, String shopId, String shopProductId, String shopSkuId) {
        GoodsRevisionItem goodsRevisionItem = new GoodsRevisionItem();
        goodsRevisionItem.setRevisionId(revisionId);
        goodsRevisionItem.setShopId(shopId);
        goodsRevisionItem.setShopProductId(shopProductId);
        goodsRevisionItem.setShopSkuId(shopSkuId);
        //List<GoodsRevisionItem> list = goodsRevisionItemMapper.selectGoodsRevisionItemList(goodsRevisionItem);
        return !checkUnique(goodsRevisionItem);
    }


}
