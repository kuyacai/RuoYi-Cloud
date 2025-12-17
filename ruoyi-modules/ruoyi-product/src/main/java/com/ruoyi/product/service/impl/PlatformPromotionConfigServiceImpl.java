package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.PlatformPromotionConfigMapper;
import com.ruoyi.product.domain.PlatformPromotionConfig;
import com.ruoyi.product.service.IPlatformPromotionConfigService;

/**
 * 平台促销配置Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class PlatformPromotionConfigServiceImpl implements IPlatformPromotionConfigService 
{
    @Autowired
    private PlatformPromotionConfigMapper platformPromotionConfigMapper;

    /**
     * 查询平台促销配置
     * 
     * @param id 平台促销配置主键
     * @return 平台促销配置
     */
    @Override
    public PlatformPromotionConfig selectPlatformPromotionConfigById(String id)
    {
        return platformPromotionConfigMapper.selectPlatformPromotionConfigById(id);
    }

    /**
     * 查询平台促销配置列表
     * 
     * @param platformPromotionConfig 平台促销配置
     * @return 平台促销配置
     */
    @Override
    public List<PlatformPromotionConfig> selectPlatformPromotionConfigList(PlatformPromotionConfig platformPromotionConfig)
    {
        return platformPromotionConfigMapper.selectPlatformPromotionConfigList(platformPromotionConfig);
    }

    /**
     * 新增平台促销配置
     * 
     * @param platformPromotionConfig 平台促销配置
     * @return 结果
     */
    @Override
    public int insertPlatformPromotionConfig(PlatformPromotionConfig platformPromotionConfig)
    {
        return platformPromotionConfigMapper.insertPlatformPromotionConfig(platformPromotionConfig);
    }

    /**
     * 修改平台促销配置
     * 
     * @param platformPromotionConfig 平台促销配置
     * @return 结果
     */
    @Override
    public int updatePlatformPromotionConfig(PlatformPromotionConfig platformPromotionConfig)
    {
        return platformPromotionConfigMapper.updatePlatformPromotionConfig(platformPromotionConfig);
    }

    /**
     * 批量删除平台促销配置
     * 
     * @param ids 需要删除的平台促销配置主键
     * @return 结果
     */
    @Override
    public int deletePlatformPromotionConfigByIds(String[] ids)
    {
        return platformPromotionConfigMapper.deletePlatformPromotionConfigByIds(ids);
    }

    /**
     * 删除平台促销配置信息
     * 
     * @param id 平台促销配置主键
     * @return 结果
     */
    @Override
    public int deletePlatformPromotionConfigById(String id)
    {
        return platformPromotionConfigMapper.deletePlatformPromotionConfigById(id);
    }
}
