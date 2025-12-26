package com.ruoyi.product.core.mybatisplus.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * 继承 MP 的 ServiceImpl，T 是实体类，M 是对应的 Mapper
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {
}