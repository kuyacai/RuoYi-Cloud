package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.PricingMultiplierMapper;
import com.ruoyi.product.domain.PricingMultiplier;
import com.ruoyi.product.service.IPricingMultiplierService;

/**
 * 定价倍数Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class PricingMultiplierServiceImpl implements IPricingMultiplierService 
{
    @Autowired
    private PricingMultiplierMapper pricingMultiplierMapper;

    /**
     * 查询定价倍数
     * 
     * @param id 定价倍数主键
     * @return 定价倍数
     */
    @Override
    public PricingMultiplier selectPricingMultiplierById(String id)
    {
        return pricingMultiplierMapper.selectPricingMultiplierById(id);
    }

    /**
     * 查询定价倍数列表
     * 
     * @param pricingMultiplier 定价倍数
     * @return 定价倍数
     */
    @Override
    public List<PricingMultiplier> selectPricingMultiplierList(PricingMultiplier pricingMultiplier)
    {
        return pricingMultiplierMapper.selectPricingMultiplierList(pricingMultiplier);
    }

    /**
     * 新增定价倍数
     * 
     * @param pricingMultiplier 定价倍数
     * @return 结果
     */
    @Override
    public int insertPricingMultiplier(PricingMultiplier pricingMultiplier)
    {
        return pricingMultiplierMapper.insertPricingMultiplier(pricingMultiplier);
    }

    /**
     * 修改定价倍数
     * 
     * @param pricingMultiplier 定价倍数
     * @return 结果
     */
    @Override
    public int updatePricingMultiplier(PricingMultiplier pricingMultiplier)
    {
        return pricingMultiplierMapper.updatePricingMultiplier(pricingMultiplier);
    }

    /**
     * 批量删除定价倍数
     * 
     * @param ids 需要删除的定价倍数主键
     * @return 结果
     */
    @Override
    public int deletePricingMultiplierByIds(String[] ids)
    {
        return pricingMultiplierMapper.deletePricingMultiplierByIds(ids);
    }

    /**
     * 删除定价倍数信息
     * 
     * @param id 定价倍数主键
     * @return 结果
     */
    @Override
    public int deletePricingMultiplierById(String id)
    {
        return pricingMultiplierMapper.deletePricingMultiplierById(id);
    }
}
