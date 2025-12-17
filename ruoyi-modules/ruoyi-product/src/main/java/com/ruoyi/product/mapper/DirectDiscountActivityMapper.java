package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.DirectDiscountActivity;

/**
 * 单品直降活动Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface DirectDiscountActivityMapper 
{
    /**
     * 查询单品直降活动
     * 
     * @param activityId 单品直降活动主键
     * @return 单品直降活动
     */
    public DirectDiscountActivity selectDirectDiscountActivityByActivityId(String activityId);

    /**
     * 查询单品直降活动列表
     * 
     * @param directDiscountActivity 单品直降活动
     * @return 单品直降活动集合
     */
    public List<DirectDiscountActivity> selectDirectDiscountActivityList(DirectDiscountActivity directDiscountActivity);

    /**
     * 新增单品直降活动
     * 
     * @param directDiscountActivity 单品直降活动
     * @return 结果
     */
    public int insertDirectDiscountActivity(DirectDiscountActivity directDiscountActivity);

    /**
     * 修改单品直降活动
     * 
     * @param directDiscountActivity 单品直降活动
     * @return 结果
     */
    public int updateDirectDiscountActivity(DirectDiscountActivity directDiscountActivity);

    /**
     * 删除单品直降活动
     * 
     * @param activityId 单品直降活动主键
     * @return 结果
     */
    public int deleteDirectDiscountActivityByActivityId(String activityId);

    /**
     * 批量删除单品直降活动
     * 
     * @param activityIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDirectDiscountActivityByActivityIds(String[] activityIds);
}
