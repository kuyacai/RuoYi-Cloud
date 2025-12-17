package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.ProductDiscountConfig;

/**
 * 商品优惠配置Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface ProductDiscountConfigMapper 
{
    /**
     * 查询商品优惠配置
     * 
     * @param id 商品优惠配置主键
     * @return 商品优惠配置
     */
    public ProductDiscountConfig selectProductDiscountConfigById(String id);

    /**
     * 查询商品优惠配置列表
     * 
     * @param productDiscountConfig 商品优惠配置
     * @return 商品优惠配置集合
     */
    public List<ProductDiscountConfig> selectProductDiscountConfigList(ProductDiscountConfig productDiscountConfig);

    /**
     * 新增商品优惠配置
     * 
     * @param productDiscountConfig 商品优惠配置
     * @return 结果
     */
    public int insertProductDiscountConfig(ProductDiscountConfig productDiscountConfig);

    /**
     * 修改商品优惠配置
     * 
     * @param productDiscountConfig 商品优惠配置
     * @return 结果
     */
    public int updateProductDiscountConfig(ProductDiscountConfig productDiscountConfig);

    /**
     * 删除商品优惠配置
     * 
     * @param id 商品优惠配置主键
     * @return 结果
     */
    public int deleteProductDiscountConfigById(String id);

    /**
     * 批量删除商品优惠配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductDiscountConfigByIds(String[] ids);
}
