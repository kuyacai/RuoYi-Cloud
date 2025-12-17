package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.Shop;

/**
 * 店铺Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IShopService 
{
    /**
     * 查询店铺
     * 
     * @param shopId 店铺主键
     * @return 店铺
     */
    public Shop selectShopByShopId(String shopId);

    /**
     * 查询店铺列表
     * 
     * @param shop 店铺
     * @return 店铺集合
     */
    public List<Shop> selectShopList(Shop shop);

    /**
     * 新增店铺
     * 
     * @param shop 店铺
     * @return 结果
     */
    public int insertShop(Shop shop);

    /**
     * 修改店铺
     * 
     * @param shop 店铺
     * @return 结果
     */
    public int updateShop(Shop shop);

    /**
     * 批量删除店铺
     * 
     * @param shopIds 需要删除的店铺主键集合
     * @return 结果
     */
    public int deleteShopByShopIds(String[] shopIds);

    /**
     * 删除店铺信息
     * 
     * @param shopId 店铺主键
     * @return 结果
     */
    public int deleteShopByShopId(String shopId);
}
