package com.ruoyi.product.mapper;

import com.ruoyi.product.core.mybatisplus.RootMapper;
import com.ruoyi.product.domain.AsyncTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AsyncTaskMapper extends RootMapper<AsyncTask> {
    // 基础 CRUD 已经由 RootMapper (BaseMapper) 提供，不需要 XML
}