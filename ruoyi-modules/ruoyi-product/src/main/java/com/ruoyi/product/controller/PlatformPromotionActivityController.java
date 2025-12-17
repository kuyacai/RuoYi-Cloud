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
import com.ruoyi.product.domain.PlatformPromotionActivity;
import com.ruoyi.product.service.IPlatformPromotionActivityService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 平台促销活动Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/activity/platformpromotion")
public class PlatformPromotionActivityController extends BaseController
{
    @Autowired
    private IPlatformPromotionActivityService platformPromotionActivityService;

    /**
     * 查询平台促销活动列表
     */
    @RequiresPermissions("product:activity:list")
    @GetMapping("/list")
    public TableDataInfo list(PlatformPromotionActivity platformPromotionActivity)
    {
        startPage();
        List<PlatformPromotionActivity> list = platformPromotionActivityService.selectPlatformPromotionActivityList(platformPromotionActivity);
        return getDataTable(list);
    }

    /**
     * 导出平台促销活动列表
     */
    @RequiresPermissions("product:activity:export")
    @Log(title = "平台促销活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformPromotionActivity platformPromotionActivity)
    {
        List<PlatformPromotionActivity> list = platformPromotionActivityService.selectPlatformPromotionActivityList(platformPromotionActivity);
        ExcelUtil<PlatformPromotionActivity> util = new ExcelUtil<PlatformPromotionActivity>(PlatformPromotionActivity.class);
        util.exportExcel(response, list, "平台促销活动数据");
    }

    /**
     * 获取平台促销活动详细信息
     */
    @RequiresPermissions("product:activity:query")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") String activityId)
    {
        return success(platformPromotionActivityService.selectPlatformPromotionActivityByActivityId(activityId));
    }

    /**
     * 新增平台促销活动
     */
    @RequiresPermissions("product:activity:add")
    @Log(title = "平台促销活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformPromotionActivity platformPromotionActivity)
    {
        return toAjax(platformPromotionActivityService.insertPlatformPromotionActivity(platformPromotionActivity));
    }

    /**
     * 修改平台促销活动
     */
    @RequiresPermissions("product:activity:edit")
    @Log(title = "平台促销活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformPromotionActivity platformPromotionActivity)
    {
        return toAjax(platformPromotionActivityService.updatePlatformPromotionActivity(platformPromotionActivity));
    }

    /**
     * 删除平台促销活动
     */
    @RequiresPermissions("product:activity:remove")
    @Log(title = "平台促销活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable String[] activityIds)
    {
        return toAjax(platformPromotionActivityService.deletePlatformPromotionActivityByActivityIds(activityIds));
    }
}
