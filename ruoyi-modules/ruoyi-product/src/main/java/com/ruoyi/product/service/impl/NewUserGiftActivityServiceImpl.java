package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.NewUserGiftActivityMapper;
import com.ruoyi.product.domain.NewUserGiftActivity;
import com.ruoyi.product.service.INewUserGiftActivityService;

/**
 * 新用户礼包活动Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class NewUserGiftActivityServiceImpl implements INewUserGiftActivityService 
{
    @Autowired
    private NewUserGiftActivityMapper newUserGiftActivityMapper;

    /**
     * 查询新用户礼包活动
     * 
     * @param activityId 新用户礼包活动主键
     * @return 新用户礼包活动
     */
    @Override
    public NewUserGiftActivity selectNewUserGiftActivityByActivityId(String activityId)
    {
        return newUserGiftActivityMapper.selectNewUserGiftActivityByActivityId(activityId);
    }

    /**
     * 查询新用户礼包活动列表
     * 
     * @param newUserGiftActivity 新用户礼包活动
     * @return 新用户礼包活动
     */
    @Override
    public List<NewUserGiftActivity> selectNewUserGiftActivityList(NewUserGiftActivity newUserGiftActivity)
    {
        return newUserGiftActivityMapper.selectNewUserGiftActivityList(newUserGiftActivity);
    }

    /**
     * 新增新用户礼包活动
     * 
     * @param newUserGiftActivity 新用户礼包活动
     * @return 结果
     */
    @Override
    public int insertNewUserGiftActivity(NewUserGiftActivity newUserGiftActivity)
    {
        return newUserGiftActivityMapper.insertNewUserGiftActivity(newUserGiftActivity);
    }

    /**
     * 修改新用户礼包活动
     * 
     * @param newUserGiftActivity 新用户礼包活动
     * @return 结果
     */
    @Override
    public int updateNewUserGiftActivity(NewUserGiftActivity newUserGiftActivity)
    {
        return newUserGiftActivityMapper.updateNewUserGiftActivity(newUserGiftActivity);
    }

    /**
     * 批量删除新用户礼包活动
     * 
     * @param activityIds 需要删除的新用户礼包活动主键
     * @return 结果
     */
    @Override
    public int deleteNewUserGiftActivityByActivityIds(String[] activityIds)
    {
        return newUserGiftActivityMapper.deleteNewUserGiftActivityByActivityIds(activityIds);
    }

    /**
     * 删除新用户礼包活动信息
     * 
     * @param activityId 新用户礼包活动主键
     * @return 结果
     */
    @Override
    public int deleteNewUserGiftActivityByActivityId(String activityId)
    {
        return newUserGiftActivityMapper.deleteNewUserGiftActivityByActivityId(activityId);
    }
}
