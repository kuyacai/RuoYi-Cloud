package com.ruoyi.product.mapper;

import com.ruoyi.product.core.mybatisplus.RootMapper;
import com.ruoyi.product.domain.GoodsRevisionSpu;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * SPU 快照Mapper接口
 */
public interface GoodsRevisionSpuMapper extends RootMapper<GoodsRevisionSpu> {
    /**
     * 查询需要标题更新的SPU列表
     * 对应 GoodsRevisionSpuMapper.xml 中的 selectNeedTitleUpdate
     */
    List<GoodsRevisionSpu> selectNeedTitleUpdate(@Param("goodsId") String goodsId);
}