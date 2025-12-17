package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.RepurchaseCouponProductMapper;
import com.ruoyi.product.domain.RepurchaseCouponProduct;
import com.ruoyi.product.service.IRepurchaseCouponProductService;

/**
 * 复购券活动商品Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class RepurchaseCouponProductServiceImpl implements IRepurchaseCouponProductService 
{
    @Autowired
    private RepurchaseCouponProductMapper repurchaseCouponProductMapper;

    /**
     * 查询复购券活动商品
     * 
     * @param id 复购券活动商品主键
     * @return 复购券活动商品
     */
    @Override
    public RepurchaseCouponProduct selectRepurchaseCouponProductById(String id)
    {
        return repurchaseCouponProductMapper.selectRepurchaseCouponProductById(id);
    }

    /**
     * 查询复购券活动商品列表
     * 
     * @param repurchaseCouponProduct 复购券活动商品
     * @return 复购券活动商品
     */
    @Override
    public List<RepurchaseCouponProduct> selectRepurchaseCouponProductList(RepurchaseCouponProduct repurchaseCouponProduct)
    {
        return repurchaseCouponProductMapper.selectRepurchaseCouponProductList(repurchaseCouponProduct);
    }

    /**
     * 新增复购券活动商品
     * 
     * @param repurchaseCouponProduct 复购券活动商品
     * @return 结果
     */
    @Override
    public int insertRepurchaseCouponProduct(RepurchaseCouponProduct repurchaseCouponProduct)
    {
        return repurchaseCouponProductMapper.insertRepurchaseCouponProduct(repurchaseCouponProduct);
    }

    /**
     * 修改复购券活动商品
     * 
     * @param repurchaseCouponProduct 复购券活动商品
     * @return 结果
     */
    @Override
    public int updateRepurchaseCouponProduct(RepurchaseCouponProduct repurchaseCouponProduct)
    {
        return repurchaseCouponProductMapper.updateRepurchaseCouponProduct(repurchaseCouponProduct);
    }

    /**
     * 批量删除复购券活动商品
     * 
     * @param ids 需要删除的复购券活动商品主键
     * @return 结果
     */
    @Override
    public int deleteRepurchaseCouponProductByIds(String[] ids)
    {
        return repurchaseCouponProductMapper.deleteRepurchaseCouponProductByIds(ids);
    }

    /**
     * 删除复购券活动商品信息
     * 
     * @param id 复购券活动商品主键
     * @return 结果
     */
    @Override
    public int deleteRepurchaseCouponProductById(String id)
    {
        return repurchaseCouponProductMapper.deleteRepurchaseCouponProductById(id);
    }
}
