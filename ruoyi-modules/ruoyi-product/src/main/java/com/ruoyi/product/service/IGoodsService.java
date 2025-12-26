package com.ruoyi.product.service;

import java.util.List;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.Goods;

/**
 * 云商品根Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsService extends IBaseService<Goods> {

    /**
     * 根据来源ID，判断该商品是否存在。
     * @param sourceId
     * @return
     */
    boolean existsBySourceId(String sourceId);

    /**
     * 根据来源ID统计商品数
     * @param sourceId
     * @return
     */
    int countBySourceId(String sourceId);

    /**
     * 根据来源查商品列表
     * @param sourceId
     * @return
     */
    List<Goods> listBySourceId(String sourceId);
    /**
     * 取最近插入的某个店铺的某个商品id的商品
     * @param shopProductId
     * @return
     */
    Goods getLatestByShopProductId(String shopProductId);
    /**
     * 根据goods_id批量查询
     * @param goodsIds
     * @return
     */
    List<Goods> listByBatchIds(List<String> goodsIds);


    /**
     * 根据来源ID查询商品信息
     * * @param sourceId 来源唯一标识
     * @return 商品实体，若不存在则返回 null
     */
    Goods selectGoodsBySourceId(String sourceId);
}
