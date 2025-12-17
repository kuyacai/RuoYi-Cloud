package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.GoodsRevision;

/**
 * 商品版本Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsRevisionService 
{
    /**
     * 查询商品版本
     * 
     * @param revisionId 商品版本主键
     * @return 商品版本
     */
    public GoodsRevision selectGoodsRevisionByRevisionId(String revisionId);

    /**
     * 查询商品版本列表
     * 
     * @param goodsRevision 商品版本
     * @return 商品版本集合
     */
    public List<GoodsRevision> selectGoodsRevisionList(GoodsRevision goodsRevision);

    /**
     * 新增商品版本
     * 
     * @param goodsRevision 商品版本
     * @return 结果
     */
    public int insertGoodsRevision(GoodsRevision goodsRevision);

    /**
     * 修改商品版本
     * 
     * @param goodsRevision 商品版本
     * @return 结果
     */
    public int updateGoodsRevision(GoodsRevision goodsRevision);

    /**
     * 批量删除商品版本
     * 
     * @param revisionIds 需要删除的商品版本主键集合
     * @return 结果
     */
    public int deleteGoodsRevisionByRevisionIds(String[] revisionIds);

    /**
     * 删除商品版本信息
     * 
     * @param revisionId 商品版本主键
     * @return 结果
     */
    public int deleteGoodsRevisionByRevisionId(String revisionId);

    /**
     * 根据商品ID查询冻结的版本
     * @param goodsId
     * @return
     */
    public GoodsRevision selectFrozenRevisionByGoodsId(String goodsId);

    /**
     * 根据商品ID查询编辑中的版本
     * @param goodsId
     * @return
     */
    public GoodsRevision selectEditingRevisionByGoodsId(String goodsId);


}
