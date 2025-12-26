package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.mapper.PriceReferenceMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.PriceReference;
import com.ruoyi.product.service.IPriceReferenceService;

/**
 * 价格参考Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */

@Service
public class PriceReferenceServiceImpl extends BaseServiceImpl<PriceReferenceMapper, PriceReference>
        implements IPriceReferenceService {
    /**
     * 实现根据 original_price 查询逻辑
     * 使用 getOne 确保返回单个对象
     */
    @Override
    public PriceReference getByOriginalPrice(Integer originalPrice) {
        if (originalPrice == null) {
            return null;
        }
        return this.getOne(new LambdaQueryWrapper<PriceReference>()
                .eq(PriceReference::getOriginalPrice, originalPrice)
                .last("LIMIT 1")); // 保证数据库层面只扫描一行，提升效率
    }

}
