package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.PricingMultiplier;

/**
 * 定价倍数Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface PricingMultiplierMapper 
{
    /**
     * 查询定价倍数
     * 
     * @param id 定价倍数主键
     * @return 定价倍数
     */
    public PricingMultiplier selectPricingMultiplierById(String id);

    /**
     * 查询定价倍数列表
     * 
     * @param pricingMultiplier 定价倍数
     * @return 定价倍数集合
     */
    public List<PricingMultiplier> selectPricingMultiplierList(PricingMultiplier pricingMultiplier);

    /**
     * 新增定价倍数
     * 
     * @param pricingMultiplier 定价倍数
     * @return 结果
     */
    public int insertPricingMultiplier(PricingMultiplier pricingMultiplier);

    /**
     * 修改定价倍数
     * 
     * @param pricingMultiplier 定价倍数
     * @return 结果
     */
    public int updatePricingMultiplier(PricingMultiplier pricingMultiplier);

    /**
     * 删除定价倍数
     * 
     * @param id 定价倍数主键
     * @return 结果
     */
    public int deletePricingMultiplierById(String id);

    /**
     * 批量删除定价倍数
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePricingMultiplierByIds(String[] ids);
}
