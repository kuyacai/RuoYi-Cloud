package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ShopListingSkuMapper;
import com.ruoyi.product.domain.ShopListingSku;
import com.ruoyi.product.service.IShopListingSkuService;

/**
 * 店铺商品SKU同步状态Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ShopListingSkuServiceImpl implements IShopListingSkuService 
{
    @Autowired
    private ShopListingSkuMapper shopListingSkuMapper;

    /**
     * 查询店铺商品SKU同步状态
     * 
     * @param listingSkuId 店铺商品SKU同步状态主键
     * @return 店铺商品SKU同步状态
     */
    @Override
    public ShopListingSku selectShopListingSkuByListingSkuId(String listingSkuId)
    {
        return shopListingSkuMapper.selectShopListingSkuByListingSkuId(listingSkuId);
    }

    /**
     * 查询店铺商品SKU同步状态列表
     * 
     * @param shopListingSku 店铺商品SKU同步状态
     * @return 店铺商品SKU同步状态
     */
    @Override
    public List<ShopListingSku> selectShopListingSkuList(ShopListingSku shopListingSku)
    {
        return shopListingSkuMapper.selectShopListingSkuList(shopListingSku);
    }

    /**
     * 新增店铺商品SKU同步状态
     * 
     * @param shopListingSku 店铺商品SKU同步状态
     * @return 结果
     */
    @Override
    public int insertShopListingSku(ShopListingSku shopListingSku)
    {
        return shopListingSkuMapper.insertShopListingSku(shopListingSku);
    }

    /**
     * 修改店铺商品SKU同步状态
     * 
     * @param shopListingSku 店铺商品SKU同步状态
     * @return 结果
     */
    @Override
    public int updateShopListingSku(ShopListingSku shopListingSku)
    {
        return shopListingSkuMapper.updateShopListingSku(shopListingSku);
    }

    /**
     * 批量删除店铺商品SKU同步状态
     * 
     * @param listingSkuIds 需要删除的店铺商品SKU同步状态主键
     * @return 结果
     */
    @Override
    public int deleteShopListingSkuByListingSkuIds(String[] listingSkuIds)
    {
        return shopListingSkuMapper.deleteShopListingSkuByListingSkuIds(listingSkuIds);
    }

    /**
     * 删除店铺商品SKU同步状态信息
     * 
     * @param listingSkuId 店铺商品SKU同步状态主键
     * @return 结果
     */
    @Override
    public int deleteShopListingSkuByListingSkuId(String listingSkuId)
    {
        return shopListingSkuMapper.deleteShopListingSkuByListingSkuId(listingSkuId);
    }
}
