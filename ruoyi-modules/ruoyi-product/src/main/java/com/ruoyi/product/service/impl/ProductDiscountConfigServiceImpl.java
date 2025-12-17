package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ProductDiscountConfigMapper;
import com.ruoyi.product.domain.ProductDiscountConfig;
import com.ruoyi.product.service.IProductDiscountConfigService;

/**
 * 商品优惠配置Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ProductDiscountConfigServiceImpl implements IProductDiscountConfigService 
{
    @Autowired
    private ProductDiscountConfigMapper productDiscountConfigMapper;

    /**
     * 查询商品优惠配置
     * 
     * @param id 商品优惠配置主键
     * @return 商品优惠配置
     */
    @Override
    public ProductDiscountConfig selectProductDiscountConfigById(String id)
    {
        return productDiscountConfigMapper.selectProductDiscountConfigById(id);
    }

    /**
     * 查询商品优惠配置列表
     * 
     * @param productDiscountConfig 商品优惠配置
     * @return 商品优惠配置
     */
    @Override
    public List<ProductDiscountConfig> selectProductDiscountConfigList(ProductDiscountConfig productDiscountConfig)
    {
        return productDiscountConfigMapper.selectProductDiscountConfigList(productDiscountConfig);
    }

    /**
     * 新增商品优惠配置
     * 
     * @param productDiscountConfig 商品优惠配置
     * @return 结果
     */
    @Override
    public int insertProductDiscountConfig(ProductDiscountConfig productDiscountConfig)
    {
        return productDiscountConfigMapper.insertProductDiscountConfig(productDiscountConfig);
    }

    /**
     * 修改商品优惠配置
     * 
     * @param productDiscountConfig 商品优惠配置
     * @return 结果
     */
    @Override
    public int updateProductDiscountConfig(ProductDiscountConfig productDiscountConfig)
    {
        return productDiscountConfigMapper.updateProductDiscountConfig(productDiscountConfig);
    }

    /**
     * 批量删除商品优惠配置
     * 
     * @param ids 需要删除的商品优惠配置主键
     * @return 结果
     */
    @Override
    public int deleteProductDiscountConfigByIds(String[] ids)
    {
        return productDiscountConfigMapper.deleteProductDiscountConfigByIds(ids);
    }

    /**
     * 删除商品优惠配置信息
     * 
     * @param id 商品优惠配置主键
     * @return 结果
     */
    @Override
    public int deleteProductDiscountConfigById(String id)
    {
        return productDiscountConfigMapper.deleteProductDiscountConfigById(id);
    }
}
