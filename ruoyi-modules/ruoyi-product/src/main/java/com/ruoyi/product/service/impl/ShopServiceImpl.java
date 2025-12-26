package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.mapper.ShopMapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.Shop;
import com.ruoyi.product.service.IShopService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 店铺Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl extends BaseServiceImpl<ShopMapper, Shop>  implements IShopService 
{

    
}
