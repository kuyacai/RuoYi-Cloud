package com.ruoyi.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.product.constant.ItemTaskStatus;
import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.domain.GoodsRevisionSpu;
import com.ruoyi.product.domain.ItemTask;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.ProductTitle;
import com.ruoyi.product.service.IGoodsRevisionSpuService;
import com.ruoyi.product.service.IGoodsService;
import com.ruoyi.product.service.IItemTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 计算SKU市场标价服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ModifyTitleService {

    private final PlatformTransactionManager transactionManager;
    private final IGoodsService goodsService;
    private final IGoodsRevisionSpuService goodsRevisionSpuService;
    private final IItemTaskService itemTaskService;

    /**
     * 新方法：处理单条SPU的价格信息。
     * 
     * @return ItemProcessResult 处理结果
     */
    public ItemProcessResult processSingleSpuTitle(ProductTitle pt, String shopId) {
        // 复用原有的事务管理逻辑
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            /* 1. 基础校验 */
            if (!validateSPData(pt)) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("数据校验失败");
            }

            /* 2. 检查商品是否存在 */
            Goods goods = goodsService.getLatestByShopProductId(pt.getProductId());
            if (goods == null) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("找不到对应的商品信息。商品ID:" + pt.getProductId());
            }

            /* 3. 读取修改价格的sku */
            List<GoodsRevisionSpu> spuList = goodsRevisionSpuService.listNeedTitleUpdate(goods.getGoodsId());
            if (spuList == null || spuList.isEmpty()) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("找不到需要修改的的商品SKU信息。商品ID:" + pt.getProductId());
            }

            // 4.逐条修改sku的价格
            String taskId = "";
            for (GoodsRevisionSpu spu : spuList) {
                spu.setNewTitle(pt.getNewTitle());
                spu.setGuideShortTitle(pt.getGuideShortTitle());
                spu.setSearchKeywords(pt.getSearchKeywords());
                spu.setVideoScript(pt.getVideoScript());

                goodsRevisionSpuService.updateById(spu);
                // 这一批sku，应该对应同一个task_id
                taskId = spu.getTaskId();
            }
            /* 6. 更新任务状态 */
            if (StringUtils.isNotEmpty(taskId)) {
                // 3. 使用 getById 获取任务对象
                ItemTask task = itemTaskService.getById(taskId);
                if (task != null) {
                    task.setTaskStatus(ItemTaskStatus.DONE.getCode());
                    // 4. 保持调用 updateItemTask 以触发内部计数器和异步消息逻辑
                    itemTaskService.updateItemTask(task);
                }
            }

            transactionManager.commit(status);
            return ItemProcessResult.success();

        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("更新标题失败: productId={}",
                    pt.getProductId(), e);
            return ItemProcessResult.fail(e.getMessage());
        }
    }

    /**
     * 校验商品数据
     */
    private boolean validateSPData(ProductTitle pt) {
        if (StringUtils.isEmpty(pt.getProductId())) {
            return false;
        }
        if (StringUtils.isEmpty(pt.getNewTitle())) {
            return false;
        }
        if (StringUtils.isEmpty(pt.getGuideShortTitle())) {
            return false;
        }
        // 可以添加更多校验逻辑
        return true;
    }
}
