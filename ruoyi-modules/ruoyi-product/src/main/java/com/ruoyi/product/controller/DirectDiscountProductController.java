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
import com.ruoyi.product.domain.DirectDiscountProduct;
import com.ruoyi.product.service.IDirectDiscountProductService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 单品直降活动商品Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/product")
public class DirectDiscountProductController extends BaseController
{
    @Autowired
    private IDirectDiscountProductService directDiscountProductService;

    /**
     * 查询单品直降活动商品列表
     */
    @RequiresPermissions("product:product:list")
    @GetMapping("/list")
    public TableDataInfo list(DirectDiscountProduct directDiscountProduct)
    {
        startPage();
        List<DirectDiscountProduct> list = directDiscountProductService.selectDirectDiscountProductList(directDiscountProduct);
        return getDataTable(list);
    }

    /**
     * 导出单品直降活动商品列表
     */
    @RequiresPermissions("product:product:export")
    @Log(title = "单品直降活动商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DirectDiscountProduct directDiscountProduct)
    {
        List<DirectDiscountProduct> list = directDiscountProductService.selectDirectDiscountProductList(directDiscountProduct);
        ExcelUtil<DirectDiscountProduct> util = new ExcelUtil<DirectDiscountProduct>(DirectDiscountProduct.class);
        util.exportExcel(response, list, "单品直降活动商品数据");
    }

    /**
     * 获取单品直降活动商品详细信息
     */
    @RequiresPermissions("product:product:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(directDiscountProductService.selectDirectDiscountProductById(id));
    }

    /**
     * 新增单品直降活动商品
     */
    @RequiresPermissions("product:product:add")
    @Log(title = "单品直降活动商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DirectDiscountProduct directDiscountProduct)
    {
        return toAjax(directDiscountProductService.insertDirectDiscountProduct(directDiscountProduct));
    }

    /**
     * 修改单品直降活动商品
     */
    @RequiresPermissions("product:product:edit")
    @Log(title = "单品直降活动商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DirectDiscountProduct directDiscountProduct)
    {
        return toAjax(directDiscountProductService.updateDirectDiscountProduct(directDiscountProduct));
    }

    /**
     * 删除单品直降活动商品
     */
    @RequiresPermissions("product:product:remove")
    @Log(title = "单品直降活动商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(directDiscountProductService.deleteDirectDiscountProductByIds(ids));
    }
}
