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
import com.ruoyi.product.domain.PricingMultiplier;
import com.ruoyi.product.service.IPricingMultiplierService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 定价倍数Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/pricemultiplier/config")
public class PricingMultiplierController extends BaseController
{
    @Autowired
    private IPricingMultiplierService pricingMultiplierService;

    /**
     * 查询定价倍数列表
     */
    @RequiresPermissions("product:multiplier:list")
    @GetMapping("/list")
    public TableDataInfo list(PricingMultiplier pricingMultiplier)
    {
        startPage();
        List<PricingMultiplier> list = pricingMultiplierService.selectPricingMultiplierList(pricingMultiplier);
        return getDataTable(list);
    }

    /**
     * 导出定价倍数列表
     */
    @RequiresPermissions("product:multiplier:export")
    @Log(title = "定价倍数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PricingMultiplier pricingMultiplier)
    {
        List<PricingMultiplier> list = pricingMultiplierService.selectPricingMultiplierList(pricingMultiplier);
        ExcelUtil<PricingMultiplier> util = new ExcelUtil<PricingMultiplier>(PricingMultiplier.class);
        util.exportExcel(response, list, "定价倍数数据");
    }

    /**
     * 获取定价倍数详细信息
     */
    @RequiresPermissions("product:multiplier:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(pricingMultiplierService.selectPricingMultiplierById(id));
    }

    /**
     * 新增定价倍数
     */
    @RequiresPermissions("product:multiplier:add")
    @Log(title = "定价倍数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PricingMultiplier pricingMultiplier)
    {
        return toAjax(pricingMultiplierService.insertPricingMultiplier(pricingMultiplier));
    }

    /**
     * 修改定价倍数
     */
    @RequiresPermissions("product:multiplier:edit")
    @Log(title = "定价倍数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PricingMultiplier pricingMultiplier)
    {
        return toAjax(pricingMultiplierService.updatePricingMultiplier(pricingMultiplier));
    }

    /**
     * 删除定价倍数
     */
    @RequiresPermissions("product:multiplier:remove")
    @Log(title = "定价倍数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(pricingMultiplierService.deletePricingMultiplierByIds(ids));
    }
}
