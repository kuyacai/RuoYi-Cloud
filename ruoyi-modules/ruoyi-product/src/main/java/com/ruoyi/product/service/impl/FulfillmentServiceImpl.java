package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.Fulfillment;
import com.ruoyi.product.mapper.FulfillmentMapper;
import com.ruoyi.product.service.IFulfillmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FulfillmentServiceImpl extends BaseServiceImpl<FulfillmentMapper, Fulfillment>
        implements IFulfillmentService {

}