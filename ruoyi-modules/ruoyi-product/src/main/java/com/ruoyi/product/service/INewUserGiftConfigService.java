package com.ruoyi.product.service;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.NewUserGiftConfig;

/**
 * 新用户礼包配置Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface INewUserGiftConfigService extends IBaseService<NewUserGiftConfig> {
    /**
     * 根据SKU标价匹配符合条件的配置对象
     * 匹配规则：priceMin <= price < priceMax
     * * @param markedPrice SKU标价
     * 
     * @return 符合条件的配置对象，未匹配到返回 null
     */
    NewUserGiftConfig matchConfigByPrice(Integer marketPrice);
}
