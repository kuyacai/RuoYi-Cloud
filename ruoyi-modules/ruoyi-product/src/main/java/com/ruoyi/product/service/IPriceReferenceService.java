package com.ruoyi.product.service;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.PriceReference;

/**
 * 价格参考Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IPriceReferenceService extends IBaseService<PriceReference> {
    /**
     * 根据原价获取价格参考信息
     * * @param originalPrice 原价（单位：分）
     * 
     * @return 价格参考实体，不存在则返回 null
     */
    PriceReference getByOriginalPrice(Long originalPrice);
}
