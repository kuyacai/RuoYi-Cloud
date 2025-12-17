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
import com.ruoyi.product.domain.NewUserGiftProduct;
import com.ruoyi.product.service.INewUserGiftProductService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 新用户礼包活动商品Controller
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@RestController
@RequestMapping("/newusergift/activity/product")
public class NewUserGiftProductController extends BaseController
{
    @Autowired
    private INewUserGiftProductService newUserGiftProductService;

    /**
     * 查询新用户礼包活动商品列表
     */
    @RequiresPermissions("product:product:list")
    @GetMapping("/list")
    public TableDataInfo list(NewUserGiftProduct newUserGiftProduct)
    {
        startPage();
        List<NewUserGiftProduct> list = newUserGiftProductService.selectNewUserGiftProductList(newUserGiftProduct);
        return getDataTable(list);
    }

    /**
     * 导出新用户礼包活动商品列表
     */
    @RequiresPermissions("product:product:export")
    @Log(title = "新用户礼包活动商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NewUserGiftProduct newUserGiftProduct)
    {
        List<NewUserGiftProduct> list = newUserGiftProductService.selectNewUserGiftProductList(newUserGiftProduct);
        ExcelUtil<NewUserGiftProduct> util = new ExcelUtil<NewUserGiftProduct>(NewUserGiftProduct.class);
        util.exportExcel(response, list, "新用户礼包活动商品数据");
    }

    /**
     * 获取新用户礼包活动商品详细信息
     */
    @RequiresPermissions("product:product:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(newUserGiftProductService.selectNewUserGiftProductById(id));
    }

    /**
     * 新增新用户礼包活动商品
     */
    @RequiresPermissions("product:product:add")
    @Log(title = "新用户礼包活动商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NewUserGiftProduct newUserGiftProduct)
    {
        return toAjax(newUserGiftProductService.insertNewUserGiftProduct(newUserGiftProduct));
    }

    /**
     * 修改新用户礼包活动商品
     */
    @RequiresPermissions("product:product:edit")
    @Log(title = "新用户礼包活动商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NewUserGiftProduct newUserGiftProduct)
    {
        return toAjax(newUserGiftProductService.updateNewUserGiftProduct(newUserGiftProduct));
    }

    /**
     * 删除新用户礼包活动商品
     */
    @RequiresPermissions("product:product:remove")
    @Log(title = "新用户礼包活动商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(newUserGiftProductService.deleteNewUserGiftProductByIds(ids));
    }
}
