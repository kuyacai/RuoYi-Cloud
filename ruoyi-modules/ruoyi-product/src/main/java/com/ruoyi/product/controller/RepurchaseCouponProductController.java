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
import com.ruoyi.product.domain.RepurchaseCouponProduct;
import com.ruoyi.product.service.IRepurchaseCouponProductService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 复购券活动商品Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/repurchasecoupon/activity/product")
public class RepurchaseCouponProductController extends BaseController
{
    @Autowired
    private IRepurchaseCouponProductService repurchaseCouponProductService;

    /**
     * 查询复购券活动商品列表
     */
    @RequiresPermissions("product:product:list")
    @GetMapping("/list")
    public TableDataInfo list(RepurchaseCouponProduct repurchaseCouponProduct)
    {
        startPage();
        List<RepurchaseCouponProduct> list = repurchaseCouponProductService.selectRepurchaseCouponProductList(repurchaseCouponProduct);
        return getDataTable(list);
    }

    /**
     * 导出复购券活动商品列表
     */
    @RequiresPermissions("product:product:export")
    @Log(title = "复购券活动商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepurchaseCouponProduct repurchaseCouponProduct)
    {
        List<RepurchaseCouponProduct> list = repurchaseCouponProductService.selectRepurchaseCouponProductList(repurchaseCouponProduct);
        ExcelUtil<RepurchaseCouponProduct> util = new ExcelUtil<RepurchaseCouponProduct>(RepurchaseCouponProduct.class);
        util.exportExcel(response, list, "复购券活动商品数据");
    }

    /**
     * 获取复购券活动商品详细信息
     */
    @RequiresPermissions("product:product:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(repurchaseCouponProductService.selectRepurchaseCouponProductById(id));
    }

    /**
     * 新增复购券活动商品
     */
    @RequiresPermissions("product:product:add")
    @Log(title = "复购券活动商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepurchaseCouponProduct repurchaseCouponProduct)
    {
        return toAjax(repurchaseCouponProductService.insertRepurchaseCouponProduct(repurchaseCouponProduct));
    }

    /**
     * 修改复购券活动商品
     */
    @RequiresPermissions("product:product:edit")
    @Log(title = "复购券活动商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepurchaseCouponProduct repurchaseCouponProduct)
    {
        return toAjax(repurchaseCouponProductService.updateRepurchaseCouponProduct(repurchaseCouponProduct));
    }

    /**
     * 删除复购券活动商品
     */
    @RequiresPermissions("product:product:remove")
    @Log(title = "复购券活动商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(repurchaseCouponProductService.deleteRepurchaseCouponProductByIds(ids));
    }
}
