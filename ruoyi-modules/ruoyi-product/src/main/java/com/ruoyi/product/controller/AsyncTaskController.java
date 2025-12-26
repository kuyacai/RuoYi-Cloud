package com.ruoyi.product.controller;

import java.util.List;
import java.util.Arrays;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.domain.AsyncTask;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 异步任务Controller
 * * @author Rupert
 * @date 2025-12-16
 */
@RestController
@RequestMapping("/asynctask") // 修正了拼写错误
public class AsyncTaskController extends BaseController
{
    @Autowired
    private IAsyncTaskService asyncTaskService;

    /**
     * 查询异步任务列表
     */
    @RequiresPermissions("product:asynctask:list")
    @GetMapping("/list")
    public TableDataInfo list(AsyncTask asyncTask)
    {
        startPage(); // 依然使用 RuoYi 的 PageHelper 分页
        
        // 使用 LambdaQueryWrapper 构造查询条件
        LambdaQueryWrapper<AsyncTask> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(asyncTask.getTaskName()), AsyncTask::getTaskName, asyncTask.getTaskName())
           .eq(StringUtils.hasText(asyncTask.getTaskStatus()), AsyncTask::getTaskStatus, asyncTask.getTaskStatus())
           .orderByDesc(AsyncTask::getGmtCreate); // 使用您的 gmtCreate 字段
        
        List<AsyncTask> list = asyncTaskService.list(lqw); // list 是 IService 提供的原生方法
        return getDataTable(list);
    }

    /**
     * 导出异步任务列表
     */
    @RequiresPermissions("product:asynctask:export")
    @Log(title = "异步任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AsyncTask asyncTask)
    {
        LambdaQueryWrapper<AsyncTask> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.hasText(asyncTask.getTaskName()), AsyncTask::getTaskName, asyncTask.getTaskName())
           .eq(StringUtils.hasText(asyncTask.getTaskStatus()), AsyncTask::getTaskStatus, asyncTask.getTaskStatus());
        
        List<AsyncTask> list = asyncTaskService.list(lqw);
        ExcelUtil<AsyncTask> util = new ExcelUtil<AsyncTask>(AsyncTask.class);
        util.exportExcel(response, list, "异步任务数据");
    }

    /**
     * 获取异步任务详细信息
     */
    @RequiresPermissions("product:asynctask:query")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") String taskId)
    {
        // 使用 MP 原生的 getById
        return success(asyncTaskService.getById(taskId));
    }

    /**
     * 新增异步任务
     * 注意：由于采用了 IdType.INPUT，实际业务中通常由 createTask 处理
     */
    @RequiresPermissions("product:asynctask:add")
    @Log(title = "异步任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AsyncTask asyncTask)
    {
        // 使用 MP 原生的 save
        return toAjax(asyncTaskService.save(asyncTask));
    }

    /**
     * 修改异步任务
     */
    @RequiresPermissions("product:asynctask:edit")
    @Log(title = "异步任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AsyncTask asyncTask)
    {
        // 使用 MP 原生的 updateById
        return toAjax(asyncTaskService.updateById(asyncTask));
    }

    /**
     * 删除异步任务
     */
    @RequiresPermissions("product:asynctask:remove")
    @Log(title = "异步任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable String[] taskIds)
    {
        // 使用 MP 原生的 removeByIds，接收集合参数
        return toAjax(asyncTaskService.removeByIds(Arrays.asList(taskIds)));
    }
}