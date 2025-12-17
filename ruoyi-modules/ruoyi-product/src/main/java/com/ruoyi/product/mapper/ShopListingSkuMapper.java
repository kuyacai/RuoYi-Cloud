package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.ShopListingSku;

/**
 * 店铺商品SKU同步状态Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface ShopListingSkuMapper 
{
    /**
     * 查询店铺商品SKU同步状态
     * 
     * @param listingSkuId 店铺商品SKU同步状态主键
     * @return 店铺商品SKU同步状态
     */
    public ShopListingSku selectShopListingSkuByListingSkuId(String listingSkuId);

    /**
     * 查询店铺商品SKU同步状态列表
     * 
     * @param shopListingSku 店铺商品SKU同步状态
     * @return 店铺商品SKU同步状态集合
     */
    public List<ShopListingSku> selectShopListingSkuList(ShopListingSku shopListingSku);

    /**
     * 新增店铺商品SKU同步状态
     * 
     * @param shopListingSku 店铺商品SKU同步状态
     * @return 结果
     */
    public int insertShopListingSku(ShopListingSku shopListingSku);

    /**
     * 修改店铺商品SKU同步状态
     * 
     * @param shopListingSku 店铺商品SKU同步状态
     * @return 结果
     */
    public int updateShopListingSku(ShopListingSku shopListingSku);

    /**
     * 删除店铺商品SKU同步状态
     * 
     * @param listingSkuId 店铺商品SKU同步状态主键
     * @return 结果
     */
    public int deleteShopListingSkuByListingSkuId(String listingSkuId);

    /**
     * 批量删除店铺商品SKU同步状态
     * 
     * @param listingSkuIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopListingSkuByListingSkuIds(String[] listingSkuIds);
}
