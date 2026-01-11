package com.ruoyi.product.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.product.app.display.GoodsVersionDetail;
import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.enums.RevStatus;
import com.ruoyi.product.service.IGoodsDisplayService;
import com.ruoyi.product.service.IGoodsService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 云商品根Controller
 * * @author Rupert
 * 
 * @date 2025-12-26
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IGoodsDisplayService goodsDisplayService;

    /**
     * 查询云商品根列表
     * 修改点：使用 LambdaQueryWrapper 构建动态查询
     */
    @RequiresPermissions("product:goods:list")
    @GetMapping("/list")
    public TableDataInfo list(Goods goods) {
        startPage();
        // 假设 Service 层实现了基于实体的 Wrapper 构建，或在此处构建
        List<Goods> list = goodsService.list(new LambdaQueryWrapper<Goods>()
                .eq(StringUtils.isNotBlank(goods.getGoodsId()), Goods::getGoodsId, goods.getGoodsId())
                .like(StringUtils.isNotBlank(goods.getSourceTitle()), Goods::getSourceTitle, goods.getSourceTitle())
                .eq(StringUtils.isNotBlank(goods.getSourceId()), Goods::getSourceId, goods.getSourceId())
                .eq(StringUtils.isNotBlank(goods.getShopProductId()), Goods::getShopProductId, goods.getShopProductId())
                .orderByDesc(Goods::getCreatedAtUtc)
        // 示例：模糊查询名称
        );
        return getDataTable(list);
    }

    /**
     * 导出云商品根列表
     */
    @RequiresPermissions("product:goods:export")
    @Log(title = "云商品根", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Goods goods) {
        List<Goods> list = goodsService.list(new LambdaQueryWrapper<Goods>());
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        util.exportExcel(response, list, "云商品根数据");
    }

    /**
     * 获取云商品根详细信息
     * 修改点：使用 MP 的 getById
     */
    @RequiresPermissions("product:goods:query")
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") String goodsId) {
        return success(goodsService.getById(goodsId));
    }

    /**
     * 专门为“新窗口详情页”提供的接口
     * 这样即使在浏览器直接输入 URL，后端也能正确解析参数
     */
    @RequiresPermissions("product:goods:query")
    @GetMapping("/detail")
    public AjaxResult getGoodsVersionDetail(@RequestParam("id") String id,
            @RequestParam("status") String status) {
        // 逻辑：根据 ID 找到商品，根据 status 判断返回哪个版本的数据
        GoodsVersionDetail detail = null;
        if (RevStatus.FROZEN.getCode().equals(status)) {
            detail = goodsDisplayService.getFrozenVersion(id);
        }
        if (RevStatus.EDITING.getCode().equals(status)) {
            detail = goodsDisplayService.getEditingVersion(id);
        }
        if (RevStatus.AUDITING.getCode().equals(status)) {
            detail = goodsDisplayService.getAuditingVersion(id);
        }
        if (RevStatus.APPROVED.getCode().equals(status)) {
            detail = goodsDisplayService.getApprovedVersion(id);
        }

        if (detail == null) {
            return error("参数错误");
        } else {
            return success(detail);
        }

    }

    /**
     * 新增云商品根
     * 修改点：使用 save
     */
    @RequiresPermissions("product:goods:add")
    @Log(title = "云商品根", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Goods goods) {
        return toAjax(goodsService.save(goods));
    }

    /**
     * 修改云商品根
     * 修改点：使用 updateById
     */
    @RequiresPermissions("product:goods:edit")
    @Log(title = "云商品根", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Goods goods) {
        return toAjax(goodsService.updateById(goods));
    }

    /**
     * 删除云商品根
     * 修改点：使用 removeByIds
     */
    @RequiresPermissions("product:goods:remove")
    @Log(title = "云商品根", businessType = BusinessType.DELETE)
    @DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable String[] goodsIds) {
        return toAjax(goodsService.removeByIds(Arrays.asList(goodsIds)));
    }
}