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
import com.ruoyi.product.domain.PlatformPromotionProduct;
import com.ruoyi.product.service.IPlatformPromotionProductService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 平台促销活动商品Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/platformpromotion/activity/product")
public class PlatformPromotionProductController extends BaseController
{
    @Autowired
    private IPlatformPromotionProductService platformPromotionProductService;

    /**
     * 查询平台促销活动商品列表
     */
    @RequiresPermissions("product:product:list")
    @GetMapping("/list")
    public TableDataInfo list(PlatformPromotionProduct platformPromotionProduct)
    {
        startPage();
        List<PlatformPromotionProduct> list = platformPromotionProductService.selectPlatformPromotionProductList(platformPromotionProduct);
        return getDataTable(list);
    }

    /**
     * 导出平台促销活动商品列表
     */
    @RequiresPermissions("product:product:export")
    @Log(title = "平台促销活动商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformPromotionProduct platformPromotionProduct)
    {
        List<PlatformPromotionProduct> list = platformPromotionProductService.selectPlatformPromotionProductList(platformPromotionProduct);
        ExcelUtil<PlatformPromotionProduct> util = new ExcelUtil<PlatformPromotionProduct>(PlatformPromotionProduct.class);
        util.exportExcel(response, list, "平台促销活动商品数据");
    }

    /**
     * 获取平台促销活动商品详细信息
     */
    @RequiresPermissions("product:product:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(platformPromotionProductService.selectPlatformPromotionProductById(id));
    }

    /**
     * 新增平台促销活动商品
     */
    @RequiresPermissions("product:product:add")
    @Log(title = "平台促销活动商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformPromotionProduct platformPromotionProduct)
    {
        return toAjax(platformPromotionProductService.insertPlatformPromotionProduct(platformPromotionProduct));
    }

    /**
     * 修改平台促销活动商品
     */
    @RequiresPermissions("product:product:edit")
    @Log(title = "平台促销活动商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformPromotionProduct platformPromotionProduct)
    {
        return toAjax(platformPromotionProductService.updatePlatformPromotionProduct(platformPromotionProduct));
    }

    /**
     * 删除平台促销活动商品
     */
    @RequiresPermissions("product:product:remove")
    @Log(title = "平台促销活动商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(platformPromotionProductService.deletePlatformPromotionProductByIds(ids));
    }
}
