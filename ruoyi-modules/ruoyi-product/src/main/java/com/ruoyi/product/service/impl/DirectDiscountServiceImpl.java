package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.DirectDiscountMapper;
import com.ruoyi.product.domain.DirectDiscount;
import com.ruoyi.product.service.IDirectDiscountService;

/**
 * 单品直降配置Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class DirectDiscountServiceImpl implements IDirectDiscountService 
{
    @Autowired
    private DirectDiscountMapper directDiscountMapper;

    /**
     * 查询单品直降配置
     * 
     * @param id 单品直降配置主键
     * @return 单品直降配置
     */
    @Override
    public DirectDiscount selectDirectDiscountById(String id)
    {
        return directDiscountMapper.selectDirectDiscountById(id);
    }

    /**
     * 查询单品直降配置列表
     * 
     * @param directDiscount 单品直降配置
     * @return 单品直降配置
     */
    @Override
    public List<DirectDiscount> selectDirectDiscountList(DirectDiscount directDiscount)
    {
        return directDiscountMapper.selectDirectDiscountList(directDiscount);
    }

    /**
     * 新增单品直降配置
     * 
     * @param directDiscount 单品直降配置
     * @return 结果
     */
    @Override
    public int insertDirectDiscount(DirectDiscount directDiscount)
    {
        return directDiscountMapper.insertDirectDiscount(directDiscount);
    }

    /**
     * 修改单品直降配置
     * 
     * @param directDiscount 单品直降配置
     * @return 结果
     */
    @Override
    public int updateDirectDiscount(DirectDiscount directDiscount)
    {
        return directDiscountMapper.updateDirectDiscount(directDiscount);
    }

    /**
     * 批量删除单品直降配置
     * 
     * @param ids 需要删除的单品直降配置主键
     * @return 结果
     */
    @Override
    public int deleteDirectDiscountByIds(String[] ids)
    {
        return directDiscountMapper.deleteDirectDiscountByIds(ids);
    }

    /**
     * 删除单品直降配置信息
     * 
     * @param id 单品直降配置主键
     * @return 结果
     */
    @Override
    public int deleteDirectDiscountById(String id)
    {
        return directDiscountMapper.deleteDirectDiscountById(id);
    }
}
