package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.NewUserGiftConfig;
import com.ruoyi.product.enums.ConfigStatus;
import com.ruoyi.product.mapper.NewUserGiftConfigMapper;
import com.ruoyi.product.service.INewUserGiftConfigService;

/**
 * 新用户礼包配置Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class NewUserGiftConfigServiceImpl extends BaseServiceImpl<NewUserGiftConfigMapper, NewUserGiftConfig>
        implements INewUserGiftConfigService {

    @Override
    public NewUserGiftConfig matchConfigByPrice(Long marketPrice) {
        if (marketPrice == null) {
            return null;
        }

        // 构造查询条件：price_min <= marketPrice AND price_max > marketPrice
        // 且通常需要保证配置状态是开启的 (configStatus = '0' 假设 '0' 为正常状态)
        LambdaQueryWrapper<NewUserGiftConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.le(NewUserGiftConfig::getPriceMin, marketPrice)
                .gt(NewUserGiftConfig::getPriceMax, marketPrice)
                .eq(NewUserGiftConfig::getConfigStatus, ConfigStatus.ENABLE.getCode())
                .last("LIMIT 1"); // 理论上区间不重叠，取一条即可

        return this.getOne(queryWrapper);
    }

}
