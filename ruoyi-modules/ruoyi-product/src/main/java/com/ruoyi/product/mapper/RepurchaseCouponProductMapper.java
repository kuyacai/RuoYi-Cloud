package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.RepurchaseCouponProduct;

/**
 * 复购券活动商品Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface RepurchaseCouponProductMapper 
{
    /**
     * 查询复购券活动商品
     * 
     * @param id 复购券活动商品主键
     * @return 复购券活动商品
     */
    public RepurchaseCouponProduct selectRepurchaseCouponProductById(String id);

    /**
     * 查询复购券活动商品列表
     * 
     * @param repurchaseCouponProduct 复购券活动商品
     * @return 复购券活动商品集合
     */
    public List<RepurchaseCouponProduct> selectRepurchaseCouponProductList(RepurchaseCouponProduct repurchaseCouponProduct);

    /**
     * 新增复购券活动商品
     * 
     * @param repurchaseCouponProduct 复购券活动商品
     * @return 结果
     */
    public int insertRepurchaseCouponProduct(RepurchaseCouponProduct repurchaseCouponProduct);

    /**
     * 修改复购券活动商品
     * 
     * @param repurchaseCouponProduct 复购券活动商品
     * @return 结果
     */
    public int updateRepurchaseCouponProduct(RepurchaseCouponProduct repurchaseCouponProduct);

    /**
     * 删除复购券活动商品
     * 
     * @param id 复购券活动商品主键
     * @return 结果
     */
    public int deleteRepurchaseCouponProductById(String id);

    /**
     * 批量删除复购券活动商品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepurchaseCouponProductByIds(String[] ids);
}
