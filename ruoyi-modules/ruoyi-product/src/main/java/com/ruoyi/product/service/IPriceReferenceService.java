package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.PriceReference;

/**
 * 价格参考Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IPriceReferenceService 
{
    /**
     * 查询价格参考
     * 
     * @param id 价格参考主键
     * @return 价格参考
     */
    public PriceReference selectPriceReferenceById(String id);

    /**
     * 查询价格参考列表
     * 
     * @param priceReference 价格参考
     * @return 价格参考集合
     */
    public List<PriceReference> selectPriceReferenceList(PriceReference priceReference);

    /**
     * 新增价格参考
     * 
     * @param priceReference 价格参考
     * @return 结果
     */
    public int insertPriceReference(PriceReference priceReference);

    /**
     * 修改价格参考
     * 
     * @param priceReference 价格参考
     * @return 结果
     */
    public int updatePriceReference(PriceReference priceReference);

    /**
     * 批量删除价格参考
     * 
     * @param ids 需要删除的价格参考主键集合
     * @return 结果
     */
    public int deletePriceReferenceByIds(String[] ids);

    /**
     * 删除价格参考信息
     * 
     * @param id 价格参考主键
     * @return 结果
     */
    public int deletePriceReferenceById(String id);
}
