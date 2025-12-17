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
import com.ruoyi.product.domain.ShopListingSpu;
import com.ruoyi.product.service.IShopListingSpuService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 店铺商品同步状态Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/shoplist/spu")
public class ShopListingSpuController extends BaseController
{
    @Autowired
    private IShopListingSpuService shopListingSpuService;

    /**
     * 查询店铺商品同步状态列表
     */
    @RequiresPermissions("product:spu:list")
    @GetMapping("/list")
    public TableDataInfo list(ShopListingSpu shopListingSpu)
    {
        startPage();
        List<ShopListingSpu> list = shopListingSpuService.selectShopListingSpuList(shopListingSpu);
        return getDataTable(list);
    }

    /**
     * 导出店铺商品同步状态列表
     */
    @RequiresPermissions("product:spu:export")
    @Log(title = "店铺商品同步状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopListingSpu shopListingSpu)
    {
        List<ShopListingSpu> list = shopListingSpuService.selectShopListingSpuList(shopListingSpu);
        ExcelUtil<ShopListingSpu> util = new ExcelUtil<ShopListingSpu>(ShopListingSpu.class);
        util.exportExcel(response, list, "店铺商品同步状态数据");
    }

    /**
     * 获取店铺商品同步状态详细信息
     */
    @RequiresPermissions("product:spu:query")
    @GetMapping(value = "/{listingSpuId}")
    public AjaxResult getInfo(@PathVariable("listingSpuId") String listingSpuId)
    {
        return success(shopListingSpuService.selectShopListingSpuByListingSpuId(listingSpuId));
    }

    /**
     * 新增店铺商品同步状态
     */
    @RequiresPermissions("product:spu:add")
    @Log(title = "店铺商品同步状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopListingSpu shopListingSpu)
    {
        return toAjax(shopListingSpuService.insertShopListingSpu(shopListingSpu));
    }

    /**
     * 修改店铺商品同步状态
     */
    @RequiresPermissions("product:spu:edit")
    @Log(title = "店铺商品同步状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopListingSpu shopListingSpu)
    {
        return toAjax(shopListingSpuService.updateShopListingSpu(shopListingSpu));
    }

    /**
     * 删除店铺商品同步状态
     */
    @RequiresPermissions("product:spu:remove")
    @Log(title = "店铺商品同步状态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{listingSpuIds}")
    public AjaxResult remove(@PathVariable String[] listingSpuIds)
    {
        return toAjax(shopListingSpuService.deleteShopListingSpuByListingSpuIds(listingSpuIds));
    }
}
