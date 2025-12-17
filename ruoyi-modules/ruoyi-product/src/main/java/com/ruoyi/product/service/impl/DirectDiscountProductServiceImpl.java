package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.DirectDiscountProductMapper;
import com.ruoyi.product.domain.DirectDiscountProduct;
import com.ruoyi.product.service.IDirectDiscountProductService;

/**
 * 单品直降活动商品Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class DirectDiscountProductServiceImpl implements IDirectDiscountProductService 
{
    @Autowired
    private DirectDiscountProductMapper directDiscountProductMapper;

    /**
     * 查询单品直降活动商品
     * 
     * @param id 单品直降活动商品主键
     * @return 单品直降活动商品
     */
    @Override
    public DirectDiscountProduct selectDirectDiscountProductById(String id)
    {
        return directDiscountProductMapper.selectDirectDiscountProductById(id);
    }

    /**
     * 查询单品直降活动商品列表
     * 
     * @param directDiscountProduct 单品直降活动商品
     * @return 单品直降活动商品
     */
    @Override
    public List<DirectDiscountProduct> selectDirectDiscountProductList(DirectDiscountProduct directDiscountProduct)
    {
        return directDiscountProductMapper.selectDirectDiscountProductList(directDiscountProduct);
    }

    /**
     * 新增单品直降活动商品
     * 
     * @param directDiscountProduct 单品直降活动商品
     * @return 结果
     */
    @Override
    public int insertDirectDiscountProduct(DirectDiscountProduct directDiscountProduct)
    {
        return directDiscountProductMapper.insertDirectDiscountProduct(directDiscountProduct);
    }

    /**
     * 修改单品直降活动商品
     * 
     * @param directDiscountProduct 单品直降活动商品
     * @return 结果
     */
    @Override
    public int updateDirectDiscountProduct(DirectDiscountProduct directDiscountProduct)
    {
        return directDiscountProductMapper.updateDirectDiscountProduct(directDiscountProduct);
    }

    /**
     * 批量删除单品直降活动商品
     * 
     * @param ids 需要删除的单品直降活动商品主键
     * @return 结果
     */
    @Override
    public int deleteDirectDiscountProductByIds(String[] ids)
    {
        return directDiscountProductMapper.deleteDirectDiscountProductByIds(ids);
    }

    /**
     * 删除单品直降活动商品信息
     * 
     * @param id 单品直降活动商品主键
     * @return 结果
     */
    @Override
    public int deleteDirectDiscountProductById(String id)
    {
        return directDiscountProductMapper.deleteDirectDiscountProductById(id);
    }
}
