package com.ruoyi.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.GoodsRevisionImage;
import com.ruoyi.product.mapper.GoodsRevisionImageMapper;
import com.ruoyi.product.service.IGoodsRevisionImageService;

/**
 * 商品图片快照Service业务层处理
 */
@Service
public class GoodsRevisionImageServiceImpl extends BaseServiceImpl<GoodsRevisionImageMapper, GoodsRevisionImage> implements IGoodsRevisionImageService {

    /**
     * 实现原 XML 中的 countByRevisionIdAndSourceUrl 逻辑
     * 使用 count 替代 select 列表，效率更高
     */
    @Override
    public int countByRevisionIdAndSourceUrl(String revisionId, String sourceUrl) {
        Long count = this.count(new LambdaQueryWrapper<GoodsRevisionImage>()
                .eq(GoodsRevisionImage::getRevisionId, revisionId)
                .eq(GoodsRevisionImage::getSourceUrl, sourceUrl));
        return count.intValue();
    }

    /**
     * 实现原 XML 中的 selectByRevisionIds 逻辑
     */
    @Override
    public List<GoodsRevisionImage> listByRevisionIds(List<String> revisionIds) {
        if (CollectionUtils.isEmpty(revisionIds)) {
            return new ArrayList<>();
        }
        return this.list(new LambdaQueryWrapper<GoodsRevisionImage>()
                .in(GoodsRevisionImage::getRevisionId, revisionIds));
    }

    /**
     * 实现原 XML 中的 selectByRevisionId 逻辑
     */
    @Override
    public List<GoodsRevisionImage> listByRevisionId(String revisionId) {
        return this.list(new LambdaQueryWrapper<GoodsRevisionImage>()
                .eq(GoodsRevisionImage::getRevisionId, revisionId));
    }
}