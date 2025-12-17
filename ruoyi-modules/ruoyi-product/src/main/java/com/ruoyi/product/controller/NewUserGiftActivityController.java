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
import com.ruoyi.product.domain.NewUserGiftActivity;
import com.ruoyi.product.service.INewUserGiftActivityService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 新用户礼包活动Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/activity/newusergift")
public class NewUserGiftActivityController extends BaseController
{
    @Autowired
    private INewUserGiftActivityService newUserGiftActivityService;

    /**
     * 查询新用户礼包活动列表
     */
    @RequiresPermissions("product:activity:list")
    @GetMapping("/list")
    public TableDataInfo list(NewUserGiftActivity newUserGiftActivity)
    {
        startPage();
        List<NewUserGiftActivity> list = newUserGiftActivityService.selectNewUserGiftActivityList(newUserGiftActivity);
        return getDataTable(list);
    }

    /**
     * 导出新用户礼包活动列表
     */
    @RequiresPermissions("product:activity:export")
    @Log(title = "新用户礼包活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewUserGiftActivity newUserGiftActivity)
    {
        List<NewUserGiftActivity> list = newUserGiftActivityService.selectNewUserGiftActivityList(newUserGiftActivity);
        ExcelUtil<NewUserGiftActivity> util = new ExcelUtil<NewUserGiftActivity>(NewUserGiftActivity.class);
        util.exportExcel(response, list, "新用户礼包活动数据");
    }

    /**
     * 获取新用户礼包活动详细信息
     */
    @RequiresPermissions("product:activity:query")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") String activityId)
    {
        return success(newUserGiftActivityService.selectNewUserGiftActivityByActivityId(activityId));
    }

    /**
     * 新增新用户礼包活动
     */
    @RequiresPermissions("product:activity:add")
    @Log(title = "新用户礼包活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewUserGiftActivity newUserGiftActivity)
    {
        return toAjax(newUserGiftActivityService.insertNewUserGiftActivity(newUserGiftActivity));
    }

    /**
     * 修改新用户礼包活动
     */
    @RequiresPermissions("product:activity:edit")
    @Log(title = "新用户礼包活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewUserGiftActivity newUserGiftActivity)
    {
        return toAjax(newUserGiftActivityService.updateNewUserGiftActivity(newUserGiftActivity));
    }

    /**
     * 删除新用户礼包活动
     */
    @RequiresPermissions("product:activity:remove")
    @Log(title = "新用户礼包活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable String[] activityIds)
    {
        return toAjax(newUserGiftActivityService.deleteNewUserGiftActivityByActivityIds(activityIds));
    }
}
