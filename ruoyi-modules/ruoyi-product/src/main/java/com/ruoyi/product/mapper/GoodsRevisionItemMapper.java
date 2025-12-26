package com.ruoyi.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.product.core.mybatisplus.RootMapper;
import com.ruoyi.product.domain.GoodsRevisionItem;

/**
 * SKU 快照Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface GoodsRevisionItemMapper extends RootMapper<GoodsRevisionItem> {
    /**
     * 查询需要修改价格的spu列表
     * 对应 GoodsRevisionItemMapper.xml 中的 selectSkusNeedPriceUpdate
     */
    List<GoodsRevisionItem> selectSkusNeedPriceUpdate(@Param("goodsId") String goodsId);
}
