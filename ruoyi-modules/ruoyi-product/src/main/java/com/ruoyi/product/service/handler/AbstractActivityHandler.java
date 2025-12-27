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
 * 活动核算逻辑抽象处理器
 * 采用模板方法模式定义线性核算节点
 */
@Slf4j
public abstract class AbstractActivityHandler {

    @Autowired
    protected IGoodsService goodsService;

    @Autowired
    protected IGoodsRevisionService revisionService;

    @Autowired
    protected IGoodsRevisionItemService itemService;

    /**
     * 核心模板方法：定义线性的节点处理流程
     * * @param productId 外部商品ID
     * 
     * @param shopId     店铺ID
     * @param activityId 活动ID (由Consumer从消息扩展参数提取)
     * @return 处理结果
     */
    public final ItemProcessResult handle(String productId, String shopId, String activityId) {
        try {
            // 1. 定位商品
            Goods goods = getGoods(productId, shopId);
            if (goods == null) {
                return ItemProcessResult.fail("商品不存在");
            }

            // 2. 获取版本
            String revisionId = getRevisionId(goods.getGoodsId());
            if (revisionId == null) {
                return ItemProcessResult.fail("找不到有效商品版本");
            }

            // 3. 执行核算逻辑（根据模式选择）
            if (isSkuLevelActivity()) {
                // SKU 维度：遍历该版本下所有 SKU 进行核算
                return handleSkuLevelActivity(revisionId, shopId, activityId);
            } else {
                // SPU 维度：找到基准 SKU（如最低价）进行核算
                return handleSpuLevelActivity(revisionId, shopId, activityId);
            }

        } catch (Exception e) {
            log.error("活动核算节点执行异常: productId={}", productId, e);
            return ItemProcessResult.fail("系统异常: " + e.getMessage());
        }
    }

    /**
     * SPU 维度核算逻辑 (新人礼金、通用券等)
     */
    private ItemProcessResult handleSpuLevelActivity(String revisionId, String shopId, String activityId) {
        GoodsRevisionItem targetSku = getTargetSku(revisionId);
        if (targetSku == null)
            return ItemProcessResult.fail("版本下无SKU明细");

        Object config = matchConfig(targetSku);
        if (config == null)
            return ItemProcessResult.skip("标价不在配置区间内");

        Object activity = linkActivity(shopId, config, activityId);
        if (activity == null)
            return ItemProcessResult.fail("未找到关联活动");

        return saveResult(targetSku, activity, config);
    }

    /**
     * SKU 维度核算逻辑 (单品直降专用)
     */
    private ItemProcessResult handleSkuLevelActivity(String revisionId, String shopId, String activityId) {
        // 检索该版本下所有 SKU 明细
        List<GoodsRevisionItem> allSkus = itemService.listByRevisionId(revisionId);
        if (allSkus == null || allSkus.isEmpty())
            return ItemProcessResult.fail("版本下无SKU明细");

        int processedCount = 0;
        for (GoodsRevisionItem sku : allSkus) {
            Object config = matchConfig(sku); // 匹配 price_reference 等配置
            if (config != null) {
                Object activity = linkActivity(shopId, config, activityId);
                if (activity != null) {
                    saveResult(sku, activity, config);
                    processedCount++;
                }
            }
        }

        return processedCount > 0
                ? ItemProcessResult.success()
                : ItemProcessResult.skip("该商品所有SKU标价均无法匹配优惠配置");
    }

    // ==================== 默认节点实现（子类可重写） ====================

    /**
     * 根据商品ID定位 Goods 实体
     */
    protected Goods getGoods(String productId, String shopId) {
        // shopId 暂时不使用。
        return goodsService.getLatestByShopProductId(productId);
    }

    /**
     * 获取最新版本 ID
     */
    protected String getRevisionId(String goodsId) {
        // 使用标准的 lambdaQuery() 方法
        GoodsRevision revision = revisionService.lambdaQuery()
                .eq(GoodsRevision::getGoodsId, goodsId)
                .orderByDesc(GoodsRevision::getGmtCreate)
                .last("LIMIT 1")
                .one();
        return revision != null ? revision.getRevisionId() : null;
    }

    /**
     * 默认寻找标价最低的 SKU 节点
     */
    protected GoodsRevisionItem getTargetSku(String revisionId) {
        List<GoodsRevisionItem> items = itemService.listByRevisionId(revisionId);
        if (items == null || items.isEmpty()) {
            return null;
        }
        // 按标价升序排列，取第一个
        return items.stream()
                .min(Comparator.comparing(GoodsRevisionItem::getMarketPrice))
                .orElse(null);
    }

    // ==================== 必须实现的差异化节点 ====================
    /**
     * 区分核算维度的钩子，子类若是单品直降则返回 true
     */
    protected boolean isSkuLevelActivity() {
        return false;
    }

    /**
     * 节点：匹配配置（如 platform_promotion_config）
     */
    protected abstract Object matchConfig(GoodsRevisionItem sku);

    /**
     * 节点：关联活动实例（如 platform_promotion_activity）
     */
    protected abstract Object linkActivity(String shopId, Object config, String activityId);

    /**
     * 节点：持久化结果到活动商品表
     */
    protected abstract ItemProcessResult saveResult(GoodsRevisionItem sku, Object activity, Object config);
}