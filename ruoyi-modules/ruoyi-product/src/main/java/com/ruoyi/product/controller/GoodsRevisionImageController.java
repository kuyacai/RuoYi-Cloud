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
import com.ruoyi.product.domain.GoodsRevisionImage;
import com.ruoyi.product.service.IGoodsRevisionImageService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * goods 图片Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/goods/image")
public class GoodsRevisionImageController extends BaseController
{
    @Autowired
    private IGoodsRevisionImageService goodsRevisionImageService;

    /**
     * 查询goods 图片列表
     */
    @RequiresPermissions("product:image:list")
    @GetMapping("/list")
    public TableDataInfo list(GoodsRevisionImage goodsRevisionImage)
    {
        startPage();
        List<GoodsRevisionImage> list = goodsRevisionImageService.selectGoodsRevisionImageList(goodsRevisionImage);
        return getDataTable(list);
    }

    /**
     * 导出goods 图片列表
     */
    @RequiresPermissions("product:image:export")
    @Log(title = "goods 图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsRevisionImage goodsRevisionImage)
    {
        List<GoodsRevisionImage> list = goodsRevisionImageService.selectGoodsRevisionImageList(goodsRevisionImage);
        ExcelUtil<GoodsRevisionImage> util = new ExcelUtil<GoodsRevisionImage>(GoodsRevisionImage.class);
        util.exportExcel(response, list, "goods 图片数据");
    }

    /**
     * 获取goods 图片详细信息
     */
    @RequiresPermissions("product:image:query")
    @GetMapping(value = "/{imageId}")
    public AjaxResult getInfo(@PathVariable("imageId") String imageId)
    {
        return success(goodsRevisionImageService.selectGoodsRevisionImageByImageId(imageId));
    }

    /**
     * 新增goods 图片
     */
    @RequiresPermissions("product:image:add")
    @Log(title = "goods 图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsRevisionImage goodsRevisionImage)
    {
        return toAjax(goodsRevisionImageService.insertGoodsRevisionImage(goodsRevisionImage));
    }

    /**
     * 修改goods 图片
     */
    @RequiresPermissions("product:image:edit")
    @Log(title = "goods 图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsRevisionImage goodsRevisionImage)
    {
        return toAjax(goodsRevisionImageService.updateGoodsRevisionImage(goodsRevisionImage));
    }

    /**
     * 删除goods 图片
     */
    @RequiresPermissions("product:image:remove")
    @Log(title = "goods 图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{imageIds}")
    public AjaxResult remove(@PathVariable String[] imageIds)
    {
        return toAjax(goodsRevisionImageService.deleteGoodsRevisionImageByImageIds(imageIds));
    }
}
