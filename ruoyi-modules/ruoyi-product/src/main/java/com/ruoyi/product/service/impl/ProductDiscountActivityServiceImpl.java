package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.ProductDiscountActivityMapper;
import com.ruoyi.product.domain.ProductDiscountActivity;
import com.ruoyi.product.service.IProductDiscountActivityService;

/**
 * 商品优惠活动Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ProductDiscountActivityServiceImpl implements IProductDiscountActivityService 
{
    @Autowired
    private ProductDiscountActivityMapper productDiscountActivityMapper;

    /**
     * 查询商品优惠活动
     * 
     * @param activityId 商品优惠活动主键
     * @return 商品优惠活动
     */
    @Override
    public ProductDiscountActivity selectProductDiscountActivityByActivityId(String activityId)
    {
        return productDiscountActivityMapper.selectProductDiscountActivityByActivityId(activityId);
    }

    /**
     * 查询商品优惠活动列表
     * 
     * @param productDiscountActivity 商品优惠活动
     * @return 商品优惠活动
     */
    @Override
    public List<ProductDiscountActivity> selectProductDiscountActivityList(ProductDiscountActivity productDiscountActivity)
    {
        return productDiscountActivityMapper.selectProductDiscountActivityList(productDiscountActivity);
    }

    /**
     * 新增商品优惠活动
     * 
     * @param productDiscountActivity 商品优惠活动
     * @return 结果
     */
    @Override
    public int insertProductDiscountActivity(ProductDiscountActivity productDiscountActivity)
    {
        return productDiscountActivityMapper.insertProductDiscountActivity(productDiscountActivity);
    }

    /**
     * 修改商品优惠活动
     * 
     * @param productDiscountActivity 商品优惠活动
     * @return 结果
     */
    @Override
    public int updateProductDiscountActivity(ProductDiscountActivity productDiscountActivity)
    {
        return productDiscountActivityMapper.updateProductDiscountActivity(productDiscountActivity);
    }

    /**
     * 批量删除商品优惠活动
     * 
     * @param activityIds 需要删除的商品优惠活动主键
     * @return 结果
     */
    @Override
    public int deleteProductDiscountActivityByActivityIds(String[] activityIds)
    {
        return productDiscountActivityMapper.deleteProductDiscountActivityByActivityIds(activityIds);
    }

    /**
     * 删除商品优惠活动信息
     * 
     * @param activityId 商品优惠活动主键
     * @return 结果
     */
    @Override
    public int deleteProductDiscountActivityByActivityId(String activityId)
    {
        return productDiscountActivityMapper.deleteProductDiscountActivityByActivityId(activityId);
    }
}
