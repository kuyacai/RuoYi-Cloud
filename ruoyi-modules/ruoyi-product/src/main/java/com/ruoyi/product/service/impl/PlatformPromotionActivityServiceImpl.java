package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.PlatformPromotionActivityMapper;
import com.ruoyi.product.domain.PlatformPromotionActivity;
import com.ruoyi.product.service.IPlatformPromotionActivityService;

/**
 * 平台促销活动Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class PlatformPromotionActivityServiceImpl implements IPlatformPromotionActivityService 
{
    @Autowired
    private PlatformPromotionActivityMapper platformPromotionActivityMapper;

    /**
     * 查询平台促销活动
     * 
     * @param activityId 平台促销活动主键
     * @return 平台促销活动
     */
    @Override
    public PlatformPromotionActivity selectPlatformPromotionActivityByActivityId(String activityId)
    {
        return platformPromotionActivityMapper.selectPlatformPromotionActivityByActivityId(activityId);
    }

    /**
     * 查询平台促销活动列表
     * 
     * @param platformPromotionActivity 平台促销活动
     * @return 平台促销活动
     */
    @Override
    public List<PlatformPromotionActivity> selectPlatformPromotionActivityList(PlatformPromotionActivity platformPromotionActivity)
    {
        return platformPromotionActivityMapper.selectPlatformPromotionActivityList(platformPromotionActivity);
    }

    /**
     * 新增平台促销活动
     * 
     * @param platformPromotionActivity 平台促销活动
     * @return 结果
     */
    @Override
    public int insertPlatformPromotionActivity(PlatformPromotionActivity platformPromotionActivity)
    {
        return platformPromotionActivityMapper.insertPlatformPromotionActivity(platformPromotionActivity);
    }

    /**
     * 修改平台促销活动
     * 
     * @param platformPromotionActivity 平台促销活动
     * @return 结果
     */
    @Override
    public int updatePlatformPromotionActivity(PlatformPromotionActivity platformPromotionActivity)
    {
        return platformPromotionActivityMapper.updatePlatformPromotionActivity(platformPromotionActivity);
    }

    /**
     * 批量删除平台促销活动
     * 
     * @param activityIds 需要删除的平台促销活动主键
     * @return 结果
     */
    @Override
    public int deletePlatformPromotionActivityByActivityIds(String[] activityIds)
    {
        return platformPromotionActivityMapper.deletePlatformPromotionActivityByActivityIds(activityIds);
    }

    /**
     * 删除平台促销活动信息
     * 
     * @param activityId 平台促销活动主键
     * @return 结果
     */
    @Override
    public int deletePlatformPromotionActivityByActivityId(String activityId)
    {
        return platformPromotionActivityMapper.deletePlatformPromotionActivityByActivityId(activityId);
    }
}
