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
import com.ruoyi.product.domain.RepurchaseCouponActivity;
import com.ruoyi.product.service.IRepurchaseCouponActivityService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 复购券活动Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/activity/repurchase")
public class RepurchaseCouponActivityController extends BaseController
{
    @Autowired
    private IRepurchaseCouponActivityService repurchaseCouponActivityService;

    /**
     * 查询复购券活动列表
     */
    @RequiresPermissions("product:activity:list")
    @GetMapping("/list")
    public TableDataInfo list(RepurchaseCouponActivity repurchaseCouponActivity)
    {
        startPage();
        List<RepurchaseCouponActivity> list = repurchaseCouponActivityService.selectRepurchaseCouponActivityList(repurchaseCouponActivity);
        return getDataTable(list);
    }

    /**
     * 导出复购券活动列表
     */
    @RequiresPermissions("product:activity:export")
    @Log(title = "复购券活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepurchaseCouponActivity repurchaseCouponActivity)
    {
        List<RepurchaseCouponActivity> list = repurchaseCouponActivityService.selectRepurchaseCouponActivityList(repurchaseCouponActivity);
        ExcelUtil<RepurchaseCouponActivity> util = new ExcelUtil<RepurchaseCouponActivity>(RepurchaseCouponActivity.class);
        util.exportExcel(response, list, "复购券活动数据");
    }

    /**
     * 获取复购券活动详细信息
     */
    @RequiresPermissions("product:activity:query")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") String activityId)
    {
        return success(repurchaseCouponActivityService.selectRepurchaseCouponActivityByActivityId(activityId));
    }

    /**
     * 新增复购券活动
     */
    @RequiresPermissions("product:activity:add")
    @Log(title = "复购券活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepurchaseCouponActivity repurchaseCouponActivity)
    {
        return toAjax(repurchaseCouponActivityService.insertRepurchaseCouponActivity(repurchaseCouponActivity));
    }

    /**
     * 修改复购券活动
     */
    @RequiresPermissions("product:activity:edit")
    @Log(title = "复购券活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepurchaseCouponActivity repurchaseCouponActivity)
    {
        return toAjax(repurchaseCouponActivityService.updateRepurchaseCouponActivity(repurchaseCouponActivity));
    }

    /**
     * 删除复购券活动
     */
    @RequiresPermissions("product:activity:remove")
    @Log(title = "复购券活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable String[] activityIds)
    {
        return toAjax(repurchaseCouponActivityService.deleteRepurchaseCouponActivityByActivityIds(activityIds));
    }
}
