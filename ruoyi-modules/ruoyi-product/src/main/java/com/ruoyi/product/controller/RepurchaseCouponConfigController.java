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
import com.ruoyi.product.domain.RepurchaseCouponConfig;
import com.ruoyi.product.service.IRepurchaseCouponConfigService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 复购券配置Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/repurchasecoupon/config")
public class RepurchaseCouponConfigController extends BaseController
{
    @Autowired
    private IRepurchaseCouponConfigService repurchaseCouponConfigService;

    /**
     * 查询复购券配置列表
     */
    @RequiresPermissions("product:config:list")
    @GetMapping("/list")
    public TableDataInfo list(RepurchaseCouponConfig repurchaseCouponConfig)
    {
        startPage();
        List<RepurchaseCouponConfig> list = repurchaseCouponConfigService.selectRepurchaseCouponConfigList(repurchaseCouponConfig);
        return getDataTable(list);
    }

    /**
     * 导出复购券配置列表
     */
    @RequiresPermissions("product:config:export")
    @Log(title = "复购券配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepurchaseCouponConfig repurchaseCouponConfig)
    {
        List<RepurchaseCouponConfig> list = repurchaseCouponConfigService.selectRepurchaseCouponConfigList(repurchaseCouponConfig);
        ExcelUtil<RepurchaseCouponConfig> util = new ExcelUtil<RepurchaseCouponConfig>(RepurchaseCouponConfig.class);
        util.exportExcel(response, list, "复购券配置数据");
    }

    /**
     * 获取复购券配置详细信息
     */
    @RequiresPermissions("product:config:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(repurchaseCouponConfigService.selectRepurchaseCouponConfigById(id));
    }

    /**
     * 新增复购券配置
     */
    @RequiresPermissions("product:config:add")
    @Log(title = "复购券配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepurchaseCouponConfig repurchaseCouponConfig)
    {
        return toAjax(repurchaseCouponConfigService.insertRepurchaseCouponConfig(repurchaseCouponConfig));
    }

    /**
     * 修改复购券配置
     */
    @RequiresPermissions("product:config:edit")
    @Log(title = "复购券配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepurchaseCouponConfig repurchaseCouponConfig)
    {
        return toAjax(repurchaseCouponConfigService.updateRepurchaseCouponConfig(repurchaseCouponConfig));
    }

    /**
     * 删除复购券配置
     */
    @RequiresPermissions("product:config:remove")
    @Log(title = "复购券配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(repurchaseCouponConfigService.deleteRepurchaseCouponConfigByIds(ids));
    }
}
