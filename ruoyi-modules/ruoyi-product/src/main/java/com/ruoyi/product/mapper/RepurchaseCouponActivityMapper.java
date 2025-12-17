package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.RepurchaseCouponActivity;

/**
 * 复购券活动Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface RepurchaseCouponActivityMapper 
{
    /**
     * 查询复购券活动
     * 
     * @param activityId 复购券活动主键
     * @return 复购券活动
     */
    public RepurchaseCouponActivity selectRepurchaseCouponActivityByActivityId(String activityId);

    /**
     * 查询复购券活动列表
     * 
     * @param repurchaseCouponActivity 复购券活动
     * @return 复购券活动集合
     */
    public List<RepurchaseCouponActivity> selectRepurchaseCouponActivityList(RepurchaseCouponActivity repurchaseCouponActivity);

    /**
     * 新增复购券活动
     * 
     * @param repurchaseCouponActivity 复购券活动
     * @return 结果
     */
    public int insertRepurchaseCouponActivity(RepurchaseCouponActivity repurchaseCouponActivity);

    /**
     * 修改复购券活动
     * 
     * @param repurchaseCouponActivity 复购券活动
     * @return 结果
     */
    public int updateRepurchaseCouponActivity(RepurchaseCouponActivity repurchaseCouponActivity);

    /**
     * 删除复购券活动
     * 
     * @param activityId 复购券活动主键
     * @return 结果
     */
    public int deleteRepurchaseCouponActivityByActivityId(String activityId);

    /**
     * 批量删除复购券活动
     * 
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepurchaseCouponActivityByActivityIds(String[] activityIds);
}
