package com.ruoyi.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ruoyi.product.mapper.GoodsRevisionMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.service.IGoodsRevisionService;

/**
 * 商品版本Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsRevisionServiceImpl extends BaseServiceImpl<GoodsRevisionMapper, GoodsRevision>
        implements IGoodsRevisionService {
    @Override
    public List<GoodsRevision> listFrozenByGoodsId(String goodsId) {
        return this.list(new LambdaQueryWrapper<GoodsRevision>()
                .eq(GoodsRevision::getGoodsId, goodsId)
                .eq(GoodsRevision::getRevStatus, "frozen"));
    }

    @Override
    public List<GoodsRevision> listEditingByGoodsId(String goodsId) {
        return this.list(new LambdaQueryWrapper<GoodsRevision>()
                .eq(GoodsRevision::getGoodsId, goodsId)
                .eq(GoodsRevision::getRevStatus, "editing"));
    }

    @Override
    public List<GoodsRevision> listApprovingByGoodsId(String goodsId) {
        return this.list(new LambdaQueryWrapper<GoodsRevision>()
                .eq(GoodsRevision::getGoodsId, goodsId)
                .eq(GoodsRevision::getRevStatus, "approving"));
    }

    @Override
    public List<GoodsRevision> listByRevisionIds(List<String> revisionIds) {
        if (CollectionUtils.isEmpty(revisionIds)) {
            return new ArrayList<>();
        }
        // 直接使用 IService 自带的 listByIds 方法
        return this.listByIds(revisionIds);
    }

    @Override
    public List<GoodsRevision> listByGoodsId(String goodsId) {
        return this.list(new LambdaQueryWrapper<GoodsRevision>()
                .eq(GoodsRevision::getGoodsId, goodsId)
                .orderByDesc(GoodsRevision::getGmtCreate)); // 对应 XML 中的 Order By gmt_create desc
    }

    @Override
    public List<GoodsRevision> listByGoodsAndStatus(String goodsId, List<String> statusList) {
        LambdaQueryWrapper<GoodsRevision> lqw = new LambdaQueryWrapper<>();
        lqw.eq(GoodsRevision::getGoodsId, goodsId);

        // 对应 XML 中的 <if test="statusList != null ...">
        if (!CollectionUtils.isEmpty(statusList)) {
            lqw.in(GoodsRevision::getRevStatus, statusList);
        }

        // 对应 XML 中的 ORDER BY gmt_modified DESC
        lqw.orderByDesc(GoodsRevision::getGmtModified);

        return this.list(lqw);
    }

}
