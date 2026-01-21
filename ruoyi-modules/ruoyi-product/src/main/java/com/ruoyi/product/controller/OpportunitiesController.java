package com.ruoyi.product.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.Opportunity;
import com.ruoyi.product.service.IOpportunityService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 商机Controller
 * 
 * @author Rupert
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/opportunities")
public class OpportunitiesController extends BaseController {
    @Autowired
    private IOpportunityService opportunitiesService;

    /**
     * 查询商机列表
     */
    @RequiresPermissions("product:opportunities:list")
    @GetMapping("/list")
    public TableDataInfo list(Opportunity opportunities) {
        startPage();
        List<Opportunity> list = opportunitiesService.list();
        return getDataTable(list);
    }

    /**
     * 导出商机列表
     */
    @RequiresPermissions("product:opportunities:export")
    @Log(title = "商机", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Opportunity opportunities) {
        List<Opportunity> list = opportunitiesService.list();
        ExcelUtil<Opportunity> util = new ExcelUtil<Opportunity>(Opportunity.class);
        util.exportExcel(response, list, "商机数据");
    }

    /**
     * 获取商机详细信息
     */
    @RequiresPermissions("product:opportunities:query")
    @GetMapping(value = "/{clueId}")
    public AjaxResult getInfo(@PathVariable("clueId") String clueId) {
        return success(opportunitiesService.getById(clueId));
    }

    /**
     * 新增商机
     */
    @RequiresPermissions("product:opportunities:add")
    @Log(title = "商机", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Opportunity opportunities) {
        return toAjax(opportunitiesService.save(opportunities));
    }

    /**
     * 修改商机
     */
    @RequiresPermissions("product:opportunities:edit")
    @Log(title = "商机", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Opportunity opportunities) {
        return toAjax(opportunitiesService.updateById(opportunities));
    }

    /**
     * 删除商机
     */
    @RequiresPermissions("product:opportunities:remove")
    @Log(title = "商机", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clueIds}")
    public AjaxResult remove(@PathVariable String[] clueIds) {
        List<String> ids = Arrays.asList(clueIds);

        return toAjax(opportunitiesService.removeByIds(ids));
    }
}
