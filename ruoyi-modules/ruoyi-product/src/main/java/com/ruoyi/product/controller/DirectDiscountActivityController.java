package com.ruoyi.product.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.DirectDiscountActivity;
import com.ruoyi.product.service.IDirectDiscountActivityService;
import com.ruoyi.product.utils.EnhancedExcelUtil;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 单品直降活动Controller
 * * @author Rupert
 * 
 * @date 2025-12-26
 */
@RestController
@RequestMapping("/promotion/singlediscountactivity")
public class DirectDiscountActivityController extends BaseController {
    @Autowired
    private IDirectDiscountActivityService directDiscountActivityService;

    /**
     * 查询单品直降活动列表
     * 修改点：使用 LambdaQueryWrapper 实现 activityName, shopId, platformActivityId 过滤
     */
    @RequiresPermissions("product:activity:list")
    @GetMapping("/list")
    public TableDataInfo list(DirectDiscountActivity activity) {
        startPage();
        List<DirectDiscountActivity> list = directDiscountActivityService
                .list(new LambdaQueryWrapper<DirectDiscountActivity>()
                        .like(StringUtils.isNotBlank(activity.getActivityName()),
                                DirectDiscountActivity::getActivityName, activity.getActivityName())
                        .eq(StringUtils.isNotBlank(activity.getShopId()), DirectDiscountActivity::getShopId,
                                activity.getShopId())
                        .eq(StringUtils.isNotBlank(activity.getPlatformActivityId()),
                                DirectDiscountActivity::getPlatformActivityId, activity.getPlatformActivityId())
                        .orderByDesc(DirectDiscountActivity::getGmtCreate)); // 默认按创建时间倒序
        return getDataTable(list);
    }

    /**
     * 导出单品直降活动列表
     */
    @RequiresPermissions("product:activity:export")
    @Log(title = "单品直降活动商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void exportSingleDiscountProduct(HttpServletResponse response,
            @RequestParam(value = "activityId", required = false) String activityId) {

        List<DirectDiscountActivity> list = new ArrayList<>();

        EnhancedExcelUtil<DirectDiscountActivity> util = new EnhancedExcelUtil<>(DirectDiscountActivity.class);
        util.exportExcel(response, list, "单品直降活动数据");
    }

    /**
     * 获取单品直降活动详细信息
     */
    @RequiresPermissions("product:activity:query")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") String activityId) {
        return success(directDiscountActivityService.getById(activityId));
    }

    /**
     * 新增单品直降活动
     */
    @RequiresPermissions("product:activity:add")
    @Log(title = "单品直降活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DirectDiscountActivity directDiscountActivity) {
        return toAjax(directDiscountActivityService.save(directDiscountActivity));
    }

    /**
     * 修改单品直降活动
     */
    @RequiresPermissions("product:activity:edit")
    @Log(title = "单品直降活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DirectDiscountActivity directDiscountActivity) {
        return toAjax(directDiscountActivityService.updateById(directDiscountActivity));
    }

    /**
     * 删除单品直降活动
     */
    @RequiresPermissions("product:activity:remove")
    @Log(title = "单品直降活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable String[] activityIds) {
        return toAjax(directDiscountActivityService.removeByIds(Arrays.asList(activityIds)));
    }
}