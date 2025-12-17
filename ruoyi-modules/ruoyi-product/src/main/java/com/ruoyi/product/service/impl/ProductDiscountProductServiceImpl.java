package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ProductDiscountProductMapper;
import com.ruoyi.product.domain.ProductDiscountProduct;
import com.ruoyi.product.service.IProductDiscountProductService;

/**
 * 商品优惠活动商品Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ProductDiscountProductServiceImpl implements IProductDiscountProductService 
{
    @Autowired
    private ProductDiscountProductMapper productDiscountProductMapper;

    /**
     * 查询商品优惠活动商品
     * 
     * @param id 商品优惠活动商品主键
     * @return 商品优惠活动商品
     */
    @Override
    public ProductDiscountProduct selectProductDiscountProductById(String id)
    {
        return productDiscountProductMapper.selectProductDiscountProductById(id);
    }

    /**
     * 查询商品优惠活动商品列表
     * 
     * @param productDiscountProduct 商品优惠活动商品
     * @return 商品优惠活动商品
     */
    @Override
    public List<ProductDiscountProduct> selectProductDiscountProductList(ProductDiscountProduct productDiscountProduct)
    {
        return productDiscountProductMapper.selectProductDiscountProductList(productDiscountProduct);
    }

    /**
     * 新增商品优惠活动商品
     * 
     * @param productDiscountProduct 商品优惠活动商品
     * @return 结果
     */
    @Override
    public int insertProductDiscountProduct(ProductDiscountProduct productDiscountProduct)
    {
        return productDiscountProductMapper.insertProductDiscountProduct(productDiscountProduct);
    }

    /**
     * 修改商品优惠活动商品
     * 
     * @param productDiscountProduct 商品优惠活动商品
     * @return 结果
     */
    @Override
    public int updateProductDiscountProduct(ProductDiscountProduct productDiscountProduct)
    {
        return productDiscountProductMapper.updateProductDiscountProduct(productDiscountProduct);
    }

    /**
     * 批量删除商品优惠活动商品
     * 
     * @param ids 需要删除的商品优惠活动商品主键
     * @return 结果
     */
    @Override
    public int deleteProductDiscountProductByIds(String[] ids)
    {
        return productDiscountProductMapper.deleteProductDiscountProductByIds(ids);
    }

    /**
     * 删除商品优惠活动商品信息
     * 
     * @param id 商品优惠活动商品主键
     * @return 结果
     */
    @Override
    public int deleteProductDiscountProductById(String id)
    {
        return productDiscountProductMapper.deleteProductDiscountProductById(id);
    }
}
