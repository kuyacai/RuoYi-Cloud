package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.DirectDiscountActivityMapper;
import com.ruoyi.product.domain.DirectDiscountActivity;
import com.ruoyi.product.service.IDirectDiscountActivityService;

/**
 * 单品直降活动Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class DirectDiscountActivityServiceImpl implements IDirectDiscountActivityService 
{
    @Autowired
    private DirectDiscountActivityMapper directDiscountActivityMapper;

    /**
     * 查询单品直降活动
     * 
     * @param activityId 单品直降活动主键
     * @return 单品直降活动
     */
    @Override
    public DirectDiscountActivity selectDirectDiscountActivityByActivityId(String activityId)
    {
        return directDiscountActivityMapper.selectDirectDiscountActivityByActivityId(activityId);
    }

    /**
     * 查询单品直降活动列表
     * 
     * @param directDiscountActivity 单品直降活动
     * @return 单品直降活动
     */
    @Override
    public List<DirectDiscountActivity> selectDirectDiscountActivityList(DirectDiscountActivity directDiscountActivity)
    {
        return directDiscountActivityMapper.selectDirectDiscountActivityList(directDiscountActivity);
    }

    /**
     * 新增单品直降活动
     * 
     * @param directDiscountActivity 单品直降活动
     * @return 结果
     */
    @Override
    public int insertDirectDiscountActivity(DirectDiscountActivity directDiscountActivity)
    {
        return directDiscountActivityMapper.insertDirectDiscountActivity(directDiscountActivity);
    }

    /**
     * 修改单品直降活动
     * 
     * @param directDiscountActivity 单品直降活动
     * @return 结果
     */
    @Override
    public int updateDirectDiscountActivity(DirectDiscountActivity directDiscountActivity)
    {
        return directDiscountActivityMapper.updateDirectDiscountActivity(directDiscountActivity);
    }

    /**
     * 批量删除单品直降活动
     * 
     * @param activityIds 需要删除的单品直降活动主键
     * @return 结果
     */
    @Override
    public int deleteDirectDiscountActivityByActivityIds(String[] activityIds)
    {
        return directDiscountActivityMapper.deleteDirectDiscountActivityByActivityIds(activityIds);
    }

    /**
     * 删除单品直降活动信息
     * 
     * @param activityId 单品直降活动主键
     * @return 结果
     */
    @Override
    public int deleteDirectDiscountActivityByActivityId(String activityId)
    {
        return directDiscountActivityMapper.deleteDirectDiscountActivityByActivityId(activityId);
    }
}
