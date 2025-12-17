package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ShopListingSpuMapper;
import com.ruoyi.product.domain.ShopListingSpu;
import com.ruoyi.product.service.IShopListingSpuService;

/**
 * 店铺商品同步状态Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ShopListingSpuServiceImpl implements IShopListingSpuService 
{
    @Autowired
    private ShopListingSpuMapper shopListingSpuMapper;

    /**
     * 查询店铺商品同步状态
     * 
     * @param listingSpuId 店铺商品同步状态主键
     * @return 店铺商品同步状态
     */
    @Override
    public ShopListingSpu selectShopListingSpuByListingSpuId(String listingSpuId)
    {
        return shopListingSpuMapper.selectShopListingSpuByListingSpuId(listingSpuId);
    }

    /**
     * 查询店铺商品同步状态列表
     * 
     * @param shopListingSpu 店铺商品同步状态
     * @return 店铺商品同步状态
     */
    @Override
    public List<ShopListingSpu> selectShopListingSpuList(ShopListingSpu shopListingSpu)
    {
        return shopListingSpuMapper.selectShopListingSpuList(shopListingSpu);
    }

    /**
     * 新增店铺商品同步状态
     * 
     * @param shopListingSpu 店铺商品同步状态
     * @return 结果
     */
    @Override
    public int insertShopListingSpu(ShopListingSpu shopListingSpu)
    {
        return shopListingSpuMapper.insertShopListingSpu(shopListingSpu);
    }

    /**
     * 修改店铺商品同步状态
     * 
     * @param shopListingSpu 店铺商品同步状态
     * @return 结果
     */
    @Override
    public int updateShopListingSpu(ShopListingSpu shopListingSpu)
    {
        return shopListingSpuMapper.updateShopListingSpu(shopListingSpu);
    }

    /**
     * 批量删除店铺商品同步状态
     * 
     * @param listingSpuIds 需要删除的店铺商品同步状态主键
     * @return 结果
     */
    @Override
    public int deleteShopListingSpuByListingSpuIds(String[] listingSpuIds)
    {
        return shopListingSpuMapper.deleteShopListingSpuByListingSpuIds(listingSpuIds);
    }

    /**
     * 删除店铺商品同步状态信息
     * 
     * @param listingSpuId 店铺商品同步状态主键
     * @return 结果
     */
    @Override
    public int deleteShopListingSpuByListingSpuId(String listingSpuId)
    {
        return shopListingSpuMapper.deleteShopListingSpuByListingSpuId(listingSpuId);
    }
}
