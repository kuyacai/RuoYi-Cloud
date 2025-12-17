package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.Goods;

/**
 * 云商品根Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsService 
{
    /**
     * 查询云商品根
     * 
     * @param goodsId 云商品根主键
     * @return 云商品根
     */
    public Goods selectGoodsByGoodsId(String goodsId);

    /**
     * 查询云商品根列表
     * 
     * @param goods 云商品根
     * @return 云商品根集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增云商品根
     * 
     * @param goods 云商品根
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改云商品根
     * 
     * @param goods 云商品根
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除云商品根
     * 
     * @param goodsIds 需要删除的云商品根主键集合
     * @return 结果
     */
    public int deleteGoodsByGoodsIds(String[] goodsIds);

    /**
     * 删除云商品根信息
     * 
     * @param goodsId 云商品根主键
     * @return 结果
     */
    public int deleteGoodsByGoodsId(String goodsId);

    /**
     * 根据源商品ID判断商品是否存在
     * @param sourceId
     * @return
     */
    public boolean existsBySourceId(String sourceId);

    public Goods selectGoodsBySourceId(String sourceId);
}
