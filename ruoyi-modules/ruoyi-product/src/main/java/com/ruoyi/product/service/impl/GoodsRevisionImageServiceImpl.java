package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.GoodsRevisionImageMapper;
import com.ruoyi.product.domain.GoodsRevisionImage;
import com.ruoyi.product.service.IGoodsRevisionImageService;

/**
 * goods 图片Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class GoodsRevisionImageServiceImpl implements IGoodsRevisionImageService 
{
    @Autowired
    private GoodsRevisionImageMapper goodsRevisionImageMapper;

    /**
     * 查询goods 图片
     * 
     * @param imageId goods 图片主键
     * @return goods 图片
     */
    @Override
    public GoodsRevisionImage selectGoodsRevisionImageByImageId(String imageId)
    {
        return goodsRevisionImageMapper.selectGoodsRevisionImageByImageId(imageId);
    }

    /**
     * 查询goods 图片列表
     * 
     * @param goodsRevisionImage goods 图片
     * @return goods 图片
     */
    @Override
    public List<GoodsRevisionImage> selectGoodsRevisionImageList(GoodsRevisionImage goodsRevisionImage)
    {
        return goodsRevisionImageMapper.selectGoodsRevisionImageList(goodsRevisionImage);
    }

    /**
     * 新增goods 图片
     * 
     * @param goodsRevisionImage goods 图片
     * @return 结果
     */
    @Override
    public int insertGoodsRevisionImage(GoodsRevisionImage goodsRevisionImage)
    {
        return goodsRevisionImageMapper.insertGoodsRevisionImage(goodsRevisionImage);
    }

    /**
     * 修改goods 图片
     * 
     * @param goodsRevisionImage goods 图片
     * @return 结果
     */
    @Override
    public int updateGoodsRevisionImage(GoodsRevisionImage goodsRevisionImage)
    {
        return goodsRevisionImageMapper.updateGoodsRevisionImage(goodsRevisionImage);
    }

    /**
     * 批量删除goods 图片
     * 
     * @param imageIds 需要删除的goods 图片主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRevisionImageByImageIds(String[] imageIds)
    {
        return goodsRevisionImageMapper.deleteGoodsRevisionImageByImageIds(imageIds);
    }

    /**
     * 删除goods 图片信息
     * 
     * @param imageId goods 图片主键
     * @return 结果
     */
    @Override
    public int deleteGoodsRevisionImageByImageId(String imageId)
    {
        return goodsRevisionImageMapper.deleteGoodsRevisionImageByImageId(imageId);
    }


    /**
     * 根据 revisionId 和 sourceUrl 判断图片是否存在
     * 
     * @param revisionId 商品修订版ID
     * @param sourceUrl 图片源地址
     * @return true 存在，false 不存在
     */
    @Override
    public boolean existsByRevisionIdAndSourceUrl(String revisionId, String sourceUrl) {
        int count = goodsRevisionImageMapper.countByRevisionIdAndSourceUrl(revisionId, sourceUrl);
        return count > 0;
    }
}
