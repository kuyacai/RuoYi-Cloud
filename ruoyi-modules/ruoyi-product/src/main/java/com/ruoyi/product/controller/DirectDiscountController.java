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
import com.ruoyi.product.domain.DirectDiscount;
import com.ruoyi.product.service.IDirectDiscountService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 单品直降配置Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/discount")
public class DirectDiscountController extends BaseController
{
    @Autowired
    private IDirectDiscountService directDiscountService;

    /**
     * 查询单品直降配置列表
     */
    @RequiresPermissions("product:discount:list")
    @GetMapping("/list")
    public TableDataInfo list(DirectDiscount directDiscount)
    {
        startPage();
        List<DirectDiscount> list = directDiscountService.selectDirectDiscountList(directDiscount);
        return getDataTable(list);
    }

    /**
     * 导出单品直降配置列表
     */
    @RequiresPermissions("product:discount:export")
    @Log(title = "单品直降配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DirectDiscount directDiscount)
    {
        List<DirectDiscount> list = directDiscountService.selectDirectDiscountList(directDiscount);
        ExcelUtil<DirectDiscount> util = new ExcelUtil<DirectDiscount>(DirectDiscount.class);
        util.exportExcel(response, list, "单品直降配置数据");
    }

    /**
     * 获取单品直降配置详细信息
     */
    @RequiresPermissions("product:discount:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(directDiscountService.selectDirectDiscountById(id));
    }

    /**
     * 新增单品直降配置
     */
    @RequiresPermissions("product:discount:add")
    @Log(title = "单品直降配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DirectDiscount directDiscount)
    {
        return toAjax(directDiscountService.insertDirectDiscount(directDiscount));
    }

    /**
     * 修改单品直降配置
     */
    @RequiresPermissions("product:discount:edit")
    @Log(title = "单品直降配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DirectDiscount directDiscount)
    {
        return toAjax(directDiscountService.updateDirectDiscount(directDiscount));
    }

    /**
     * 删除单品直降配置
     */
    @RequiresPermissions("product:discount:remove")
    @Log(title = "单品直降配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(directDiscountService.deleteDirectDiscountByIds(ids));
    }
}
