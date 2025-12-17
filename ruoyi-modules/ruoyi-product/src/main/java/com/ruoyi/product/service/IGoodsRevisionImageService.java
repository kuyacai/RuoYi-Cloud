package com.ruoyi.product.service;

import java.util.List;
import com.ruoyi.product.domain.GoodsRevisionImage;

/**
 * goods 图片Service接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface IGoodsRevisionImageService 
{
    /**
     * 查询goods 图片
     * 
     * @param imageId goods 图片主键
     * @return goods 图片
     */
    public GoodsRevisionImage selectGoodsRevisionImageByImageId(String imageId);

    /**
     * 查询goods 图片列表
     * 
     * @param goodsRevisionImage goods 图片
     * @return goods 图片集合
     */
    public List<GoodsRevisionImage> selectGoodsRevisionImageList(GoodsRevisionImage goodsRevisionImage);

    /**
     * 新增goods 图片
     * 
     * @param goodsRevisionImage goods 图片
     * @return 结果
     */
    public int insertGoodsRevisionImage(GoodsRevisionImage goodsRevisionImage);

    /**
     * 修改goods 图片
     * 
     * @param goodsRevisionImage goods 图片
     * @return 结果
     */
    public int updateGoodsRevisionImage(GoodsRevisionImage goodsRevisionImage);

    /**
     * 批量删除goods 图片
     * 
     * @param imageIds 需要删除的goods 图片主键集合
     * @return 结果
     */
    public int deleteGoodsRevisionImageByImageIds(String[] imageIds);

    /**
     * 删除goods 图片信息
     * 
     * @param imageId goods 图片主键
     * @return 结果
     */
    public int deleteGoodsRevisionImageByImageId(String imageId);

    /**
     * 根据 revisionId 和 sourceUrl 判断图片是否存在
     * 
     * @param revisionId 商品修订版ID
     * @param sourceUrl 图片源地址
     * @return true 存在，false 不存在
     */
    public boolean existsByRevisionIdAndSourceUrl(String revisionId, String sourceUrl);
}
