package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.GoodsRevisionSpuMapper;
import com.ruoyi.product.domain.GoodsRevisionSpu;
import com.ruoyi.product.service.IGoodsRevisionSpuService;

/**
 * SPU 快照Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsRevisionSpuServiceImpl implements IGoodsRevisionSpuService 
{
    @Autowired
    private GoodsRevisionSpuMapper goodsRevisionSpuMapper;

    /**
     * 查询SPU 快照
     * 
     * @param revisionId SPU 快照主键
     * @return SPU 快照
     */
    @Override
    public GoodsRevisionSpu selectGoodsRevisionSpuByRevisionId(String revisionId)
    {
        return goodsRevisionSpuMapper.selectGoodsRevisionSpuByRevisionId(revisionId);
    }

    /**
     * 查询SPU 快照列表
     * 
     * @param goodsRevisionSpu SPU 快照
     * @return SPU 快照
     */
    @Override
    public List<GoodsRevisionSpu> selectGoodsRevisionSpuList(GoodsRevisionSpu goodsRevisionSpu)
    {
        return goodsRevisionSpuMapper.selectGoodsRevisionSpuList(goodsRevisionSpu);
    }

    /**
     * 新增SPU 快照
     * 
     * @param goodsRevisionSpu SPU 快照
     * @return 结果
     */
    @Override
    public int insertGoodsRevisionSpu(GoodsRevisionSpu goodsRevisionSpu)
    {
        return goodsRevisionSpuMapper.insertGoodsRevisionSpu(goodsRevisionSpu);
    }

    /**
     * 修改SPU 快照
     * 
     * @param goodsRevisionSpu SPU 快照
     * @return 结果
     */
    @Override
    public int updateGoodsRevisionSpu(GoodsRevisionSpu goodsRevisionSpu)
    {
        return goodsRevisionSpuMapper.updateGoodsRevisionSpu(goodsRevisionSpu);
    }

    /**
     * 批量删除SPU 快照
     * 
     * @param revisionIds 需要删除的SPU 快照主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRevisionSpuByRevisionIds(String[] revisionIds)
    {
        return goodsRevisionSpuMapper.deleteGoodsRevisionSpuByRevisionIds(revisionIds);
    }

    /**
     * 删除SPU 快照信息
     * 
     * @param revisionId SPU 快照主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRevisionSpuByRevisionId(String revisionId)
    {
        return goodsRevisionSpuMapper.deleteGoodsRevisionSpuByRevisionId(revisionId);
    }
}
