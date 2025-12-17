package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.RepurchaseCouponConfigMapper;
import com.ruoyi.product.domain.RepurchaseCouponConfig;
import com.ruoyi.product.service.IRepurchaseCouponConfigService;

/**
 * 复购券配置Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class RepurchaseCouponConfigServiceImpl implements IRepurchaseCouponConfigService 
{
    @Autowired
    private RepurchaseCouponConfigMapper repurchaseCouponConfigMapper;

    /**
     * 查询复购券配置
     * 
     * @param id 复购券配置主键
     * @return 复购券配置
     */
    @Override
    public RepurchaseCouponConfig selectRepurchaseCouponConfigById(String id)
    {
        return repurchaseCouponConfigMapper.selectRepurchaseCouponConfigById(id);
    }

    /**
     * 查询复购券配置列表
     * 
     * @param repurchaseCouponConfig 复购券配置
     * @return 复购券配置
     */
    @Override
    public List<RepurchaseCouponConfig> selectRepurchaseCouponConfigList(RepurchaseCouponConfig repurchaseCouponConfig)
    {
        return repurchaseCouponConfigMapper.selectRepurchaseCouponConfigList(repurchaseCouponConfig);
    }

    /**
     * 新增复购券配置
     * 
     * @param repurchaseCouponConfig 复购券配置
     * @return 结果
     */
    @Override
    public int insertRepurchaseCouponConfig(RepurchaseCouponConfig repurchaseCouponConfig)
    {
        return repurchaseCouponConfigMapper.insertRepurchaseCouponConfig(repurchaseCouponConfig);
    }

    /**
     * 修改复购券配置
     * 
     * @param repurchaseCouponConfig 复购券配置
     * @return 结果
     */
    @Override
    public int updateRepurchaseCouponConfig(RepurchaseCouponConfig repurchaseCouponConfig)
    {
        return repurchaseCouponConfigMapper.updateRepurchaseCouponConfig(repurchaseCouponConfig);
    }

    /**
     * 批量删除复购券配置
     * 
     * @param ids 需要删除的复购券配置主键
     * @return 结果
     */
    @Override
    public int deleteRepurchaseCouponConfigByIds(String[] ids)
    {
        return repurchaseCouponConfigMapper.deleteRepurchaseCouponConfigByIds(ids);
    }

    /**
     * 删除复购券配置信息
     * 
     * @param id 复购券配置主键
     * @return 结果
     */
    @Override
    public int deleteRepurchaseCouponConfigById(String id)
    {
        return repurchaseCouponConfigMapper.deleteRepurchaseCouponConfigById(id);
    }
}
