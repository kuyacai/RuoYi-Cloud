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
import com.ruoyi.product.domain.NewUserGiftConfig;
import com.ruoyi.product.service.INewUserGiftConfigService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 新用户礼包配置Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/newusergift/config")
public class NewUserGiftConfigController extends BaseController
{
    @Autowired
    private INewUserGiftConfigService newUserGiftConfigService;

    /**
     * 查询新用户礼包配置列表
     */
    @RequiresPermissions("product:config:list")
    @GetMapping("/list")
    public TableDataInfo list(NewUserGiftConfig newUserGiftConfig)
    {
        startPage();
        List<NewUserGiftConfig> list = newUserGiftConfigService.selectNewUserGiftConfigList(newUserGiftConfig);
        return getDataTable(list);
    }

    /**
     * 导出新用户礼包配置列表
     */
    @RequiresPermissions("product:config:export")
    @Log(title = "新用户礼包配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewUserGiftConfig newUserGiftConfig)
    {
        List<NewUserGiftConfig> list = newUserGiftConfigService.selectNewUserGiftConfigList(newUserGiftConfig);
        ExcelUtil<NewUserGiftConfig> util = new ExcelUtil<NewUserGiftConfig>(NewUserGiftConfig.class);
        util.exportExcel(response, list, "新用户礼包配置数据");
    }

    /**
     * 获取新用户礼包配置详细信息
     */
    @RequiresPermissions("product:config:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(newUserGiftConfigService.selectNewUserGiftConfigById(id));
    }

    /**
     * 新增新用户礼包配置
     */
    @RequiresPermissions("product:config:add")
    @Log(title = "新用户礼包配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewUserGiftConfig newUserGiftConfig)
    {
        return toAjax(newUserGiftConfigService.insertNewUserGiftConfig(newUserGiftConfig));
    }

    /**
     * 修改新用户礼包配置
     */
    @RequiresPermissions("product:config:edit")
    @Log(title = "新用户礼包配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewUserGiftConfig newUserGiftConfig)
    {
        return toAjax(newUserGiftConfigService.updateNewUserGiftConfig(newUserGiftConfig));
    }

    /**
     * 删除新用户礼包配置
     */
    @RequiresPermissions("product:config:remove")
    @Log(title = "新用户礼包配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(newUserGiftConfigService.deleteNewUserGiftConfigByIds(ids));
    }
}
