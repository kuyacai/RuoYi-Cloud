package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.WfNodeCapability;
import com.ruoyi.product.mapper.WfNodeCapabilityMapper;
import com.ruoyi.product.service.IWfNodeCapabilityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WfNodeCapabilityServiceImpl extends BaseServiceImpl<WfNodeCapabilityMapper, WfNodeCapability>
        implements IWfNodeCapabilityService {
}