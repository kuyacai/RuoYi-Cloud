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
import com.ruoyi.product.domain.GoodsRevisionSpu;
import com.ruoyi.product.service.IGoodsRevisionSpuService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * SPU 快照Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/goods/revision/spu")
public class GoodsRevisionSpuController extends BaseController
{
    @Autowired
    private IGoodsRevisionSpuService goodsRevisionSpuService;

    /**
     * 查询SPU 快照列表
     */
    @RequiresPermissions("product:spu:list")
    @GetMapping("/list")
    public TableDataInfo list(GoodsRevisionSpu goodsRevisionSpu)
    {
        startPage();
        List<GoodsRevisionSpu> list = goodsRevisionSpuService.selectGoodsRevisionSpuList(goodsRevisionSpu);
        return getDataTable(list);
    }

    /**
     * 导出SPU 快照列表
     */
    @RequiresPermissions("product:spu:export")
    @Log(title = "SPU 快照", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsRevisionSpu goodsRevisionSpu)
    {
        List<GoodsRevisionSpu> list = goodsRevisionSpuService.selectGoodsRevisionSpuList(goodsRevisionSpu);
        ExcelUtil<GoodsRevisionSpu> util = new ExcelUtil<GoodsRevisionSpu>(GoodsRevisionSpu.class);
        util.exportExcel(response, list, "SPU 快照数据");
    }

    /**
     * 获取SPU 快照详细信息
     */
    @RequiresPermissions("product:spu:query")
    @GetMapping(value = "/{revisionId}")
    public AjaxResult getInfo(@PathVariable("revisionId") String revisionId)
    {
        return success(goodsRevisionSpuService.selectGoodsRevisionSpuByRevisionId(revisionId));
    }

    /**
     * 新增SPU 快照
     */
    @RequiresPermissions("product:spu:add")
    @Log(title = "SPU 快照", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsRevisionSpu goodsRevisionSpu)
    {
        return toAjax(goodsRevisionSpuService.insertGoodsRevisionSpu(goodsRevisionSpu));
    }

    /**
     * 修改SPU 快照
     */
    @RequiresPermissions("product:spu:edit")
    @Log(title = "SPU 快照", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsRevisionSpu goodsRevisionSpu)
    {
        return toAjax(goodsRevisionSpuService.updateGoodsRevisionSpu(goodsRevisionSpu));
    }

    /**
     * 删除SPU 快照
     */
    @RequiresPermissions("product:spu:remove")
    @Log(title = "SPU 快照", businessType = BusinessType.DELETE)
	@DeleteMapping("/{revisionIds}")
    public AjaxResult remove(@PathVariable String[] revisionIds)
    {
        return toAjax(goodsRevisionSpuService.deleteGoodsRevisionSpuByRevisionIds(revisionIds));
    }
}
