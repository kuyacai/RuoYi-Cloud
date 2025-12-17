package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.NewUserGiftConfig;

/**
 * 新用户礼包配置Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface NewUserGiftConfigMapper 
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
     * 删除新用户礼包配置
     * 
     * @param id 新用户礼包配置主键
     * @return 结果
     */
    public int deleteNewUserGiftConfigById(String id);

    /**
     * 批量删除新用户礼包配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNewUserGiftConfigByIds(String[] ids);
}
