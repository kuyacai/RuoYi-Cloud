package com.ruoyi.product.service;

import java.util.List;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.GoodsRevisionImage;

/**
 * goods 图片Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsRevisionImageService extends IBaseService<GoodsRevisionImage> {

    /**
     * 根据版本ID和来源URL统计图片数量
     * 
     * @param revisionId 版本ID
     * @param sourceUrl  来源URL
     * @return 匹配的记录数
     */
    int countByRevisionIdAndSourceUrl(String revisionId, String sourceUrl);

    /**
     * 根据版本ID集合批量查询图片快照
     * 
     * @param revisionIds 版本ID列表
     * @return 图片快照列表
     */
    List<GoodsRevisionImage> listByRevisionIds(List<String> revisionIds);

    /**
     * 根据版本ID查询图片快照列表
     * 
     * @param revisionId 版本ID
     * @return 该版本下的所有图片
     */
    List<GoodsRevisionImage> listByRevisionId(String revisionId);
}
