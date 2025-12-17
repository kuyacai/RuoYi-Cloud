package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.ProductDiscountActivity;

/**
 * 商品优惠活动Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IProductDiscountActivityService 
{
    /**
     * 查询商品优惠活动
     * 
     * @param activityId 商品优惠活动主键
     * @return 商品优惠活动
     */
    public ProductDiscountActivity selectProductDiscountActivityByActivityId(String activityId);

    /**
     * 查询商品优惠活动列表
     * 
     * @param productDiscountActivity 商品优惠活动
     * @return 商品优惠活动集合
     */
    public List<ProductDiscountActivity> selectProductDiscountActivityList(ProductDiscountActivity productDiscountActivity);

    /**
     * 新增商品优惠活动
     * 
     * @param productDiscountActivity 商品优惠活动
     * @return 结果
     */
    public int insertProductDiscountActivity(ProductDiscountActivity productDiscountActivity);

    /**
     * 修改商品优惠活动
     * 
     * @param productDiscountActivity 商品优惠活动
     * @return 结果
     */
    public int updateProductDiscountActivity(ProductDiscountActivity productDiscountActivity);

    /**
     * 批量删除商品优惠活动
     * 
     * @param activityIds 需要删除的商品优惠活动主键集合
     * @return 结果
     */
    public int deleteProductDiscountActivityByActivityIds(String[] activityIds);

    /**
     * 删除商品优惠活动信息
     * 
     * @param activityId 商品优惠活动主键
     * @return 结果
     */
    public int deleteProductDiscountActivityByActivityId(String activityId);
}
