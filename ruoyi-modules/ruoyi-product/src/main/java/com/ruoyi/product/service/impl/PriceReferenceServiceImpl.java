package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.PriceReferenceMapper;
import com.ruoyi.product.domain.PriceReference;
import com.ruoyi.product.service.IPriceReferenceService;

/**
 * 价格参考Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class PriceReferenceServiceImpl implements IPriceReferenceService 
{
    @Autowired
    private PriceReferenceMapper priceReferenceMapper;

    /**
     * 查询价格参考
     * 
     * @param id 价格参考主键
     * @return 价格参考
     */
    @Override
    public PriceReference selectPriceReferenceById(String id)
    {
        return priceReferenceMapper.selectPriceReferenceById(id);
    }

    /**
     * 查询价格参考列表
     * 
     * @param priceReference 价格参考
     * @return 价格参考
     */
    @Override
    public List<PriceReference> selectPriceReferenceList(PriceReference priceReference)
    {
        return priceReferenceMapper.selectPriceReferenceList(priceReference);
    }

    /**
     * 新增价格参考
     * 
     * @param priceReference 价格参考
     * @return 结果
     */
    @Override
    public int insertPriceReference(PriceReference priceReference)
    {
        return priceReferenceMapper.insertPriceReference(priceReference);
    }

    /**
     * 修改价格参考
     * 
     * @param priceReference 价格参考
     * @return 结果
     */
    @Override
    public int updatePriceReference(PriceReference priceReference)
    {
        return priceReferenceMapper.updatePriceReference(priceReference);
    }

    /**
     * 批量删除价格参考
     * 
     * @param ids 需要删除的价格参考主键
     * @return 结果
     */
    @Override
    public int deletePriceReferenceByIds(String[] ids)
    {
        return priceReferenceMapper.deletePriceReferenceByIds(ids);
    }

    /**
     * 删除价格参考信息
     * 
     * @param id 价格参考主键
     * @return 结果
     */
    @Override
    public int deletePriceReferenceById(String id)
    {
        return priceReferenceMapper.deletePriceReferenceById(id);
    }
}
