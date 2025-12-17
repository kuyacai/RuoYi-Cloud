package com.ruoyi.product.controller;

import java.util.List;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.ShopListingSku;
import com.ruoyi.product.service.IShopListingSkuService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 店铺商品SKU同步状态Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/shoplist/sku")
public class ShopListingSkuController extends BaseController
{
    @Autowired
    private IShopListingSkuService shopListingSkuService;

    /**
     * 查询店铺商品SKU同步状态列表
     */
    @RequiresPermissions("product:sku:list")
    @GetMapping("/list")
    public TableDataInfo list(ShopListingSku shopListingSku)
    {
        startPage();
        List<ShopListingSku> list = shopListingSkuService.selectShopListingSkuList(shopListingSku);
        return getDataTable(list);
    }

    /**
     * 导出店铺商品SKU同步状态列表
     */
    @RequiresPermissions("product:sku:export")
    @Log(title = "店铺商品SKU同步状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopListingSku shopListingSku)
    {
        List<ShopListingSku> list = shopListingSkuService.selectShopListingSkuList(shopListingSku);
        ExcelUtil<ShopListingSku> util = new ExcelUtil<ShopListingSku>(ShopListingSku.class);
        util.exportExcel(response, list, "店铺商品SKU同步状态数据");
    }

    /**
     * 获取店铺商品SKU同步状态详细信息
     */
    @RequiresPermissions("product:sku:query")
    @GetMapping(value = "/{listingSkuId}")
    public AjaxResult getInfo(@PathVariable("listingSkuId") String listingSkuId)
    {
        return success(shopListingSkuService.selectShopListingSkuByListingSkuId(listingSkuId));
    }

    /**
     * 新增店铺商品SKU同步状态
     */
    @RequiresPermissions("product:sku:add")
    @Log(title = "店铺商品SKU同步状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopListingSku shopListingSku)
    {
        return toAjax(shopListingSkuService.insertShopListingSku(shopListingSku));
    }

    /**
     * 修改店铺商品SKU同步状态
     */
    @RequiresPermissions("product:sku:edit")
    @Log(title = "店铺商品SKU同步状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopListingSku shopListingSku)
    {
        return toAjax(shopListingSkuService.updateShopListingSku(shopListingSku));
    }

    /**
     * 删除店铺商品SKU同步状态
     */
    @RequiresPermissions("product:sku:remove")
    @Log(title = "店铺商品SKU同步状态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{listingSkuIds}")
    public AjaxResult remove(@PathVariable String[] listingSkuIds)
    {
        return toAjax(shopListingSkuService.deleteShopListingSkuByListingSkuIds(listingSkuIds));
    }
}
