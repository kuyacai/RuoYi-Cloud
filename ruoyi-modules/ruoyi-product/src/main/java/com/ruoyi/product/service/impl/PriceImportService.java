package com.ruoyi.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.ItemTask;
import com.ruoyi.product.domain.PriceReference;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.domain.dto.SimpleProduct;
import com.ruoyi.product.enums.ItemTaskStatus;
import com.ruoyi.product.service.IGoodsRevisionItemService;
import com.ruoyi.product.service.IGoodsService;
import com.ruoyi.product.service.IItemTaskService;
import com.ruoyi.product.service.IPriceReferenceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 计算SKU市场标价服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PriceImportService {

    private final PlatformTransactionManager transactionManager;
    private final IGoodsService goodsService;
    private final IPriceReferenceService priceReferenceService;
    private final IGoodsRevisionItemService goodsRevisionItemService;
    private final IItemTaskService itemTaskService;

    /**
     * 新方法：处理单条SPU的价格信息。
     * 
     * @return ItemProcessResult 处理结果
     */
    public ItemProcessResult processSingleSpuPrice(SimpleProduct sp, String shopId) {
        // 复用原有的事务管理逻辑
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            /* 1. 基础校验 */
            if (!validateSPData(sp)) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("数据校验失败");
            }

            /* 2. 检查商品是否存在 */
            Goods goods = goodsService.getLatestByShopProductId(sp.getProductId());
            if (goods == null) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("找不到对应的商品信息。商品ID:" + sp.getProductId());
            }

            /* 3. 读取修改价格的sku */
            List<GoodsRevisionItem> skuList = goodsRevisionItemService.selectSkusNeedPriceUpdate(goods.getGoodsId());
            if (skuList == null || skuList.isEmpty()) {
                transactionManager.rollback(status);
                return ItemProcessResult.skip("找不到需要修改的的商品SKU信息。商品ID:" + sp.getProductId());
            }

            // 4.逐条修改sku的价格
            String taskId = "";
            for (GoodsRevisionItem sku : skuList) {
                long org_price = sku.getOrignialPrice();
                PriceReference priceRef = priceReferenceService.getByOriginalPrice(org_price);
                if (priceRef == null) {
                    transactionManager.rollback(status);
                    return ItemProcessResult.skip("找不到对应的参考价格信息。原价：" + org_price);
                } else {
                    sku.setMarketPrice(priceRef.getEffectiveMarkedPrice());
                    // 修改价格信息
                    goodsRevisionItemService.updateById(sku);
                }
                // 这一批sku，应该对应同一个task_id
                taskId = sku.getTaskId();
            }

            /* 5 & 6. 更新任务状态 */
            if (StringUtils.isNotEmpty(taskId)) {
                // 修改点：使用 getById 替代 selectItemTaskByTaskId
                ItemTask task = itemTaskService.getById(taskId);
                if (task != null) {
                    task.setTaskStatus(ItemTaskStatus.DONE);

                    // 重要：保持调用此方法以触发内部的计数器(task_counter)更新逻辑和消息发送
                    itemTaskService.updateItemTask(task);
                }
            }

            transactionManager.commit(status);
            return ItemProcessResult.success();

        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("处理价格失败: productId={}",
                    sp.getProductId(), e);
            return ItemProcessResult.fail(e.getMessage());
        }
    }

    /**
     * 校验商品数据
     */
    private boolean validateSPData(SimpleProduct sp) {
        if (StringUtils.isEmpty(sp.getProductId())) {
            return false;
        }
        // 可以添加更多校验逻辑
        return true;
    }
}
