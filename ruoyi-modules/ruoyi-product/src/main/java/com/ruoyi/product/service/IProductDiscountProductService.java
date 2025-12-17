package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.ProductDiscountProduct;

/**
 * 商品优惠活动商品Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IProductDiscountProductService 
{
    /**
     * 查询商品优惠活动商品
     * 
     * @param id 商品优惠活动商品主键
     * @return 商品优惠活动商品
     */
    public ProductDiscountProduct selectProductDiscountProductById(String id);

    /**
     * 查询商品优惠活动商品列表
     * 
     * @param productDiscountProduct 商品优惠活动商品
     * @return 商品优惠活动商品集合
     */
    public List<ProductDiscountProduct> selectProductDiscountProductList(ProductDiscountProduct productDiscountProduct);

    /**
     * 新增商品优惠活动商品
     * 
     * @param productDiscountProduct 商品优惠活动商品
     * @return 结果
     */
    public int insertProductDiscountProduct(ProductDiscountProduct productDiscountProduct);

    /**
     * 修改商品优惠活动商品
     * 
     * @param productDiscountProduct 商品优惠活动商品
     * @return 结果
     */
    public int updateProductDiscountProduct(ProductDiscountProduct productDiscountProduct);

    /**
     * 批量删除商品优惠活动商品
     * 
     * @param ids 需要删除的商品优惠活动商品主键集合
     * @return 结果
     */
    public int deleteProductDiscountProductByIds(String[] ids);

    /**
     * 删除商品优惠活动商品信息
     * 
     * @param id 商品优惠活动商品主键
     * @return 结果
     */
    public int deleteProductDiscountProductById(String id);
}
