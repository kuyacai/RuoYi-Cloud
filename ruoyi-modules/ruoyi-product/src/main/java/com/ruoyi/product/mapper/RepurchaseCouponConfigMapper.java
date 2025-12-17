package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.RepurchaseCouponConfig;

/**
 * 复购券配置Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface RepurchaseCouponConfigMapper 
{
    /**
     * 查询复购券配置
     * 
     * @param id 复购券配置主键
     * @return 复购券配置
     */
    public RepurchaseCouponConfig selectRepurchaseCouponConfigById(String id);

    /**
     * 查询复购券配置列表
     * 
     * @param repurchaseCouponConfig 复购券配置
     * @return 复购券配置集合
     */
    public List<RepurchaseCouponConfig> selectRepurchaseCouponConfigList(RepurchaseCouponConfig repurchaseCouponConfig);

    /**
     * 新增复购券配置
     * 
     * @param repurchaseCouponConfig 复购券配置
     * @return 结果
     */
    public int insertRepurchaseCouponConfig(RepurchaseCouponConfig repurchaseCouponConfig);

    /**
     * 修改复购券配置
     * 
     * @param repurchaseCouponConfig 复购券配置
     * @return 结果
     */
    public int updateRepurchaseCouponConfig(RepurchaseCouponConfig repurchaseCouponConfig);

    /**
     * 删除复购券配置
     * 
     * @param id 复购券配置主键
     * @return 结果
     */
    public int deleteRepurchaseCouponConfigById(String id);

    /**
     * 批量删除复购券配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepurchaseCouponConfigByIds(String[] ids);
}
