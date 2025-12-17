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
import com.ruoyi.product.domain.ProductDiscountActivity;
import com.ruoyi.product.service.IProductDiscountActivityService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品优惠活动Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/activity/productdiscount")
public class ProductDiscountActivityController extends BaseController
{
    @Autowired
    private IProductDiscountActivityService productDiscountActivityService;

    /**
     * 查询商品优惠活动列表
     */
    @RequiresPermissions("product:activity:list")
    @GetMapping("/list")
    public TableDataInfo list(ProductDiscountActivity productDiscountActivity)
    {
        startPage();
        List<ProductDiscountActivity> list = productDiscountActivityService.selectProductDiscountActivityList(productDiscountActivity);
        return getDataTable(list);
    }

    /**
     * 导出商品优惠活动列表
     */
    @RequiresPermissions("product:activity:export")
    @Log(title = "商品优惠活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductDiscountActivity productDiscountActivity)
    {
        List<ProductDiscountActivity> list = productDiscountActivityService.selectProductDiscountActivityList(productDiscountActivity);
        ExcelUtil<ProductDiscountActivity> util = new ExcelUtil<ProductDiscountActivity>(ProductDiscountActivity.class);
        util.exportExcel(response, list, "商品优惠活动数据");
    }

    /**
     * 获取商品优惠活动详细信息
     */
    @RequiresPermissions("product:activity:query")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") String activityId)
    {
        return success(productDiscountActivityService.selectProductDiscountActivityByActivityId(activityId));
    }

    /**
     * 新增商品优惠活动
     */
    @RequiresPermissions("product:activity:add")
    @Log(title = "商品优惠活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductDiscountActivity productDiscountActivity)
    {
        return toAjax(productDiscountActivityService.insertProductDiscountActivity(productDiscountActivity));
    }

    /**
     * 修改商品优惠活动
     */
    @RequiresPermissions("product:activity:edit")
    @Log(title = "商品优惠活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductDiscountActivity productDiscountActivity)
    {
        return toAjax(productDiscountActivityService.updateProductDiscountActivity(productDiscountActivity));
    }

    /**
     * 删除商品优惠活动
     */
    @RequiresPermissions("product:activity:remove")
    @Log(title = "商品优惠活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable String[] activityIds)
    {
        return toAjax(productDiscountActivityService.deleteProductDiscountActivityByActivityIds(activityIds));
    }
}
