package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.PlatformPromotionProductMapper;
import com.ruoyi.product.domain.PlatformPromotionProduct;
import com.ruoyi.product.service.IPlatformPromotionProductService;

/**
 * 平台促销活动商品Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class PlatformPromotionProductServiceImpl implements IPlatformPromotionProductService 
{
    @Autowired
    private PlatformPromotionProductMapper platformPromotionProductMapper;

    /**
     * 查询平台促销活动商品
     * 
     * @param id 平台促销活动商品主键
     * @return 平台促销活动商品
     */
    @Override
    public PlatformPromotionProduct selectPlatformPromotionProductById(String id)
    {
        return platformPromotionProductMapper.selectPlatformPromotionProductById(id);
    }

    /**
     * 查询平台促销活动商品列表
     * 
     * @param platformPromotionProduct 平台促销活动商品
     * @return 平台促销活动商品
     */
    @Override
    public List<PlatformPromotionProduct> selectPlatformPromotionProductList(PlatformPromotionProduct platformPromotionProduct)
    {
        return platformPromotionProductMapper.selectPlatformPromotionProductList(platformPromotionProduct);
    }

    /**
     * 新增平台促销活动商品
     * 
     * @param platformPromotionProduct 平台促销活动商品
     * @return 结果
     */
    @Override
    public int insertPlatformPromotionProduct(PlatformPromotionProduct platformPromotionProduct)
    {
        return platformPromotionProductMapper.insertPlatformPromotionProduct(platformPromotionProduct);
    }

    /**
     * 修改平台促销活动商品
     * 
     * @param platformPromotionProduct 平台促销活动商品
     * @return 结果
     */
    @Override
    public int updatePlatformPromotionProduct(PlatformPromotionProduct platformPromotionProduct)
    {
        return platformPromotionProductMapper.updatePlatformPromotionProduct(platformPromotionProduct);
    }

    /**
     * 批量删除平台促销活动商品
     * 
     * @param ids 需要删除的平台促销活动商品主键
     * @return 结果
     */
    @Override
    public int deletePlatformPromotionProductByIds(String[] ids)
    {
        return platformPromotionProductMapper.deletePlatformPromotionProductByIds(ids);
    }

    /**
     * 删除平台促销活动商品信息
     * 
     * @param id 平台促销活动商品主键
     * @return 结果
     */
    @Override
    public int deletePlatformPromotionProductById(String id)
    {
        return platformPromotionProductMapper.deletePlatformPromotionProductById(id);
    }
}
