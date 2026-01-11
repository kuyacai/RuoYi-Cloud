package com.ruoyi.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.mapper.GoodsMapper;
import com.ruoyi.product.service.IGoodsService;

/**
 * 云商品根Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsServiceImpl extends BaseServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    /**
     * 统计来源的商品数
     * 
     * @param sourceId
     * @return
     */
    @Override
    public int countBySourceId(String sourceId) {
        return (int) this.count(new LambdaQueryWrapper<Goods>()
                .eq(Goods::getSourceId, sourceId));
    }

    @Override
    public boolean existsBySourceId(String sourceId) {
        // 方案一：基于已有的 count 方法判断
        // return this.countBySourceId(sourceId) > 0;

        // 方案二：使用 MyBatis Plus 3.5.x+ 推荐的 exists 方法（性能更优）
        // 它会生成类似 SELECT 1 FROM goods WHERE source_id = ? LIMIT 1 的 SQL
        return this.exists(new LambdaQueryWrapper<Goods>()
                .eq(Goods::getSourceId, sourceId));
    }

    /**
     * 根据来源查商品列表
     * 
     * @param sourceId
     * @return
     */
    @Override
    public List<Goods> listBySourceId(String sourceId) {
        return this.list(new LambdaQueryWrapper<Goods>()
                .eq(Goods::getSourceId, sourceId)
                .orderByDesc(Goods::getCreatedAtUtc)); // 建议查询列表时也带上默认排序
    }

    /**
     * 取最近插入的某个店铺的某个商品id的商品
     * 
     * @param shopProductId
     * @return
     */
    @Override
    public Goods getLatestByShopProductId(String shopProductId) {
        return this.getOne(new LambdaQueryWrapper<Goods>()
                .eq(Goods::getShopProductId, shopProductId)
                .orderByDesc(Goods::getCreatedAtUtc) // 按创建时间倒序
                .last("limit 1")); // 强制只取数据库层面的第一条，避免查出多条导致报错
    }

    /**
     * 根据goods_id批量查询
     * 
     * @param goodsIds
     * @return
     */
    @Override
    public List<Goods> listByBatchIds(List<String> goodsIds) {
        if (CollectionUtils.isEmpty(goodsIds)) {
            return new ArrayList<>();
        }
        // 直接调用父类的原生方法即可，不需要自己写 SQL
        return this.listByIds(goodsIds);
    }

    /**
     * 实现根据 source_id 查询逻辑
     * 使用 getOne 配合 LambdaQueryWrapper 确保查询的类型安全
     */
    @Override
    public Goods selectGoodsBySourceId(String sourceId) {
        if (sourceId == null) {
            return null;
        }

        // 使用 LambdaQueryWrapper 匹配 sourceId 字段
        // 使用 last("LIMIT 1") 确保在数据库查找到第一条记录后立即返回，提高性能
        return this.getOne(new LambdaQueryWrapper<Goods>()
                .eq(Goods::getSourceId, sourceId)
                .last("LIMIT 1"));
    }

}
