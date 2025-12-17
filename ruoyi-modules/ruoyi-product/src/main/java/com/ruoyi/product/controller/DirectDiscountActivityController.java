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
import com.ruoyi.product.domain.DirectDiscountActivity;
import com.ruoyi.product.service.IDirectDiscountActivityService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 单品直降活动Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/activity/discount")
public class DirectDiscountActivityController extends BaseController
{
    @Autowired
    private IDirectDiscountActivityService directDiscountActivityService;

    /**
     * 查询单品直降活动列表
     */
    @RequiresPermissions("product:activity:list")
    @GetMapping("/list")
    public TableDataInfo list(DirectDiscountActivity directDiscountActivity)
    {
        startPage();
        List<DirectDiscountActivity> list = directDiscountActivityService.selectDirectDiscountActivityList(directDiscountActivity);
        return getDataTable(list);
    }

    /**
     * 导出单品直降活动列表
     */
    @RequiresPermissions("product:activity:export")
    @Log(title = "单品直降活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DirectDiscountActivity directDiscountActivity)
    {
        List<DirectDiscountActivity> list = directDiscountActivityService.selectDirectDiscountActivityList(directDiscountActivity);
        ExcelUtil<DirectDiscountActivity> util = new ExcelUtil<DirectDiscountActivity>(DirectDiscountActivity.class);
        util.exportExcel(response, list, "单品直降活动数据");
    }

    /**
     * 获取单品直降活动详细信息
     */
    @RequiresPermissions("product:activity:query")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") String activityId)
    {
        return success(directDiscountActivityService.selectDirectDiscountActivityByActivityId(activityId));
    }

    /**
     * 新增单品直降活动
     */
    @RequiresPermissions("product:activity:add")
    @Log(title = "单品直降活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DirectDiscountActivity directDiscountActivity)
    {
        return toAjax(directDiscountActivityService.insertDirectDiscountActivity(directDiscountActivity));
    }

    /**
     * 修改单品直降活动
     */
    @RequiresPermissions("product:activity:edit")
    @Log(title = "单品直降活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DirectDiscountActivity directDiscountActivity)
    {
        return toAjax(directDiscountActivityService.updateDirectDiscountActivity(directDiscountActivity));
    }

    /**
     * 删除单品直降活动
     */
    @RequiresPermissions("product:activity:remove")
    @Log(title = "单品直降活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable String[] activityIds)
    {
        return toAjax(directDiscountActivityService.deleteDirectDiscountActivityByActivityIds(activityIds));
    }
}
