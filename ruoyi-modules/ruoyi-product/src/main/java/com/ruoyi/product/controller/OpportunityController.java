package com.ruoyi.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.Opportunity;
import com.ruoyi.product.service.IOpportunityService;

@RestController
@RequestMapping("/opportunity")
public class OpportunityController extends BaseController {

    @Autowired
    private IOpportunityService opportunityService;

    /**
     * 分页列表（带关联数据）
     */
    @GetMapping("/list")
    public TableDataInfo list(Opportunity opportunity) {
        startPage();

        // 1. 先分页查询机会数据
        List<String> clueIds = opportunityService.selectOpportunityPageIds(opportunity);

        // 2. 批量查询关联数据
        // 2. 直接使用批量查询方法获取完整数据
        List<Opportunity> list = opportunityService.selectOpportunitiesWithDetails(clueIds);

        return getDataTable(list);
    }

    /**
     * 单个详情
     */
    @GetMapping("/detail/{id}")
    public AjaxResult detail(@PathVariable String clueId) {
        Opportunity opportunity = opportunityService.getOpportunityWithDetails(clueId);
        return AjaxResult.success(opportunity);
    }

    /**
     * 删除商机（级联删除关联数据）
     */
    @RequiresPermissions("opportunity:opportunity:remove")
    @Log(title = "商机管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clueId}")
    public AjaxResult remove(@PathVariable String clueId) {
        boolean flag = opportunityService.deleteOpportunityWithRelated(clueId);
        if (flag) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }

    }

    /**
     * 批量删除商机（级联删除）
     */
    @RequiresPermissions("opportunity:opportunity:remove")
    @Log(title = "商机管理", businessType = BusinessType.DELETE)
    @PostMapping("/batchRemove")
    public AjaxResult batchRemove(@RequestBody List<String> clueIds) {
        boolean flag = opportunityService.batchDeleteOpportunityWithRelated(clueIds);
        if (flag) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }

    }
}