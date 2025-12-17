package com.ruoyi.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.product.mapper.NewUserGiftProductMapper;
import com.ruoyi.product.domain.NewUserGiftProduct;
import com.ruoyi.product.service.INewUserGiftProductService;

/**
 * 新用户礼包活动商品Service业务层处理
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Service
public class NewUserGiftProductServiceImpl implements INewUserGiftProductService 
{
    @Autowired
    private NewUserGiftProductMapper newUserGiftProductMapper;

    /**
     * 查询新用户礼包活动商品
     * 
     * @param id 新用户礼包活动商品主键
     * @return 新用户礼包活动商品
     */
    @Override
    public NewUserGiftProduct selectNewUserGiftProductById(String id)
    {
        return newUserGiftProductMapper.selectNewUserGiftProductById(id);
    }

    /**
     * 查询新用户礼包活动商品列表
     * 
     * @param newUserGiftProduct 新用户礼包活动商品
     * @return 新用户礼包活动商品
     */
    @Override
    public List<NewUserGiftProduct> selectNewUserGiftProductList(NewUserGiftProduct newUserGiftProduct)
    {
        return newUserGiftProductMapper.selectNewUserGiftProductList(newUserGiftProduct);
    }

    /**
     * 新增新用户礼包活动商品
     * 
     * @param newUserGiftProduct 新用户礼包活动商品
     * @return 结果
     */
    @Override
    public int insertNewUserGiftProduct(NewUserGiftProduct newUserGiftProduct)
    {
        return newUserGiftProductMapper.insertNewUserGiftProduct(newUserGiftProduct);
    }

    /**
     * 修改新用户礼包活动商品
     * 
     * @param newUserGiftProduct 新用户礼包活动商品
     * @return 结果
     */
    @Override
    public int updateNewUserGiftProduct(NewUserGiftProduct newUserGiftProduct)
    {
        return newUserGiftProductMapper.updateNewUserGiftProduct(newUserGiftProduct);
    }

    /**
     * 批量删除新用户礼包活动商品
     * 
     * @param ids 需要删除的新用户礼包活动商品主键
     * @return 结果
     */
    @Override
    public int deleteNewUserGiftProductByIds(String[] ids)
    {
        return newUserGiftProductMapper.deleteNewUserGiftProductByIds(ids);
    }

    /**
     * 删除新用户礼包活动商品信息
     * 
     * @param id 新用户礼包活动商品主键
     * @return 结果
     */
    @Override
    public int deleteNewUserGiftProductById(String id)
    {
        return newUserGiftProductMapper.deleteNewUserGiftProductById(id);
    }
}
