package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.PlatformPromotionProduct;

/**
 * 平台促销活动商品Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface PlatformPromotionProductMapper 
{
    /**
     * 查询平台促销活动商品
     * 
     * @param id 平台促销活动商品主键
     * @return 平台促销活动商品
     */
    public PlatformPromotionProduct selectPlatformPromotionProductById(String id);

    /**
     * 查询平台促销活动商品列表
     * 
     * @param platformPromotionProduct 平台促销活动商品
     * @return 平台促销活动商品集合
     */
    public List<PlatformPromotionProduct> selectPlatformPromotionProductList(PlatformPromotionProduct platformPromotionProduct);

    /**
     * 新增平台促销活动商品
     * 
     * @param platformPromotionProduct 平台促销活动商品
     * @return 结果
     */
    public int insertPlatformPromotionProduct(PlatformPromotionProduct platformPromotionProduct);

    /**
     * 修改平台促销活动商品
     * 
     * @param platformPromotionProduct 平台促销活动商品
     * @return 结果
     */
    public int updatePlatformPromotionProduct(PlatformPromotionProduct platformPromotionProduct);

    /**
     * 删除平台促销活动商品
     * 
     * @param id 平台促销活动商品主键
     * @return 结果
     */
    public int deletePlatformPromotionProductById(String id);

    /**
     * 批量删除平台促销活动商品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformPromotionProductByIds(String[] ids);
}
