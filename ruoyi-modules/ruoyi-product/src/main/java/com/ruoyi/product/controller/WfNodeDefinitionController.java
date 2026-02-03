package com.ruoyi.product.controller;

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
import com.ruoyi.common.core.utils.uuid.UUID;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.WfNodeCapability;
import com.ruoyi.product.domain.WfNodeDefinition;
import com.ruoyi.product.service.IWfNodeCapabilityService;
import com.ruoyi.product.service.IWfNodeDefinitionService;

import lombok.RequiredArgsConstructor;

/**
 * 工作流节点定义 Controller
 */
@RestController
@RequestMapping("/node-definition")
@RequiredArgsConstructor
public class WfNodeDefinitionController extends BaseController {

    private final IWfNodeDefinitionService wfNodeDefinitionService;
    private final IWfNodeCapabilityService capabilityService; // 需要注入能力仓库的服务

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
     * 新增节点定义
     */
    @RequiresPermissions("product:definition:add")
    @Log(title = "节点定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfNodeDefinition wfNodeDefinition) {
        // 1. 校验必要参数
        if (StringUtils.isEmpty(wfNodeDefinition.getCapabilityId())) {
            return AjaxResult.error("关联能力不能为空");
        }

        // 2. 根据能力仓库补全 handlerType 和 默认 manualStatus
        WfNodeCapability capability = capabilityService.getById(wfNodeDefinition.getCapabilityVersionId());
        if (capability == null) {
            return AjaxResult.error("关联的能力算子不存在");
        }

        // 设置执行器类型（从能力带入）
        wfNodeDefinition.setHandlerType(capability.getHandlerType());

        // 如果前端没有显式指定 manualStatus，则继承能力算子的默认设置
        if (wfNodeDefinition.getManualStatus() == null) {
            wfNodeDefinition.setManualStatus(capability.getManualStatus());
        }

        // 3. 处理 ID 和 排序
        if (StringUtils.isEmpty(wfNodeDefinition.getNodeDefId())) {
            wfNodeDefinition.setNodeDefId(UUID.fastUUID().toString(true));
        }

        if (wfNodeDefinition.getNodeOrder() == null) {
            long count = wfNodeDefinitionService.count(new LambdaQueryWrapper<WfNodeDefinition>()
                    .eq(WfNodeDefinition::getDefinitionId, wfNodeDefinition.getDefinitionId()));
            wfNodeDefinition.setNodeOrder((int) count + 1);
        }

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
    /**
     * 删除节点（带自动重排）
     */
    @RequiresPermissions("product:definition:remove")
    @Log(title = "节点定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{nodeDefId}/{definitionId}")
    public AjaxResult remove(@PathVariable String nodeDefId, @PathVariable String definitionId) {
        return toAjax(wfNodeDefinitionService.removeAndResort(nodeDefId, definitionId));
    }
}