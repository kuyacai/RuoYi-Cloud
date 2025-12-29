package com.ruoyi.product.domain.dto;

import java.io.Serializable;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;
import com.ruoyi.product.core.annotation.ExcelBusiness;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 必须：给 ExcelUtil 反射创建实例用
@AllArgsConstructor // 建议：在使用 @Builder 时必须配合全参构造
// TODO 参数值临时设置
@ExcelBusiness(value = "业务表示", exportName = "数据导出", templateName = "导入模板")
public class SimpleProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "商品ID", type = Type.ALL)
    private String productId;

    @Excel(name = "商品标题", type = Type.IMPORT)
    private String title;

    @Excel(name = "SKUID", type = Type.IMPORT)
    private String skuId;

}
