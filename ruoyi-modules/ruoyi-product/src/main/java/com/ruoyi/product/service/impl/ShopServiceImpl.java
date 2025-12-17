package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ShopMapper;
import com.ruoyi.product.domain.Shop;
import com.ruoyi.product.service.IShopService;

/**
 * 店铺Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ShopServiceImpl implements IShopService 
{
    @Autowired
    private ShopMapper shopMapper;

    /**
     * 查询店铺
     * 
     * @param shopId 店铺主键
     * @return 店铺
     */
    @Override
    public Shop selectShopByShopId(String shopId)
    {
        return shopMapper.selectShopByShopId(shopId);
    }

    /**
     * 查询店铺列表
     * 
     * @param shop 店铺
     * @return 店铺
     */
    @Override
    public List<Shop> selectShopList(Shop shop)
    {
        return shopMapper.selectShopList(shop);
    }

    /**
     * 新增店铺
     * 
     * @param shop 店铺
     * @return 结果
     */
    @Override
    public int insertShop(Shop shop)
    {
        return shopMapper.insertShop(shop);
    }

    /**
     * 修改店铺
     * 
     * @param shop 店铺
     * @return 结果
     */
    @Override
    public int updateShop(Shop shop)
    {
        return shopMapper.updateShop(shop);
    }

    /**
     * 批量删除店铺
     * 
     * @param shopIds 需要删除的店铺主键
     * @return 结果
     */
    @Override
    public int deleteShopByShopIds(String[] shopIds)
    {
        return shopMapper.deleteShopByShopIds(shopIds);
    }

    /**
     * 删除店铺信息
     * 
     * @param shopId 店铺主键
     * @return 结果
     */
    @Override
    public int deleteShopByShopId(String shopId)
    {
        return shopMapper.deleteShopByShopId(shopId);
    }
}
