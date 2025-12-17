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
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.service.IGoodsRevisionService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商品版本Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/goods/revision")
public class GoodsRevisionController extends BaseController
{
    @Autowired
    private IGoodsRevisionService goodsRevisionService;

    /**
     * 查询商品版本列表
     */
    @RequiresPermissions("product:revision:list")
    @GetMapping("/list")
    public TableDataInfo list(GoodsRevision goodsRevision)
    {
        startPage();
        List<GoodsRevision> list = goodsRevisionService.selectGoodsRevisionList(goodsRevision);
        return getDataTable(list);
    }

    /**
     * 导出商品版本列表
     */
    @RequiresPermissions("product:revision:export")
    @Log(title = "商品版本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsRevision goodsRevision)
    {
        List<GoodsRevision> list = goodsRevisionService.selectGoodsRevisionList(goodsRevision);
        ExcelUtil<GoodsRevision> util = new ExcelUtil<GoodsRevision>(GoodsRevision.class);
        util.exportExcel(response, list, "商品版本数据");
    }

    /**
     * 获取商品版本详细信息
     */
    @RequiresPermissions("product:revision:query")
    @GetMapping(value = "/{revisionId}")
    public AjaxResult getInfo(@PathVariable("revisionId") String revisionId)
    {
        return success(goodsRevisionService.selectGoodsRevisionByRevisionId(revisionId));
    }

    /**
     * 新增商品版本
     */
    @RequiresPermissions("product:revision:add")
    @Log(title = "商品版本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsRevision goodsRevision)
    {
        return toAjax(goodsRevisionService.insertGoodsRevision(goodsRevision));
    }

    /**
     * 修改商品版本
     */
    @RequiresPermissions("product:revision:edit")
    @Log(title = "商品版本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsRevision goodsRevision)
    {
        return toAjax(goodsRevisionService.updateGoodsRevision(goodsRevision));
    }

    /**
     * 删除商品版本
     */
    @RequiresPermissions("product:revision:remove")
    @Log(title = "商品版本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{revisionIds}")
    public AjaxResult remove(@PathVariable String[] revisionIds)
    {
        return toAjax(goodsRevisionService.deleteGoodsRevisionByRevisionIds(revisionIds));
    }
}
