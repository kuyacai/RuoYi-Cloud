package com.ruoyi.product.service.impl;

import org.springframework.stereotype.Service;

import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.DouyinKeywords;
import com.ruoyi.product.mapper.DouyinKeywordsMapper;
import com.ruoyi.product.service.IDouyinKeywordsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DouyinKeywordsServiceImpl extends BaseServiceImpl<DouyinKeywordsMapper, DouyinKeywords>
        implements IDouyinKeywordsService {

}
