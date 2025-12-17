package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.NewUserGiftConfigMapper;
import com.ruoyi.product.domain.NewUserGiftConfig;
import com.ruoyi.product.service.INewUserGiftConfigService;

/**
 * 新用户礼包配置Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class NewUserGiftConfigServiceImpl implements INewUserGiftConfigService 
{
    @Autowired
    private NewUserGiftConfigMapper newUserGiftConfigMapper;

    /**
     * 查询新用户礼包配置
     * 
     * @param id 新用户礼包配置主键
     * @return 新用户礼包配置
     */
    @Override
    public NewUserGiftConfig selectNewUserGiftConfigById(String id)
    {
        return newUserGiftConfigMapper.selectNewUserGiftConfigById(id);
    }

    /**
     * 查询新用户礼包配置列表
     * 
     * @param newUserGiftConfig 新用户礼包配置
     * @return 新用户礼包配置
     */
    @Override
    public List<NewUserGiftConfig> selectNewUserGiftConfigList(NewUserGiftConfig newUserGiftConfig)
    {
        return newUserGiftConfigMapper.selectNewUserGiftConfigList(newUserGiftConfig);
    }

    /**
     * 新增新用户礼包配置
     * 
     * @param newUserGiftConfig 新用户礼包配置
     * @return 结果
     */
    @Override
    public int insertNewUserGiftConfig(NewUserGiftConfig newUserGiftConfig)
    {
        return newUserGiftConfigMapper.insertNewUserGiftConfig(newUserGiftConfig);
    }

    /**
     * 修改新用户礼包配置
     * 
     * @param newUserGiftConfig 新用户礼包配置
     * @return 结果
     */
    @Override
    public int updateNewUserGiftConfig(NewUserGiftConfig newUserGiftConfig)
    {
        return newUserGiftConfigMapper.updateNewUserGiftConfig(newUserGiftConfig);
    }

    /**
     * 批量删除新用户礼包配置
     * 
     * @param ids 需要删除的新用户礼包配置主键
     * @return 结果
     */
    @Override
    public int deleteNewUserGiftConfigByIds(String[] ids)
    {
        return newUserGiftConfigMapper.deleteNewUserGiftConfigByIds(ids);
    }

    /**
     * 删除新用户礼包配置信息
     * 
     * @param id 新用户礼包配置主键
     * @return 结果
     */
    @Override
    public int deleteNewUserGiftConfigById(String id)
    {
        return newUserGiftConfigMapper.deleteNewUserGiftConfigById(id);
    }
}
