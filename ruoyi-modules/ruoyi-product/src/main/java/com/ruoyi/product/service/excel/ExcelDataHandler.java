package com.ruoyi.product.service.excel;

import java.util.List;
import java.util.Map;

public interface ExcelDataHandler<T> {
    /**
     * 根据动态参数查询数据
     * 
     * @param params 包含 shopId, activityId 等所有过滤条件的 Map
     * @return 填充好数据的 DTO 列表
     */
    List<T> getExportData(Map<String, Object> params);

    /** 参数校验（默认不校验，具体业务可重写） */
    default void validateParams(Map<String, Object> params) {
        // 使用 Hibernate Validator 或者断言
    }
}