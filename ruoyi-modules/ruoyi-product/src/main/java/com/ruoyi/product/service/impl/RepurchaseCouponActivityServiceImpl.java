package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.RepurchaseCouponActivityMapper;
import com.ruoyi.product.domain.RepurchaseCouponActivity;
import com.ruoyi.product.service.IRepurchaseCouponActivityService;

/**
 * 复购券活动Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class RepurchaseCouponActivityServiceImpl implements IRepurchaseCouponActivityService 
{
    @Autowired
    private RepurchaseCouponActivityMapper repurchaseCouponActivityMapper;

    /**
     * 查询复购券活动
     * 
     * @param activityId 复购券活动主键
     * @return 复购券活动
     */
    @Override
    public RepurchaseCouponActivity selectRepurchaseCouponActivityByActivityId(String activityId)
    {
        return repurchaseCouponActivityMapper.selectRepurchaseCouponActivityByActivityId(activityId);
    }

    /**
     * 查询复购券活动列表
     * 
     * @param repurchaseCouponActivity 复购券活动
     * @return 复购券活动
     */
    @Override
    public List<RepurchaseCouponActivity> selectRepurchaseCouponActivityList(RepurchaseCouponActivity repurchaseCouponActivity)
    {
        return repurchaseCouponActivityMapper.selectRepurchaseCouponActivityList(repurchaseCouponActivity);
    }

    /**
     * 新增复购券活动
     * 
     * @param repurchaseCouponActivity 复购券活动
     * @return 结果
     */
    @Override
    public int insertRepurchaseCouponActivity(RepurchaseCouponActivity repurchaseCouponActivity)
    {
        return repurchaseCouponActivityMapper.insertRepurchaseCouponActivity(repurchaseCouponActivity);
    }

    /**
     * 修改复购券活动
     * 
     * @param repurchaseCouponActivity 复购券活动
     * @return 结果
     */
    @Override
    public int updateRepurchaseCouponActivity(RepurchaseCouponActivity repurchaseCouponActivity)
    {
        return repurchaseCouponActivityMapper.updateRepurchaseCouponActivity(repurchaseCouponActivity);
    }

    /**
     * 批量删除复购券活动
     * 
     * @param activityIds 需要删除的复购券活动主键
     * @return 结果
     */
    @Override
    public int deleteRepurchaseCouponActivityByActivityIds(String[] activityIds)
    {
        return repurchaseCouponActivityMapper.deleteRepurchaseCouponActivityByActivityIds(activityIds);
    }

    /**
     * 删除复购券活动信息
     * 
     * @param activityId 复购券活动主键
     * @return 结果
     */
    @Override
    public int deleteRepurchaseCouponActivityByActivityId(String activityId)
    {
        return repurchaseCouponActivityMapper.deleteRepurchaseCouponActivityByActivityId(activityId);
    }
}
