package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.ProductSnapshots;
import com.ruoyi.product.mapper.ProductSnapshotsMapper;
import com.ruoyi.product.service.IProductSnapshotsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSnapshotsServiceImpl extends BaseServiceImpl<ProductSnapshotsMapper, ProductSnapshots>
        implements IProductSnapshotsService {

}
