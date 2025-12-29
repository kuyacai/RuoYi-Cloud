package com.ruoyi.product.service.handler;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.dto.ItemProcessResult;
import com.ruoyi.product.service.IGoodsRevisionItemService;
import com.ruoyi.product.service.IGoodsRevisionService;
import com.ruoyi.product.service.IGoodsService;

import lombok.extern.slf4j.Slf4j;

/**
 * 活动核算逻辑抽象处理器 (重构版)
 * 核心逻辑：SPU 级准入校验 + SKU 级差异化执行
 */
@Slf4j
public abstract class AbstractActivityHandler {

    @Autowired
    protected IGoodsService goodsService;
    @Autowired
    protected IGoodsRevisionService revisionService;
    @Autowired
    protected IGoodsRevisionItemService itemService;

    public final ItemProcessResult handle(String productId, String shopId, String activityId) {
        try {
            // 1. 定位商品及版本
            Goods goods = goodsService.getLatestByShopProductId(productId);
            if (goods == null)
                return ItemProcessResult.fail("商品不存在");

            String revisionId = getRevisionId(goods.getGoodsId());
            if (revisionId == null)
                return ItemProcessResult.fail("找不到有效商品版本");

            // 2. 【核心】SPU 级冲突检查：禁止一个 SPU 的 SKU 分散在多个同类活动中
            ItemProcessResult conflictResult = checkSpuConflict(productId, shopId, activityId);
            if (!conflictResult.isSuccess()) {
                return conflictResult;
            }

            // 3. 执行差异化核算
            if (isSkuLevelActivity()) {
                return handleSkuLevelActivity(revisionId, shopId, activityId);
            } else {
                return handleSpuLevelActivity(revisionId, shopId, activityId);
            }

        } catch (Exception e) {
            log.error("核算异常: productId={}", productId, e);
            return ItemProcessResult.fail("系统异常: " + e.getMessage());
        }
    }

    /**
     * SPU 冲突检查逻辑
     */
    private ItemProcessResult checkSpuConflict(String shopProductId, String shopId, String activityId) {
        List<Object> activeRecords = findActiveRecordsBySpu(shopProductId, shopId);
        for (Object record : activeRecords) {
            String currentActivityId = getActivityIdFromRecord(record);
            if (!activityId.equals(currentActivityId)) {
                return ItemProcessResult.fail("冲突：该商品已有 SKU 在活动[" + currentActivityId + "]中处于启用状态");
            }
        }
        return ItemProcessResult.success();
    }

    /**
     * SPU 维度逻辑：以基准 SKU 进行核算
     */
    protected ItemProcessResult handleSpuLevelActivity(String revisionId, String shopId, String activityId) {
        GoodsRevisionItem targetSku = getTargetSku(revisionId);
        if (targetSku == null)
            return ItemProcessResult.fail("无 SKU 明细");

        Object config = matchConfig(targetSku);
        if (config == null)
            return ItemProcessResult.skip("标价不在配置区间内");

        Object activity = linkActivity(shopId, config, activityId);
        Object existing = findExistingRecord(targetSku.getShopProductId(), null, shopId);

        return saveResult(targetSku, activity, config, existing);
    }

    /**
     * SKU 维度逻辑：遍历核算，不满足配置的设为 REMOVED
     */
    protected ItemProcessResult handleSkuLevelActivity(String revisionId, String shopId, String activityId) {
        List<GoodsRevisionItem> allSkus = itemService.listByRevisionId(revisionId);
        int successCount = 0;

        for (GoodsRevisionItem sku : allSkus) {
            Object config = matchConfig(sku);
            Object activity = linkActivity(shopId, config, activityId);
            Object existing = findExistingRecord(sku.getShopProductId(), sku.getShopSkuId(), shopId);

            // 即使 config 为 null，也要调用 saveResult 内部将其状态设为 REMOVED
            saveResult(sku, activity, config, existing);
            if (config != null)
                successCount++;
        }

        return successCount > 0 ? ItemProcessResult.success() : ItemProcessResult.skip("所有 SKU 均不符合配置");
    }

    // ==================== 抽象节点 ====================

    protected abstract boolean isSkuLevelActivity();

    protected abstract Object matchConfig(GoodsRevisionItem sku);

    protected abstract Object linkActivity(String shopId, Object config, String activityId);

    /** 查找 SPU 下所有 ACTIVE 状态的记录 */
    protected abstract List<Object> findActiveRecordsBySpu(String shopProductId, String shopId);

    /** 查找特定唯一键的记录 (用于更新) */
    protected abstract Object findExistingRecord(String shopProductId, String shopSkuId, String shopId);

    protected abstract String getActivityIdFromRecord(Object record);

    /**
     * 保存结果
     * 
     * @param config 若为 null，子类应在实现中将 status 设为 REMOVED
     */
    protected abstract ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config,
            Object existingRecord);

    // ==================== 工具方法 ====================

    protected String getRevisionId(String goodsId) {
        GoodsRevision revision = revisionService.lambdaQuery()
                .eq(GoodsRevision::getGoodsId, goodsId)
                .orderByDesc(GoodsRevision::getGmtCreate).last("LIMIT 1").one();
        return revision != null ? revision.getRevisionId() : null;
    }

    protected GoodsRevisionItem getTargetSku(String revisionId) {
        List<GoodsRevisionItem> items = itemService.listByRevisionId(revisionId);
        return items == null ? null
                : items.stream().min(Comparator.comparing(GoodsRevisionItem::getMarketPrice)).orElse(null);
    }
}