package com.ruoyi.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ruoyi.product.mapper.GoodsRevisionSpuMapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.GoodsRevisionSpu;
import com.ruoyi.product.service.IGoodsRevisionSpuService;

/**
 * SPU 快照Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsRevisionSpuServiceImpl extends BaseServiceImpl<GoodsRevisionSpuMapper, GoodsRevisionSpu>
        implements IGoodsRevisionSpuService {

    @Override
    public List<GoodsRevisionSpu> listByRevisionIds(List<String> revisionIds) {
        if (CollectionUtils.isEmpty(revisionIds)) {
            return new ArrayList<>();
        }
        return this.listByIds(revisionIds);
    }

    @Override
    public List<GoodsRevisionSpu> listNeedTitleUpdate(String goodsId) {
        // 直接调用 Mapper 中定义的自定义 SQL 方法
        return baseMapper.selectNeedTitleUpdate(goodsId);
    }

}
