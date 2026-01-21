package com.ruoyi.product.controller;

import java.util.Arrays;
import java.util.List;

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
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.WfNodeDefinition;
import com.ruoyi.product.service.IWfNodeDefinitionService;

import lombok.RequiredArgsConstructor;

/**
 * 工作流节点定义 Controller
 */
@RestController
@RequestMapping("/definition")
@RequiredArgsConstructor
public class WfNodeDefinitionController extends BaseController {

    private final IWfNodeDefinitionService wfNodeDefinitionService;

    /**
     * 查询节点定义列表
     */
    @RequiresPermissions("product:definition:list")
    @GetMapping("/list")
    public TableDataInfo list(WfNodeDefinition query) {
        startPage();
        LambdaQueryWrapper<WfNodeDefinition> lqw = new LambdaQueryWrapper<WfNodeDefinition>()
                // capabilityId 和 definitionId 均为 String，使用 isNotBlank
                .eq(StringUtils.isNotBlank(query.getDefinitionId()), WfNodeDefinition::getDefinitionId,
                        query.getDefinitionId())
                .eq(StringUtils.isNotBlank(query.getCapabilityId()), WfNodeDefinition::getCapabilityId,
                        query.getCapabilityId())
                // 节点定义通常需要按照顺序排列，方便前端展示
                .orderByAsc(WfNodeDefinition::getNodeOrder);

        List<WfNodeDefinition> list = wfNodeDefinitionService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 获取详细信息
     */
    @RequiresPermissions("product:definition:query")
    @GetMapping(value = "/{nodeDefId}")
    public AjaxResult getInfo(@PathVariable("nodeDefId") String nodeDefId) {
        return AjaxResult.success(wfNodeDefinitionService.getById(nodeDefId));
    }

    /**
     * 新增
     */
    @RequiresPermissions("product:definition:add")
    @Log(title = "节点定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfNodeDefinition wfNodeDefinition) {
        return toAjax(wfNodeDefinitionService.save(wfNodeDefinition));
    }

    /**
     * 修改
     */
    @RequiresPermissions("product:definition:edit")
    @Log(title = "节点定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfNodeDefinition wfNodeDefinition) {
        return toAjax(wfNodeDefinitionService.updateById(wfNodeDefinition));
    }

    /**
     * 删除
     */
    @RequiresPermissions("product:definition:remove")
    @Log(title = "节点定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{nodeDefIds}")
    public AjaxResult remove(@PathVariable String[] nodeDefIds) {
        return toAjax(wfNodeDefinitionService.removeByIds(Arrays.asList(nodeDefIds)));
    }
}