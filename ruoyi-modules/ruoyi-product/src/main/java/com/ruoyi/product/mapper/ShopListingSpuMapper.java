package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.ShopListingSpu;

/**
 * 店铺商品同步状态Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface ShopListingSpuMapper 
{
    /**
     * 查询店铺商品同步状态
     * 
     * @param listingSpuId 店铺商品同步状态主键
     * @return 店铺商品同步状态
     */
    public ShopListingSpu selectShopListingSpuByListingSpuId(String listingSpuId);

    /**
     * 查询店铺商品同步状态列表
     * 
     * @param shopListingSpu 店铺商品同步状态
     * @return 店铺商品同步状态集合
     */
    public List<ShopListingSpu> selectShopListingSpuList(ShopListingSpu shopListingSpu);

    /**
     * 新增店铺商品同步状态
     * 
     * @param shopListingSpu 店铺商品同步状态
     * @return 结果
     */
    public int insertShopListingSpu(ShopListingSpu shopListingSpu);

    /**
     * 修改店铺商品同步状态
     * 
     * @param shopListingSpu 店铺商品同步状态
     * @return 结果
     */
    public int updateShopListingSpu(ShopListingSpu shopListingSpu);

    /**
     * 删除店铺商品同步状态
     * 
     * @param listingSpuId 店铺商品同步状态主键
     * @return 结果
     */
    public int deleteShopListingSpuByListingSpuId(String listingSpuId);

    /**
     * 批量删除店铺商品同步状态
     * 
     * @param listingSpuIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopListingSpuByListingSpuIds(String[] listingSpuIds);
}
