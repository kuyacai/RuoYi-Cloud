package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.PlatformPromotionActivity;

/**
 * 平台促销活动Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface PlatformPromotionActivityMapper 
{
    /**
     * 查询平台促销活动
     * 
     * @param activityId 平台促销活动主键
     * @return 平台促销活动
     */
    public PlatformPromotionActivity selectPlatformPromotionActivityByActivityId(String activityId);

    /**
     * 查询平台促销活动列表
     * 
     * @param platformPromotionActivity 平台促销活动
     * @return 平台促销活动集合
     */
    public List<PlatformPromotionActivity> selectPlatformPromotionActivityList(PlatformPromotionActivity platformPromotionActivity);

    /**
     * 新增平台促销活动
     * 
     * @param platformPromotionActivity 平台促销活动
     * @return 结果
     */
    public int insertPlatformPromotionActivity(PlatformPromotionActivity platformPromotionActivity);

    /**
     * 修改平台促销活动
     * 
     * @param platformPromotionActivity 平台促销活动
     * @return 结果
     */
    public int updatePlatformPromotionActivity(PlatformPromotionActivity platformPromotionActivity);

    /**
     * 删除平台促销活动
     * 
     * @param activityId 平台促销活动主键
     * @return 结果
     */
    public int deletePlatformPromotionActivityByActivityId(String activityId);

    /**
     * 批量删除平台促销活动
     * 
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformPromotionActivityByActivityIds(String[] activityIds);
}
