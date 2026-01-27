package com.ruoyi.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.product.domain.WfWorkflowInstance;
import com.ruoyi.product.service.IWfWorkflowInstanceService;

/**
 * 工作流执行实例Controller
 * * @author Rupert
 * 
 * @date 2026-01-24
 */
@RestController
@RequestMapping("/instance")
public class WfWorkflowInstanceController extends BaseController {

    @Autowired
    private IWfWorkflowInstanceService wfWorkflowInstanceService;

    /**
     * 查询工作流实例列表
     */
    @RequiresPermissions("product:instance:list")
    @GetMapping("/list")
    public TableDataInfo list(WfWorkflowInstance wfWorkflowInstance) {
        startPage();
        // 修改点：不再直接调用 list(lqw)，而是调用 service 包装后的查询方法
        List<WfWorkflowInstance> list = wfWorkflowInstanceService.selectWfWorkflowInstanceList(wfWorkflowInstance);
        return getDataTable(list);
    }

    /**
     * 获取工作流实例详细信息
     */
    @RequiresPermissions("product:instance:query")
    @GetMapping(value = "/{instanceId}")
    public AjaxResult getInfo(@PathVariable("instanceId") String instanceId) {
        return success(wfWorkflowInstanceService.getById(instanceId));
    }

    /**
     * 启动工作流实例 (核心入口)
     * * @param definitionId 流程定义ID
     * 
     * @param variables 初始启动参数 (JSON对象)
     */
    @Log(title = "启动工作流", businessType = BusinessType.INSERT)
    @RequiresPermissions("product:instance:add")
    @PostMapping("/start/{definitionId}")
    public AjaxResult start(@PathVariable String definitionId, @RequestBody Map<String, Object> variables) {
        // 获取当前操作人姓名
        String creator = SecurityUtils.getUsername();

        try {
            // 调用 Service 启动流程，内部会进行 Definition 校验并初始化 RuntimeContext
            String instanceId = wfWorkflowInstanceService.startWorkflow(definitionId, variables, creator);
            return AjaxResult.success("工作流已成功启动", instanceId);
        } catch (IllegalArgumentException e) {
            // 捕获业务逻辑校验失败（如流程被禁用）
            return error(e.getMessage());
        } catch (Exception e) {
            return error("流程启动失败：" + e.getMessage());
        }
    }

    /**
     * 删除工作流实例
     */
    @RequiresPermissions("product:instance:remove")
    @Log(title = "工作流实例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{instanceIds}")
    public AjaxResult remove(@PathVariable String[] instanceIds) {
        return toAjax(wfWorkflowInstanceService.removeByIds(Arrays.asList(instanceIds)));
    }

    /**
     * 重试某个节点
     */
    @Log(title = "重试节点", businessType = BusinessType.UPDATE)
    @RequiresPermissions("product:instance:edit")
    @PutMapping("/retry/{nodeInstanceId}")
    public AjaxResult retry(@PathVariable String nodeInstanceId) {
        wfWorkflowInstanceService.retryNode(nodeInstanceId);
        return success("重试指令已下发");
    }
}