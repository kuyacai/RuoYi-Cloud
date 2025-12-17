package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.DirectDiscount;

/**
 * 单品直降配置Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface DirectDiscountMapper 
{
    /**
     * 查询单品直降配置
     * 
     * @param id 单品直降配置主键
     * @return 单品直降配置
     */
    public DirectDiscount selectDirectDiscountById(String id);

    /**
     * 查询单品直降配置列表
     * 
     * @param directDiscount 单品直降配置
     * @return 单品直降配置集合
     */
    public List<DirectDiscount> selectDirectDiscountList(DirectDiscount directDiscount);

    /**
     * 新增单品直降配置
     * 
     * @param directDiscount 单品直降配置
     * @return 结果
     */
    public int insertDirectDiscount(DirectDiscount directDiscount);

    /**
     * 修改单品直降配置
     * 
     * @param directDiscount 单品直降配置
     * @return 结果
     */
    public int updateDirectDiscount(DirectDiscount directDiscount);

    /**
     * 删除单品直降配置
     * 
     * @param id 单品直降配置主键
     * @return 结果
     */
    public int deleteDirectDiscountById(String id);

    /**
     * 批量删除单品直降配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDirectDiscountByIds(String[] ids);
}
