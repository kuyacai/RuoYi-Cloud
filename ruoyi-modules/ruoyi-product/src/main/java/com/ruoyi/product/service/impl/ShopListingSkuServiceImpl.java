package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.product.mapper.DirectDiscountActivityMapper;
import com.ruoyi.product.mapper.ShopListingSkuMapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.DirectDiscountActivity;
import com.ruoyi.product.domain.ShopListingSku;
import com.ruoyi.product.service.IShopListingSkuService;

/**
 * 店铺商品SKU同步状态Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class ShopListingSkuServiceImpl extends BaseServiceImpl<ShopListingSkuMapper, ShopListingSku> implements IShopListingSkuService 
{
}
