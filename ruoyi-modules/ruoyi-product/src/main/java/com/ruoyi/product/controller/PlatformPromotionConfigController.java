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
import com.ruoyi.product.domain.PlatformPromotionConfig;
import com.ruoyi.product.service.IPlatformPromotionConfigService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 平台促销配置Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/platformpromotion/config")
public class PlatformPromotionConfigController extends BaseController
{
    @Autowired
    private IPlatformPromotionConfigService platformPromotionConfigService;

    /**
     * 查询平台促销配置列表
     */
    @RequiresPermissions("product:config:list")
    @GetMapping("/list")
    public TableDataInfo list(PlatformPromotionConfig platformPromotionConfig)
    {
        startPage();
        List<PlatformPromotionConfig> list = platformPromotionConfigService.selectPlatformPromotionConfigList(platformPromotionConfig);
        return getDataTable(list);
    }

    /**
     * 导出平台促销配置列表
     */
    @RequiresPermissions("product:config:export")
    @Log(title = "平台促销配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlatformPromotionConfig platformPromotionConfig)
    {
        List<PlatformPromotionConfig> list = platformPromotionConfigService.selectPlatformPromotionConfigList(platformPromotionConfig);
        ExcelUtil<PlatformPromotionConfig> util = new ExcelUtil<PlatformPromotionConfig>(PlatformPromotionConfig.class);
        util.exportExcel(response, list, "平台促销配置数据");
    }

    /**
     * 获取平台促销配置详细信息
     */
    @RequiresPermissions("product:config:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(platformPromotionConfigService.selectPlatformPromotionConfigById(id));
    }

    /**
     * 新增平台促销配置
     */
    @RequiresPermissions("product:config:add")
    @Log(title = "平台促销配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlatformPromotionConfig platformPromotionConfig)
    {
        return toAjax(platformPromotionConfigService.insertPlatformPromotionConfig(platformPromotionConfig));
    }

    /**
     * 修改平台促销配置
     */
    @RequiresPermissions("product:config:edit")
    @Log(title = "平台促销配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlatformPromotionConfig platformPromotionConfig)
    {
        return toAjax(platformPromotionConfigService.updatePlatformPromotionConfig(platformPromotionConfig));
    }

    /**
     * 删除平台促销配置
     */
    @RequiresPermissions("product:config:remove")
    @Log(title = "平台促销配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(platformPromotionConfigService.deletePlatformPromotionConfigByIds(ids));
    }
}
