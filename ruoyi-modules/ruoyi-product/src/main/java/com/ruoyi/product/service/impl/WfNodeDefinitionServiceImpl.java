package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.WfNodeDefinition;
import com.ruoyi.product.mapper.NodeDefinitionMapper;
import com.ruoyi.product.service.IWfNodeDefinitionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WfNodeDefinitionServiceImpl extends BaseServiceImpl<NodeDefinitionMapper, WfNodeDefinition>
                implements IWfNodeDefinitionService {
}