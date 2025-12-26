package com.ruoyi.product.service;

import java.util.List;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.GoodsRevision;

/**
 * 商品版本Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsRevisionService extends IBaseService<GoodsRevision> {
    /**
     * 根据商品ID查询已冻结(frozen)的版本
     * 
     * @param goodsId 商品ID
     * @return 冻结状态的版本列表
     */
    List<GoodsRevision> listFrozenByGoodsId(String goodsId);

    /**
     * 根据商品ID查询编辑中(editing)的版本
     * 
     * @param goodsId 商品ID
     * @return 编辑中状态的版本列表
     */
    List<GoodsRevision> listEditingByGoodsId(String goodsId);

    /**
     * 根据商品ID查询审核中(approving)的版本
     * 
     * @param goodsId 商品ID
     * @return 审核中状态的版本列表
     */
    List<GoodsRevision> listApprovingByGoodsId(String goodsId);

    /**
     * 根据版本ID列表批量查询
     * 
     * @param revisionIds 版本ID集合
     * @return 版本列表
     */
    List<GoodsRevision> listByRevisionIds(List<String> revisionIds);

    /**
     * 根据商品ID查询所有版本，并按创建时间倒序排列
     * 
     * @param goodsId 商品ID
     * @return 按时间倒序的版本列表
     */
    List<GoodsRevision> listByGoodsId(String goodsId);

    /**
     * 根据商品ID和状态列表筛选版本，并按修改时间倒序排列
     * 
     * @param goodsId    商品ID
     * @param statusList 状态代码列表
     * @return 符合条件且按修改时间倒序的版本列表
     */
    List<GoodsRevision> listByGoodsAndStatus(String goodsId, List<String> statusList);
}
