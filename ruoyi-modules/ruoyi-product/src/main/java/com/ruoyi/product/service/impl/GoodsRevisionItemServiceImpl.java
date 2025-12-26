package com.ruoyi.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.GoodsRevisionItemMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.GoodsRevisionSpu;
import com.ruoyi.product.service.IGoodsRevisionItemService;
import org.springframework.util.CollectionUtils;

/**
 * SKU 快照Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsRevisionItemServiceImpl extends BaseServiceImpl<GoodsRevisionItemMapper, GoodsRevisionItem>
        implements IGoodsRevisionItemService {
    /**
     * 检查唯一性
     * 修改点：使用 count 方法代替查询整个 List，性能更高
     */
    @Override
    public boolean checkUnique(GoodsRevisionItem goodsRevisionItem) {
        // 使用 LambdaQueryWrapper 动态构建条件
        Long count = this.count(new LambdaQueryWrapper<GoodsRevisionItem>()
                .eq(StringUtils.isNotBlank(goodsRevisionItem.getRevisionId()), GoodsRevisionItem::getRevisionId,
                        goodsRevisionItem.getRevisionId())
                .eq(StringUtils.isNotBlank(goodsRevisionItem.getShopId()), GoodsRevisionItem::getShopId,
                        goodsRevisionItem.getShopId())
                .eq(StringUtils.isNotBlank(goodsRevisionItem.getShopProductId()), GoodsRevisionItem::getShopProductId,
                        goodsRevisionItem.getShopProductId())
                .eq(StringUtils.isNotBlank(goodsRevisionItem.getShopSkuId()), GoodsRevisionItem::getShopSkuId,
                        goodsRevisionItem.getShopSkuId())
                .eq(StringUtils.isNotBlank(goodsRevisionItem.getSkuCode()), GoodsRevisionItem::getSkuCode,
                        goodsRevisionItem.getSkuCode())
        // 可以根据需要继续链式添加其他属性的判断
        );
        return count == 0;
    }

    /**
     * 判断是否已被导入
     * 修改点：直接构建精确查询条件，无需创建完整的实体对象
     */
    @Override
    public boolean isBeenImported(String revisionId, String shopId, String shopProductId, String shopSkuId) {
        Long count = this.count(new LambdaQueryWrapper<GoodsRevisionItem>()
                .eq(GoodsRevisionItem::getRevisionId, revisionId)
                .eq(GoodsRevisionItem::getShopId, shopId)
                .eq(GoodsRevisionItem::getShopProductId, shopProductId)
                .eq(GoodsRevisionItem::getShopSkuId, shopSkuId));

        return count > 0;
    }

    /**
     * 实现原 XML 中的 selectByRevisionIds 逻辑
     * 批量查询版本关联的 SKU
     */
    @Override
    public List<GoodsRevisionItem> listByRevisionIds(List<String> revisionIds) {
        if (CollectionUtils.isEmpty(revisionIds)) {
            return new ArrayList<>();
        }
        return this.list(new LambdaQueryWrapper<GoodsRevisionItem>()
                .in(GoodsRevisionItem::getRevisionId, revisionIds));
    }

    /**
     * 实现原 XML 中的 selectByRevisionId 逻辑
     * 查询单个版本下的所有 SKU
     */
    @Override
    public List<GoodsRevisionItem> listByRevisionId(String revisionId) {
        return this.list(new LambdaQueryWrapper<GoodsRevisionItem>()
                .eq(GoodsRevisionItem::getRevisionId, revisionId));
    }

    @Override
    public List<GoodsRevisionItem> selectSkusNeedPriceUpdate(String goodsId) {
        return baseMapper.selectSkusNeedPriceUpdate(goodsId);
    }

}
