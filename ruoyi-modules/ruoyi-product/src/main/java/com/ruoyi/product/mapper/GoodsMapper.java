package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.Goods;

/**
 * 云商品根Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface GoodsMapper 
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
     * 删除云商品根
     * 
     * @param goodsId 云商品根主键
     * @return 结果
     */
    public int deleteGoodsByGoodsId(String goodsId);

    /**
     * 批量删除云商品根
     * 
     * @param goodsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByGoodsIds(String[] goodsIds);


    /**
     * 根据源商品ID统计商品数量
     * @param sourceId
     * @return
     */
    public int countBySourceId(String sourceId);

    /**
     * 根据源商品ID查询商品
     * @param sourceId
     * @return
     */
    public Goods selectGoodsBySourceId(String sourceId);
}
