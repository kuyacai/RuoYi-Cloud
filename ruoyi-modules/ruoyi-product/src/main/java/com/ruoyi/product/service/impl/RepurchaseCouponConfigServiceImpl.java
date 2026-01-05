package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.RepurchaseCouponConfig;
import com.ruoyi.product.enums.ConfigStatus;
import com.ruoyi.product.mapper.RepurchaseCouponConfigMapper;
import com.ruoyi.product.service.IRepurchaseCouponConfigService;

/**
 * 复购券配置Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class RepurchaseCouponConfigServiceImpl
        extends BaseServiceImpl<RepurchaseCouponConfigMapper, RepurchaseCouponConfig>
        implements IRepurchaseCouponConfigService {
    @Override
    public RepurchaseCouponConfig matchConfigByPrice(Long markedPrice) {
        if (markedPrice == null)
            return null;
        return this.getOne(new LambdaQueryWrapper<RepurchaseCouponConfig>()
                .le(RepurchaseCouponConfig::getPriceMin, markedPrice)
                .gt(RepurchaseCouponConfig::getPriceMax, markedPrice)
                .eq(RepurchaseCouponConfig::getConfigStatus, ConfigStatus.ENABLE)
                .last("LIMIT 1"));
    }
}
