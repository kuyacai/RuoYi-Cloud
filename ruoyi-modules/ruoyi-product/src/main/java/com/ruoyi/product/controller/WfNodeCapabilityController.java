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
import com.ruoyi.product.domain.WfNodeCapability;
import com.ruoyi.product.service.IWfNodeCapabilityService;

import lombok.RequiredArgsConstructor;

/**
 * 节点能力元数据 Controller
 */
@RestController
@RequestMapping("/capability")
@RequiredArgsConstructor
public class WfNodeCapabilityController extends BaseController {

    private final IWfNodeCapabilityService wfNodeCapabilityService;

    /**
     * 查询节点能力列表
     */
    @RequiresPermissions("product:capability:list")
    @GetMapping("/list")
    public TableDataInfo list(WfNodeCapability query) {
        startPage();
        LambdaQueryWrapper<WfNodeCapability> lqw = new LambdaQueryWrapper<WfNodeCapability>()
                // 1. name 是 String，可以使用 isNotBlank
                .like(StringUtils.isNotBlank(query.getName()), WfNodeCapability::getName, query.getName())

                // 2. handlerType 是枚举，直接判断对象是否为 null
                .eq(query.getHandlerType() != null, WfNodeCapability::getHandlerType, query.getHandlerType())

                // 3. 修正方法名：根据实体类定义，状态字段可能是 getActiveStatus() 或 getStatus()
                // 请检查实体类，如果是 ActiveStatus 枚举，通常字段名是 activeStatus
                .eq(query.getActiveStatus() != null, WfNodeCapability::getActiveStatus, query.getActiveStatus());

        List<WfNodeCapability> list = wfNodeCapabilityService.list(lqw);
        return getDataTable(list);
    }

    /**
     * 获取详细信息
     */
    @RequiresPermissions("product:capability:query")
    @GetMapping(value = "/{capabilityId}")
    public AjaxResult getInfo(@PathVariable("capabilityId") String capabilityId) {
        return AjaxResult.success(wfNodeCapabilityService.getById(capabilityId));
    }

    /**
     * 新增
     */
    @RequiresPermissions("product:capability:add")
    @Log(title = "能力仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfNodeCapability wfNodeCapability) {
        return toAjax(wfNodeCapabilityService.save(wfNodeCapability));
    }

    /**
     * 修改
     */
    @RequiresPermissions("product:capability:edit")
    @Log(title = "能力仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfNodeCapability wfNodeCapability) {
        return toAjax(wfNodeCapabilityService.updateById(wfNodeCapability));
    }

    /**
     * 删除
     */
    @RequiresPermissions("product:capability:remove")
    @Log(title = "能力仓库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{capabilityIds}")
    public AjaxResult remove(@PathVariable String[] capabilityIds) {
        return toAjax(wfNodeCapabilityService.removeByIds(Arrays.asList(capabilityIds)));
    }

    /**
     * 校验能力标识唯一性
     */
    @GetMapping("/checkUnique/{capabilityId}")
    public AjaxResult checkUnique(@PathVariable("capabilityId") String capabilityId) {
        long count = wfNodeCapabilityService.count(
                new LambdaQueryWrapper<WfNodeCapability>()
                        .eq(WfNodeCapability::getCapabilityId, capabilityId));
        // 如果 count > 0，说明已存在，返回 false
        return AjaxResult.success(count == 0);
    }
}