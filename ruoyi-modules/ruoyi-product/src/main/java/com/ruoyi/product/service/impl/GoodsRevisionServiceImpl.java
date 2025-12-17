package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.GoodsRevisionMapper;
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.service.IGoodsRevisionService;

/**
 * 商品版本Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsRevisionServiceImpl implements IGoodsRevisionService 
{
    @Autowired
    private GoodsRevisionMapper goodsRevisionMapper;

    /**
     * 查询商品版本
     * 
     * @param revisionId 商品版本主键
     * @return 商品版本
     */
    @Override
    public GoodsRevision selectGoodsRevisionByRevisionId(String revisionId)
    {
        return goodsRevisionMapper.selectGoodsRevisionByRevisionId(revisionId);
    }

    /**
     * 查询商品版本列表
     * 
     * @param goodsRevision 商品版本
     * @return 商品版本
     */
    @Override
    public List<GoodsRevision> selectGoodsRevisionList(GoodsRevision goodsRevision)
    {
        return goodsRevisionMapper.selectGoodsRevisionList(goodsRevision);
    }

    /**
     * 新增商品版本
     * 
     * @param goodsRevision 商品版本
     * @return 结果
     */
    @Override
    public int insertGoodsRevision(GoodsRevision goodsRevision)
    {
        return goodsRevisionMapper.insertGoodsRevision(goodsRevision);
    }

    /**
     * 修改商品版本
     * 
     * @param goodsRevision 商品版本
     * @return 结果
     */
    @Override
    public int updateGoodsRevision(GoodsRevision goodsRevision)
    {
        return goodsRevisionMapper.updateGoodsRevision(goodsRevision);
    }

    /**
     * 批量删除商品版本
     * 
     * @param revisionIds 需要删除的商品版本主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRevisionByRevisionIds(String[] revisionIds)
    {
        return goodsRevisionMapper.deleteGoodsRevisionByRevisionIds(revisionIds);
    }

    /**
     * 删除商品版本信息
     * 
     * @param revisionId 商品版本主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRevisionByRevisionId(String revisionId)
    {
        return goodsRevisionMapper.deleteGoodsRevisionByRevisionId(revisionId);
    }


    /**
     * 根据商品ID查询冻结的版本
     * @param goodsId
     * @return
     */
    @Override
    public GoodsRevision selectFrozenRevisionByGoodsId(String goodsId) {
        return goodsRevisionMapper.selectFrozenRevisionByGoodsId(goodsId);
    }

    /**
     * 根据商品ID查询编辑中的版本
     * @param goodsId
     * @return
     */
    @Override
    public GoodsRevision selectEditingRevisionByGoodsId(String goodsId) {
        return goodsRevisionMapper.selectEditingRevisionByGoodsId(goodsId);
    }
}
