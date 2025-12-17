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
import com.ruoyi.product.domain.ProductDiscountConfig;
import com.ruoyi.product.service.IProductDiscountConfigService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品优惠配置Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/productdiscount/config")
public class ProductDiscountConfigController extends BaseController
{
    @Autowired
    private IProductDiscountConfigService productDiscountConfigService;

    /**
     * 查询商品优惠配置列表
     */
    @RequiresPermissions("product:config:list")
    @GetMapping("/list")
    public TableDataInfo list(ProductDiscountConfig productDiscountConfig)
    {
        startPage();
        List<ProductDiscountConfig> list = productDiscountConfigService.selectProductDiscountConfigList(productDiscountConfig);
        return getDataTable(list);
    }

    /**
     * 导出商品优惠配置列表
     */
    @RequiresPermissions("product:config:export")
    @Log(title = "商品优惠配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductDiscountConfig productDiscountConfig)
    {
        List<ProductDiscountConfig> list = productDiscountConfigService.selectProductDiscountConfigList(productDiscountConfig);
        ExcelUtil<ProductDiscountConfig> util = new ExcelUtil<ProductDiscountConfig>(ProductDiscountConfig.class);
        util.exportExcel(response, list, "商品优惠配置数据");
    }

    /**
     * 获取商品优惠配置详细信息
     */
    @RequiresPermissions("product:config:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(productDiscountConfigService.selectProductDiscountConfigById(id));
    }

    /**
     * 新增商品优惠配置
     */
    @RequiresPermissions("product:config:add")
    @Log(title = "商品优惠配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductDiscountConfig productDiscountConfig)
    {
        return toAjax(productDiscountConfigService.insertProductDiscountConfig(productDiscountConfig));
    }

    /**
     * 修改商品优惠配置
     */
    @RequiresPermissions("product:config:edit")
    @Log(title = "商品优惠配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductDiscountConfig productDiscountConfig)
    {
        return toAjax(productDiscountConfigService.updateProductDiscountConfig(productDiscountConfig));
    }

    /**
     * 删除商品优惠配置
     */
    @RequiresPermissions("product:config:remove")
    @Log(title = "商品优惠配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(productDiscountConfigService.deleteProductDiscountConfigByIds(ids));
    }
}
