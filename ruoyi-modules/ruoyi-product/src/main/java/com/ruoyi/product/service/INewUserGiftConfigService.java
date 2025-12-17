package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.NewUserGiftConfig;

/**
 * 新用户礼包配置Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface INewUserGiftConfigService 
{
    /**
     * 查询新用户礼包配置
     * 
     * @param id 新用户礼包配置主键
     * @return 新用户礼包配置
     */
    public NewUserGiftConfig selectNewUserGiftConfigById(String id);

    /**
     * 查询新用户礼包配置列表
     * 
     * @param newUserGiftConfig 新用户礼包配置
     * @return 新用户礼包配置集合
     */
    public List<NewUserGiftConfig> selectNewUserGiftConfigList(NewUserGiftConfig newUserGiftConfig);

    /**
     * 新增新用户礼包配置
     * 
     * @param newUserGiftConfig 新用户礼包配置
     * @return 结果
     */
    public int insertNewUserGiftConfig(NewUserGiftConfig newUserGiftConfig);

    /**
     * 修改新用户礼包配置
     * 
     * @param newUserGiftConfig 新用户礼包配置
     * @return 结果
     */
    public int updateNewUserGiftConfig(NewUserGiftConfig newUserGiftConfig);

    /**
     * 批量删除新用户礼包配置
     * 
     * @param ids 需要删除的新用户礼包配置主键集合
     * @return 结果
     */
    public int deleteNewUserGiftConfigByIds(String[] ids);

    /**
     * 删除新用户礼包配置信息
     * 
     * @param id 新用户礼包配置主键
     * @return 结果
     */
    public int deleteNewUserGiftConfigById(String id);
}
