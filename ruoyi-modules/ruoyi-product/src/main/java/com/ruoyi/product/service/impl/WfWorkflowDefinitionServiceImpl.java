package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.WfWorkflowDefinition;
import com.ruoyi.product.mapper.WfWorkflowDefinitionMapper;
import com.ruoyi.product.service.IWfWorkflowDefinitionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WfWorkflowDefinitionServiceImpl extends BaseServiceImpl<WfWorkflowDefinitionMapper, WfWorkflowDefinition>
        implements IWfWorkflowDefinitionService {
}