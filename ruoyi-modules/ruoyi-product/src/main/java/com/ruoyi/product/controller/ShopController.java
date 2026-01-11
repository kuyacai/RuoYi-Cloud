package com.ruoyi.product.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.Shop;
import com.ruoyi.product.service.IShopService;

@RestController
@RequestMapping("/shop")
public class ShopController extends BaseController {
    @Autowired
    private IShopService shopService;

    /**
     * 查询店铺列表 (含倒序排序实现)
     */
    @RequiresPermissions("product:shop:list")
    @GetMapping("/list")
    public TableDataInfo list(Shop shop) {
        startPage(); // 依然配合 PageHelper 使用

        // 1. 构造查询条件
        LambdaQueryWrapper<Shop> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(shop.getShopName()), Shop::getShopName, shop.getShopName())
                .eq(StringUtils.hasText(shop.getShopStatus()), Shop::getShopStatus, shop.getShopStatus());

        // 2. 实现排序：根据创建时间倒序排序 (假设字段名为 gmtCreate)
        lqw.orderByDesc(Shop::getCreatedAtUtc);

        // 3. 执行查询
        List<Shop> list = shopService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 获取店铺详细信息
     */
    @RequiresPermissions("product:shop:query")
    @GetMapping(value = "/{shopId}")
    public AjaxResult getInfo(@PathVariable("shopId") String shopId) {
        return success(shopService.getById(shopId));
    }

    /**
     * 新增店铺
     */
    @RequiresPermissions("product:shop:add")
    @Log(title = "店铺", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Shop shop) {
        return toAjax(shopService.save(shop));
    }

    /**
     * 修改店铺
     */
    @RequiresPermissions("product:shop:edit")
    @Log(title = "店铺", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Shop shop) {
        return toAjax(shopService.updateById(shop));
    }

    /**
     * 删除店铺
     */
    @RequiresPermissions("product:shop:remove")
    @Log(title = "店铺", businessType = BusinessType.DELETE)
    @DeleteMapping("/{shopIds}")
    public AjaxResult remove(@PathVariable String[] shopIds) {
        return toAjax(shopService.removeByIds(Arrays.asList(shopIds)));
    }
}