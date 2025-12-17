package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.GoodsRevisionSpu;

/**
 * SPU 快照Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsRevisionSpuService 
{
    /**
     * 查询SPU 快照
     * 
     * @param revisionId SPU 快照主键
     * @return SPU 快照
     */
    public GoodsRevisionSpu selectGoodsRevisionSpuByRevisionId(String revisionId);

    /**
     * 查询SPU 快照列表
     * 
     * @param goodsRevisionSpu SPU 快照
     * @return SPU 快照集合
     */
    public List<GoodsRevisionSpu> selectGoodsRevisionSpuList(GoodsRevisionSpu goodsRevisionSpu);

    /**
     * 新增SPU 快照
     * 
     * @param goodsRevisionSpu SPU 快照
     * @return 结果
     */
    public int insertGoodsRevisionSpu(GoodsRevisionSpu goodsRevisionSpu);

    /**
     * 修改SPU 快照
     * 
     * @param goodsRevisionSpu SPU 快照
     * @return 结果
     */
    public int updateGoodsRevisionSpu(GoodsRevisionSpu goodsRevisionSpu);

    /**
     * 批量删除SPU 快照
     * 
     * @param revisionIds 需要删除的SPU 快照主键集合
     * @return 结果
     */
    public int deleteGoodsRevisionSpuByRevisionIds(String[] revisionIds);

    /**
     * 删除SPU 快照信息
     * 
     * @param revisionId SPU 快照主键
     * @return 结果
     */
    public int deleteGoodsRevisionSpuByRevisionId(String revisionId);
}
