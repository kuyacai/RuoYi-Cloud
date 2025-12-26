package com.ruoyi.product.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.product.app.display.GoodsVersionDetail;
import com.ruoyi.product.constant.ItemTaskStatus;
import com.ruoyi.product.constant.ItemTaskCode;

public interface IGoodsDisplayService {

    /**
     * 获取指定版本ID的版本
     * @param revisionId
     * @return
     */
    public GoodsVersionDetail getVersion(String revisionId);

    /**
     * 获取冻结版本
     * @param goodsId
     * @return
     */
    public GoodsVersionDetail getFrozenVersion(String goodsId);

    /**
     * 获取编辑中的版本
     * @param goodsId
     * @return
     */
    public GoodsVersionDetail getEditingVersion(String goodsId);


    /**
     * 获取审核中的版本
     * @param goodsId
     * @return
     */
    public GoodsVersionDetail getApprovingVersion(String goodsId);

    /**
     * 标题修改任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listTitleTask(ItemTaskStatus status);

    /**
     * 图片修改任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listImageTask(ItemTaskStatus status);

    /**
     * 属性修改任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listAttributeTask(ItemTaskStatus status);

    /**
     * 价格修改任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listPriceTask(ItemTaskStatus status);

    /**
     * 库存修改任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listStockTask(ItemTaskStatus status);

    /**
     * 规格修改任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listSpecTask(ItemTaskStatus status);

    /**
     * 视频修改任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listVideoTask(ItemTaskStatus status);

    /**
     * 同步上架任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listSyncListingTask(ItemTaskStatus status);

    /**
     * 同步库存任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param status, 任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listSyncInventoryTask(ItemTaskStatus status);

    /**
     * 通用任务列表
     * 显示：商品基础信息、主图一张、SPU标题信息、版本状态
     * 不填充：SKU详情、多张图片、详情图等
     * 约定：mainImages 列表只传第一张图（或最多3张），其他图片列表为空
     * @param taskCode 任务代码
     * @param status   任务状态, null表示全部
     * @return
     */
    public List<GoodsVersionDetail> listTask(ItemTaskCode taskCode, ItemTaskStatus status);

    /**
     * 任务详情
     * @param task_id
     * @return
     */
    public Map<String, GoodsVersionDetail> getTaskDetails(String task_id);

    /**
     * 商品详情通过商品ID
     * @param goods_id
     * @return
     */
    public Map<String, GoodsVersionDetail> getGoodsDetailsByGoodsId(String goods_id,List<String> lisRevStatus);

    /**
     * 商品详情通过本店商品ID
     * @param shopProductId
     * @return
     */
    public Map<String, GoodsVersionDetail> getGoodsDetailsByShopProductId(String shopProductId,List<String> lisRevStatus);

    /**
     * 商品详情通过来源ID
     * @param sourceId
     * @return
     */
    public Map<String, GoodsVersionDetail> getGoodsDetailsBySourceId(String sourceId,List<String> lisRevStatus);

}