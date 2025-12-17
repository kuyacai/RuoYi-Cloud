package com.ruoyi.product.mapper;

import java.util.List;
import com.ruoyi.product.domain.NewUserGiftProduct;

/**
 * 新用户礼包活动商品Mapper接口
 * 
 * @author Rupert
 * @date 2025-12-13
 */
public interface NewUserGiftProductMapper 
{
    /**
     * 查询新用户礼包活动商品
     * 
     * @param id 新用户礼包活动商品主键
     * @return 新用户礼包活动商品
     */
    public NewUserGiftProduct selectNewUserGiftProductById(String id);

    /**
     * 查询新用户礼包活动商品列表
     * 
     * @param newUserGiftProduct 新用户礼包活动商品
     * @return 新用户礼包活动商品集合
     */
    public List<NewUserGiftProduct> selectNewUserGiftProductList(NewUserGiftProduct newUserGiftProduct);

    /**
     * 新增新用户礼包活动商品
     * 
     * @param newUserGiftProduct 新用户礼包活动商品
     * @return 结果
     */
    public int insertNewUserGiftProduct(NewUserGiftProduct newUserGiftProduct);

    /**
     * 修改新用户礼包活动商品
     * 
     * @param newUserGiftProduct 新用户礼包活动商品
     * @return 结果
     */
    public int updateNewUserGiftProduct(NewUserGiftProduct newUserGiftProduct);

    /**
     * 删除新用户礼包活动商品
     * 
     * @param id 新用户礼包活动商品主键
     * @return 结果
     */
    public int deleteNewUserGiftProductById(String id);

    /**
     * 批量删除新用户礼包活动商品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNewUserGiftProductByIds(String[] ids);
}
