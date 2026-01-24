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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.WfWorkflowDefinition;
import com.ruoyi.product.service.IWfWorkflowDefinitionService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 工作流定义主Controller
 * * @author Rupert
 * 
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/definition") // 建议加上模块前缀以符合规范
public class WfWorkflowDefinitionController extends BaseController {
    @Autowired
    private IWfWorkflowDefinitionService wfWorkflowDefinitionService;

    /**
     * 查询工作流定义主列表
     */
    @RequiresPermissions("product:definition:list")
    @GetMapping("/list")
    public TableDataInfo list(WfWorkflowDefinition wfWorkflowDefinition) {
        startPage();
        // 使用 MyBatis Plus 的 LambdaQueryWrapper 构建查询条件
        LambdaQueryWrapper<WfWorkflowDefinition> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotBlank(wfWorkflowDefinition.getName()), WfWorkflowDefinition::getName,
                wfWorkflowDefinition.getName());
        lqw.eq(StringUtils.isNotBlank(wfWorkflowDefinition.getBusinessTag()), WfWorkflowDefinition::getBusinessTag,
                wfWorkflowDefinition.getBusinessTag());
        lqw.orderByDesc(WfWorkflowDefinition::getCreatedAtUtc);

        List<WfWorkflowDefinition> list = wfWorkflowDefinitionService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 导出工作流定义主列表
     */
    @RequiresPermissions("product:definition:export")
    @Log(title = "工作流定义主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WfWorkflowDefinition wfWorkflowDefinition) {
        LambdaQueryWrapper<WfWorkflowDefinition> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotBlank(wfWorkflowDefinition.getName()), WfWorkflowDefinition::getName,
                wfWorkflowDefinition.getName());
        lqw.eq(StringUtils.isNotBlank(wfWorkflowDefinition.getBusinessTag()), WfWorkflowDefinition::getBusinessTag,
                wfWorkflowDefinition.getBusinessTag());

        List<WfWorkflowDefinition> list = wfWorkflowDefinitionService.list(lqw);
        ExcelUtil<WfWorkflowDefinition> util = new ExcelUtil<>(WfWorkflowDefinition.class);
        util.exportExcel(response, list, "工作流定义主数据");
    }

    /**
     * 获取工作流定义主详细信息
     */
    @RequiresPermissions("product:definition:query")
    @GetMapping(value = "/{definitionId}")
    public AjaxResult getInfo(@PathVariable("definitionId") String definitionId) {
        // MyBatis Plus 的 getById
        return success(wfWorkflowDefinitionService.getById(definitionId));
    }

    /**
     * 新增工作流定义主
     */
    @RequiresPermissions("product:definition:add")
    @Log(title = "工作流定义主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfWorkflowDefinition wfWorkflowDefinition) {
        // MyBatis Plus 的 save。注意：UUID 主键建议在数据库层或 Entity 层面处理（如 IdType.ASSIGN_UUID）
        wfWorkflowDefinition.setDefinitionId(UUID.fastUUID().toString(true));
        return toAjax(wfWorkflowDefinitionService.save(wfWorkflowDefinition));
    }

    /**
     * 修改工作流定义主
     */
    @RequiresPermissions("product:definition:edit")
    @Log(title = "工作流定义主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfWorkflowDefinition wfWorkflowDefinition) {
        // MyBatis Plus 的 updateById
        return toAjax(wfWorkflowDefinitionService.updateById(wfWorkflowDefinition));
    }

    /**
     * 删除工作流定义主
     */
    @RequiresPermissions("product:definition:remove")
    @Log(title = "工作流定义主", businessType = BusinessType.DELETE)
    @DeleteMapping("/{definitionIds}")
    public AjaxResult remove(@PathVariable String[] definitionIds) {
        // MyBatis Plus 的 removeByIds
        return toAjax(wfWorkflowDefinitionService.removeByIds(Arrays.asList(definitionIds)));
    }
}