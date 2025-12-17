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
import com.ruoyi.product.domain.PriceReference;
import com.ruoyi.product.service.IPriceReferenceService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 价格参考Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/pricereference/config")
public class PriceReferenceController extends BaseController
{
    @Autowired
    private IPriceReferenceService priceReferenceService;

    /**
     * 查询价格参考列表
     */
    @RequiresPermissions("product:reference:list")
    @GetMapping("/list")
    public TableDataInfo list(PriceReference priceReference)
    {
        startPage();
        List<PriceReference> list = priceReferenceService.selectPriceReferenceList(priceReference);
        return getDataTable(list);
    }

    /**
     * 导出价格参考列表
     */
    @RequiresPermissions("product:reference:export")
    @Log(title = "价格参考", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PriceReference priceReference)
    {
        List<PriceReference> list = priceReferenceService.selectPriceReferenceList(priceReference);
        ExcelUtil<PriceReference> util = new ExcelUtil<PriceReference>(PriceReference.class);
        util.exportExcel(response, list, "价格参考数据");
    }

    /**
     * 获取价格参考详细信息
     */
    @RequiresPermissions("product:reference:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(priceReferenceService.selectPriceReferenceById(id));
    }

    /**
     * 新增价格参考
     */
    @RequiresPermissions("product:reference:add")
    @Log(title = "价格参考", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PriceReference priceReference)
    {
        return toAjax(priceReferenceService.insertPriceReference(priceReference));
    }

    /**
     * 修改价格参考
     */
    @RequiresPermissions("product:reference:edit")
    @Log(title = "价格参考", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PriceReference priceReference)
    {
        return toAjax(priceReferenceService.updatePriceReference(priceReference));
    }

    /**
     * 删除价格参考
     */
    @RequiresPermissions("product:reference:remove")
    @Log(title = "价格参考", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(priceReferenceService.deletePriceReferenceByIds(ids));
    }
}
