package com.ruoyi.product.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.product.service.impl.ProductEnumService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enums")
public class ProductEnumController extends BaseController {

    @Autowired
    private ProductEnumService productEnumService;

    @GetMapping("/{enumName}")
    public AjaxResult getEnum(@PathVariable String enumName) {
        return AjaxResult.success(productEnumService.getEnum(enumName));
    }

    @PostMapping("/batch")
    public AjaxResult getEnums(@RequestBody List<String> enumNames) {
        return AjaxResult.success(productEnumService.getEnums(enumNames));
    }

    @GetMapping("/all")
    public AjaxResult getAllEnums() {
        return AjaxResult.success(productEnumService.getAllEnums());
    }
}