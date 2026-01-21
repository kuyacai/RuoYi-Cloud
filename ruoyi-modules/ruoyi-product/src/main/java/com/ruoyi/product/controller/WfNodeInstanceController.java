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
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.WfNodeInstance;
import com.ruoyi.product.service.IWfNodeInstanceService;
import com.ruoyi.product.service.IWorkflowEngineService;

import lombok.RequiredArgsConstructor;

/**
 * 工作流任务节点实例 Controller
 * * @author Rupert
 * 
 * @date 2026-01-21
 */
@RestController
@RequestMapping("/instance") // 建议与前端 instance.js 中的 url 保持一致
@RequiredArgsConstructor
public class WfNodeInstanceController extends BaseController {

    private final IWfNodeInstanceService nodeInstanceService;
    private final IWorkflowEngineService workflowEngineService;

    /**
     * 分页查询工作流任务节点实例列表
     */
    @RequiresPermissions("product:instance:list")
    @GetMapping("/list")
    public TableDataInfo list(WfNodeInstance query) {
        startPage();
        // 使用 MyBatis Plus 的 LambdaQueryWrapper
        LambdaQueryWrapper<WfNodeInstance> lqw = new LambdaQueryWrapper<WfNodeInstance>()
                .eq(query.getWorkflowInstanceId() != null, WfNodeInstance::getWorkflowInstanceId,
                        query.getWorkflowInstanceId())
                .eq(query.getStatus() != null, WfNodeInstance::getStatus, query.getStatus())
                .orderByAsc(WfNodeInstance::getNodeOrder);

        List<WfNodeInstance> list = nodeInstanceService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 获取节点详细信息
     */
    @RequiresPermissions("product:instance:query")
    @GetMapping(value = "/{taskNodeId}")
    public AjaxResult getInfo(@PathVariable("taskNodeId") String taskNodeId) {
        return AjaxResult.success(nodeInstanceService.getById(taskNodeId));
    }

    /**
     * 重试指定节点 [核心功能：配合前端 handleRetry]
     */
    @RequiresPermissions("product:instance:edit")
    @Log(title = "工作流引擎", businessType = BusinessType.UPDATE)
    @PostMapping("/retry/{taskNodeId}")
    public AjaxResult retry(@PathVariable String taskNodeId) {
        // 调用我们之前实现的 executeNode 方法，重新下发任务到 MQ
        workflowEngineService.executeNode(taskNodeId);
        return AjaxResult.success("重试指令已下发");
    }

    /**
     * 新增工作流任务节点 (手动触发时可能用到)
     */
    @RequiresPermissions("product:instance:add")
    @Log(title = "工作流实例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfNodeInstance nodeInstance) {
        return toAjax(nodeInstanceService.save(nodeInstance));
    }

    /**
     * 修改工作流任务节点 (手动回填或修正参数)
     */
    @RequiresPermissions("product:instance:edit")
    @Log(title = "工作流实例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfNodeInstance nodeInstance) {
        return toAjax(nodeInstanceService.updateById(nodeInstance));
    }

    /**
     * 删除工作流任务节点
     */
    @RequiresPermissions("product:instance:remove")
    @Log(title = "工作流实例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskNodeIds}")
    public AjaxResult remove(@PathVariable String[] taskNodeIds) {
        return toAjax(nodeInstanceService.removeByIds(Arrays.asList(taskNodeIds)));
    }
}