package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.GoodsMapper;
import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.service.IGoodsService;

/**
 * 云商品根Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsServiceImpl implements IGoodsService 
{
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询云商品根
     * 
     * @param goodsId 云商品根主键
     * @return 云商品根
     */
    @Override
    public Goods selectGoodsByGoodsId(String goodsId)
    {
        return goodsMapper.selectGoodsByGoodsId(goodsId);
    }

    /**
     * 查询云商品根列表
     * 
     * @param goods 云商品根
     * @return 云商品根
     */
    @Override
    public List<Goods> selectGoodsList(Goods goods)
    {
        return goodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增云商品根
     * 
     * @param goods 云商品根
     * @return 结果
     */
    @Override
    public int insertGoods(Goods goods)
    {
        return goodsMapper.insertGoods(goods);
    }

    /**
     * 修改云商品根
     * 
     * @param goods 云商品根
     * @return 结果
     */
    @Override
    public int updateGoods(Goods goods)
    {
        return goodsMapper.updateGoods(goods);
    }

    /**
     * 批量删除云商品根
     * 
     * @param goodsIds 需要删除的云商品根主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByGoodsIds(String[] goodsIds)
    {
        return goodsMapper.deleteGoodsByGoodsIds(goodsIds);
    }

    /**
     * 删除云商品根信息
     * 
     * @param goodsId 云商品根主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByGoodsId(String goodsId)
    {
        return goodsMapper.deleteGoodsByGoodsId(goodsId);
    }

    @Override
    public boolean existsBySourceId(String sourceId) {
        int count = goodsMapper.countBySourceId(sourceId);
        return count > 0;
    }

    @Override
    public Goods selectGoodsBySourceId(String sourceId) {
        return goodsMapper.selectGoodsBySourceId(sourceId);
    }
}

