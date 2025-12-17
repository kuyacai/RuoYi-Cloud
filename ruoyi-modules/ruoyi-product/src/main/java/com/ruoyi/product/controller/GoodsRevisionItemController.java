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
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.service.IGoodsRevisionItemService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * SKU 快照Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/goods/item")
public class GoodsRevisionItemController extends BaseController
{
    @Autowired
    private IGoodsRevisionItemService goodsRevisionItemService;

    /**
     * 查询SKU 快照列表
     */
    @RequiresPermissions("product:item:list")
    @GetMapping("/list")
    public TableDataInfo list(GoodsRevisionItem goodsRevisionItem)
    {
        startPage();
        List<GoodsRevisionItem> list = goodsRevisionItemService.selectGoodsRevisionItemList(goodsRevisionItem);
        return getDataTable(list);
    }

    /**
     * 导出SKU 快照列表
     */
    @RequiresPermissions("product:item:export")
    @Log(title = "SKU 快照", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsRevisionItem goodsRevisionItem)
    {
        List<GoodsRevisionItem> list = goodsRevisionItemService.selectGoodsRevisionItemList(goodsRevisionItem);
        ExcelUtil<GoodsRevisionItem> util = new ExcelUtil<GoodsRevisionItem>(GoodsRevisionItem.class);
        util.exportExcel(response, list, "SKU 快照数据");
    }

    /**
     * 获取SKU 快照详细信息
     */
    @RequiresPermissions("product:item:query")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") String itemId)
    {
        return success(goodsRevisionItemService.selectGoodsRevisionItemByItemId(itemId));
    }

    /**
     * 新增SKU 快照
     */
    @RequiresPermissions("product:item:add")
    @Log(title = "SKU 快照", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsRevisionItem goodsRevisionItem)
    {
        return toAjax(goodsRevisionItemService.insertGoodsRevisionItem(goodsRevisionItem));
    }

    /**
     * 修改SKU 快照
     */
    @RequiresPermissions("product:item:edit")
    @Log(title = "SKU 快照", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsRevisionItem goodsRevisionItem)
    {
        return toAjax(goodsRevisionItemService.updateGoodsRevisionItem(goodsRevisionItem));
    }

    /**
     * 删除SKU 快照
     */
    @RequiresPermissions("product:item:remove")
    @Log(title = "SKU 快照", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable String[] itemIds)
    {
        return toAjax(goodsRevisionItemService.deleteGoodsRevisionItemByItemIds(itemIds));
    }
}
