package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.PlatformPromotionConfig;

/**
 * 平台促销配置Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface PlatformPromotionConfigMapper 
{
    /**
     * 查询平台促销配置
     * 
     * @param id 平台促销配置主键
     * @return 平台促销配置
     */
    public PlatformPromotionConfig selectPlatformPromotionConfigById(String id);

    /**
     * 查询平台促销配置列表
     * 
     * @param platformPromotionConfig 平台促销配置
     * @return 平台促销配置集合
     */
    public List<PlatformPromotionConfig> selectPlatformPromotionConfigList(PlatformPromotionConfig platformPromotionConfig);

    /**
     * 新增平台促销配置
     * 
     * @param platformPromotionConfig 平台促销配置
     * @return 结果
     */
    public int insertPlatformPromotionConfig(PlatformPromotionConfig platformPromotionConfig);

    /**
     * 修改平台促销配置
     * 
     * @param platformPromotionConfig 平台促销配置
     * @return 结果
     */
    public int updatePlatformPromotionConfig(PlatformPromotionConfig platformPromotionConfig);

    /**
     * 删除平台促销配置
     * 
     * @param id 平台促销配置主键
     * @return 结果
     */
    public int deletePlatformPromotionConfigById(String id);

    /**
     * 批量删除平台促销配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformPromotionConfigByIds(String[] ids);
}
