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
import com.ruoyi.product.domain.ProductDiscountProduct;
import com.ruoyi.product.service.IProductDiscountProductService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品优惠活动商品Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/productdiscount/activity/product")
public class ProductDiscountProductController extends BaseController
{
    @Autowired
    private IProductDiscountProductService productDiscountProductService;

    /**
     * 查询商品优惠活动商品列表
     */
    @RequiresPermissions("product:product:list")
    @GetMapping("/list")
    public TableDataInfo list(ProductDiscountProduct productDiscountProduct)
    {
        startPage();
        List<ProductDiscountProduct> list = productDiscountProductService.selectProductDiscountProductList(productDiscountProduct);
        return getDataTable(list);
    }

    /**
     * 导出商品优惠活动商品列表
     */
    @RequiresPermissions("product:product:export")
    @Log(title = "商品优惠活动商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductDiscountProduct productDiscountProduct)
    {
        List<ProductDiscountProduct> list = productDiscountProductService.selectProductDiscountProductList(productDiscountProduct);
        ExcelUtil<ProductDiscountProduct> util = new ExcelUtil<ProductDiscountProduct>(ProductDiscountProduct.class);
        util.exportExcel(response, list, "商品优惠活动商品数据");
    }

    /**
     * 获取商品优惠活动商品详细信息
     */
    @RequiresPermissions("product:product:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(productDiscountProductService.selectProductDiscountProductById(id));
    }

    /**
     * 新增商品优惠活动商品
     */
    @RequiresPermissions("product:product:add")
    @Log(title = "商品优惠活动商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductDiscountProduct productDiscountProduct)
    {
        return toAjax(productDiscountProductService.insertProductDiscountProduct(productDiscountProduct));
    }

    /**
     * 修改商品优惠活动商品
     */
    @RequiresPermissions("product:product:edit")
    @Log(title = "商品优惠活动商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductDiscountProduct productDiscountProduct)
    {
        return toAjax(productDiscountProductService.updateProductDiscountProduct(productDiscountProduct));
    }

    /**
     * 删除商品优惠活动商品
     */
    @RequiresPermissions("product:product:remove")
    @Log(title = "商品优惠活动商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(productDiscountProductService.deleteProductDiscountProductByIds(ids));
    }
}
