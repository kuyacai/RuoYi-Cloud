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
import com.ruoyi.product.domain.ItemTask;
import com.ruoyi.product.service.IItemTaskService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 任务实例Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/task")
public class ItemTaskController extends BaseController
{
    @Autowired
    private IItemTaskService itemTaskService;

    /**
     * 查询任务实例列表
     */
    @RequiresPermissions("product:task:list")
    @GetMapping("/list")
    public TableDataInfo list(ItemTask itemTask)
    {
        startPage();
        List<ItemTask> list = itemTaskService.selectItemTaskList(itemTask);
        return getDataTable(list);
    }

    /**
     * 导出任务实例列表
     */
    @RequiresPermissions("product:task:export")
    @Log(title = "任务实例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ItemTask itemTask)
    {
        List<ItemTask> list = itemTaskService.selectItemTaskList(itemTask);
        ExcelUtil<ItemTask> util = new ExcelUtil<ItemTask>(ItemTask.class);
        util.exportExcel(response, list, "任务实例数据");
    }

    /**
     * 获取任务实例详细信息
     */
    @RequiresPermissions("product:task:query")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") String taskId)
    {
        return success(itemTaskService.selectItemTaskByTaskId(taskId));
    }

    /**
     * 新增任务实例
     */
    @RequiresPermissions("product:task:add")
    @Log(title = "任务实例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ItemTask itemTask)
    {
        return toAjax(itemTaskService.insertItemTask(itemTask));
    }

    /**
     * 修改任务实例
     */
    @RequiresPermissions("product:task:edit")
    @Log(title = "任务实例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ItemTask itemTask)
    {
        return toAjax(itemTaskService.updateItemTask(itemTask));
    }

    /**
     * 删除任务实例
     */
    @RequiresPermissions("product:task:remove")
    @Log(title = "任务实例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable String[] taskIds)
    {
        return toAjax(itemTaskService.deleteItemTaskByTaskIds(taskIds));
    }
}
