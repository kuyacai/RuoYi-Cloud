package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.DirectDiscountProduct;

/**
 * 单品直降活动商品Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface DirectDiscountProductMapper 
{
    /**
     * 查询单品直降活动商品
     * 
     * @param id 单品直降活动商品主键
     * @return 单品直降活动商品
     */
    public DirectDiscountProduct selectDirectDiscountProductById(String id);

    /**
     * 查询单品直降活动商品列表
     * 
     * @param directDiscountProduct 单品直降活动商品
     * @return 单品直降活动商品集合
     */
    public List<DirectDiscountProduct> selectDirectDiscountProductList(DirectDiscountProduct directDiscountProduct);

    /**
     * 新增单品直降活动商品
     * 
     * @param directDiscountProduct 单品直降活动商品
     * @return 结果
     */
    public int insertDirectDiscountProduct(DirectDiscountProduct directDiscountProduct);

    /**
     * 修改单品直降活动商品
     * 
     * @param directDiscountProduct 单品直降活动商品
     * @return 结果
     */
    public int updateDirectDiscountProduct(DirectDiscountProduct directDiscountProduct);

    /**
     * 删除单品直降活动商品
     * 
     * @param id 单品直降活动商品主键
     * @return 结果
     */
    public int deleteDirectDiscountProductById(String id);

    /**
     * 批量删除单品直降活动商品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDirectDiscountProductByIds(String[] ids);
}
