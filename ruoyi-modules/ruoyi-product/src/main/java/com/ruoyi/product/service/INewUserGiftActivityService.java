package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.NewUserGiftActivity;

/**
 * 新用户礼包活动Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface INewUserGiftActivityService 
{
    /**
     * 查询新用户礼包活动
     * 
     * @param activityId 新用户礼包活动主键
     * @return 新用户礼包活动
     */
    public NewUserGiftActivity selectNewUserGiftActivityByActivityId(String activityId);

    /**
     * 查询新用户礼包活动列表
     * 
     * @param newUserGiftActivity 新用户礼包活动
     * @return 新用户礼包活动集合
     */
    public List<NewUserGiftActivity> selectNewUserGiftActivityList(NewUserGiftActivity newUserGiftActivity);

    /**
     * 新增新用户礼包活动
     * 
     * @param newUserGiftActivity 新用户礼包活动
     * @return 结果
     */
    public int insertNewUserGiftActivity(NewUserGiftActivity newUserGiftActivity);

    /**
     * 修改新用户礼包活动
     * 
     * @param newUserGiftActivity 新用户礼包活动
     * @return 结果
     */
    public int updateNewUserGiftActivity(NewUserGiftActivity newUserGiftActivity);

    /**
     * 批量删除新用户礼包活动
     * 
     * @param activityIds 需要删除的新用户礼包活动主键集合
     * @return 结果
     */
    public int deleteNewUserGiftActivityByActivityIds(String[] activityIds);

    /**
     * 删除新用户礼包活动信息
     * 
     * @param activityId 新用户礼包活动主键
     * @return 结果
     */
    public int deleteNewUserGiftActivityByActivityId(String activityId);
}
