package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.WfNodeInstance;
import com.ruoyi.product.mapper.WfNodeInstanceMapper;
import com.ruoyi.product.service.IWfNodeInstanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WfNodeInstanceServiceImpl extends BaseServiceImpl<WfNodeInstanceMapper, WfNodeInstance>
        implements IWfNodeInstanceService {

}
