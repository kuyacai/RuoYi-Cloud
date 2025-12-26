package com.ruoyi.product.service;

import java.util.List;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.GoodsRevisionSpu;

/**
 * SPU 快照Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsRevisionSpuService  extends IBaseService<GoodsRevisionSpu>
{
    /**
     * 根据版本ID集合批量查询SPU快照
     * @param revisionIds 版本ID列表
     * @return 快照列表
     */
    List<GoodsRevisionSpu> listByRevisionIds(List<String> revisionIds);

    /**
     * 查询指定商品下需要更新标题的SPU（涉及多表关联）
     * @param goodsId 商品ID
     * @return 带有任务信息的SPU快照列表
     */
    List<GoodsRevisionSpu> listNeedTitleUpdate(String goodsId);
}
