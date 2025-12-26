package com.ruoyi.product.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.app.display.GoodsVersionAssembler;
import com.ruoyi.product.app.display.GoodsVersionDetail;
import com.ruoyi.product.constant.ItemTaskCode;
import com.ruoyi.product.constant.ItemTaskStatus;
import com.ruoyi.product.domain.Goods;
import com.ruoyi.product.domain.GoodsRevision;
import com.ruoyi.product.domain.GoodsRevisionImage;
import com.ruoyi.product.domain.GoodsRevisionItem;
import com.ruoyi.product.domain.GoodsRevisionSpu;
import com.ruoyi.product.domain.ItemTask;
import com.ruoyi.product.service.IGoodsDisplayService;
import com.ruoyi.product.service.IGoodsRevisionService;
import com.ruoyi.product.service.IGoodsRevisionSpuService;
import com.ruoyi.product.service.IGoodsService;
import com.ruoyi.product.service.IGoodsRevisionImageService;
import com.ruoyi.product.service.IGoodsRevisionItemService;
import com.ruoyi.product.service.IItemTaskService;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsDisplayService implements IGoodsDisplayService {

    private final IGoodsService goodsService;

    private final IGoodsRevisionService revisionService;

    private final IGoodsRevisionSpuService spuService;

    private final IGoodsRevisionItemService itemService;

    private final IGoodsRevisionImageService imageService;

    private final GoodsVersionAssembler assembler;

    private final IItemTaskService itemTaskService;

    @Override
    public GoodsVersionDetail getFrozenVersion(String goodsId) {
        Goods goods = goodsService.getById(goodsId);
        if (goods == null) {
            return null; // 或抛自定义异常
        }
        List<GoodsRevision> revisionList = revisionService.listFrozenByGoodsId(goodsId);
        if (revisionList == null || revisionList.isEmpty()) {
            return null; // 或抛自定义异常
        }
        GoodsRevision revision = revisionList.get(0);
        GoodsRevisionSpu spu = spuService.getById(revision.getRevisionId());
        List<GoodsRevisionItem> items = itemService.listByRevisionId(revision.getRevisionId());
        List<GoodsRevisionImage> images = imageService.listByRevisionId(revision.getRevisionId());

        return assembler.assemble(goods, revision, spu, items, images);
    }

    @Override
    public GoodsVersionDetail getApprovingVersion(String goodsId) {
        Goods goods = goodsService.getById(goodsId);
        if (goods == null) {
            return null; // 或抛自定义异常
        }
        List<GoodsRevision> revisionList = revisionService.listApprovingByGoodsId(goodsId);
        if (revisionList == null || revisionList.isEmpty()) {
            return null; // 或抛自定义异常
        }
        GoodsRevision revision = revisionList.get(0);
        GoodsRevisionSpu spu = spuService.getById(revision.getRevisionId());
        List<GoodsRevisionItem> items = itemService.listByRevisionId(revision.getRevisionId());
        List<GoodsRevisionImage> images = imageService.listByRevisionId(revision.getRevisionId());

        return assembler.assemble(goods, revision, spu, items, images);
    }

    @Override
    public GoodsVersionDetail getEditingVersion(String goodsId) {
        Goods goods = goodsService.getById(goodsId);
        if (goods == null) {
            return null; // 或抛自定义异常
        }
        List<GoodsRevision> revisionList = revisionService.listEditingByGoodsId(goodsId);
        if (revisionList == null || revisionList.isEmpty()) {
            return null; // 或抛自定义异常
        }
        GoodsRevision revision = revisionList.get(0);
        GoodsRevisionSpu spu = spuService.getById(revision.getRevisionId());
        List<GoodsRevisionItem> items = itemService.listByRevisionId(revision.getRevisionId());
        List<GoodsRevisionImage> images = imageService.listByRevisionId(revision.getRevisionId());

        return assembler.assemble(goods, revision, spu, items, images);
    }

    @Override
    public GoodsVersionDetail getVersion(String revisionId) {
        GoodsRevision revision = revisionService.getById(revisionId);
        if (revision == null) {
            return null; // 或抛自定义异常
        }
        String goodsId = revision.getGoodsId();
        Goods goods = goodsService.getById(goodsId);
        if (goods == null) {
            return null; // 或抛自定义异常
        }
        GoodsRevisionSpu spu = spuService.getById(revision.getRevisionId());
        List<GoodsRevisionItem> items = itemService.listByRevisionId(revision.getRevisionId());
        List<GoodsRevisionImage> images = imageService.listByRevisionId(revision.getRevisionId());

        return assembler.assemble(goods, revision, spu, items, images);
    }

    @Override
    public Map<String, GoodsVersionDetail> getGoodsDetailsByGoodsId(String goods_id, List<String> lisRevStatus) {
        Goods goods = goodsService.getById(goods_id);
        if (goods == null) {
            return null; // 或抛自定义异常
        }
        List<GoodsRevision> revisions = revisionService.listByGoodsAndStatus(goods_id, lisRevStatus);
        if (revisions == null || revisions.isEmpty()) {
            return null; // 或抛自定义异常
        }
        Map<String, GoodsVersionDetail> map = new HashMap<>();
        for (GoodsRevision rev : revisions) {
            String key = rev.getRevStatus();
            GoodsVersionDetail detail = getVersion(rev.getRevisionId());
            map.put(key, detail);
        }
        return map;
    }

    @Override
    public Map<String, GoodsVersionDetail> getGoodsDetailsByShopProductId(String shopProductId,
            List<String> lisRevStatus) {
        Goods goods = goodsService.getLatestByShopProductId(shopProductId);
        if (goods == null) {
            return null; // 或抛自定义异常
        }
        List<GoodsRevision> revisions = revisionService.listByGoodsAndStatus(goods.getGoodsId(), lisRevStatus);
        if (revisions == null || revisions.isEmpty()) {
            return null; // 或抛自定义异常
        }
        Map<String, GoodsVersionDetail> map = new HashMap<>();
        for (GoodsRevision rev : revisions) {
            String key = rev.getRevStatus();
            GoodsVersionDetail detail = getVersion(rev.getRevisionId());
            map.put(key, detail);
        }
        return map;
    }

    @Override
    public Map<String, GoodsVersionDetail> getGoodsDetailsBySourceId(String sourceId, List<String> lisRevStatus) {
        Goods goods = goodsService.selectGoodsBySourceId(sourceId);
        if (goods == null) {
            return null; // 或抛自定义异常
        }
        List<GoodsRevision> revisions = revisionService.listByGoodsAndStatus(goods.getGoodsId(), lisRevStatus);
        if (revisions == null || revisions.isEmpty()) {
            return null; // 或抛自定义异常
        }
        Map<String, GoodsVersionDetail> map = new HashMap<>();
        for (GoodsRevision rev : revisions) {
            String key = rev.getRevStatus();
            GoodsVersionDetail detail = getVersion(rev.getRevisionId());
            map.put(key, detail);
        }
        return map;
    }

    @Override
    public Map<String, GoodsVersionDetail> getTaskDetails(String task_id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GoodsVersionDetail> listAttributeTask(ItemTaskStatus status) {

        return listTask(ItemTaskCode.EDIT_ATTRIBUTE, status);
    }

    @Override
    public List<GoodsVersionDetail> listImageTask(ItemTaskStatus status) {
        return listTask(ItemTaskCode.EDIT_IMAGE, status);
    }

    @Override
    public List<GoodsVersionDetail> listPriceTask(ItemTaskStatus status) {
        return listTask(ItemTaskCode.EDIT_PRICE, status);
    }

    @Override
    public List<GoodsVersionDetail> listSpecTask(ItemTaskStatus status) {
        return listTask(ItemTaskCode.EDIT_SPEC, status);
    }

    @Override
    public List<GoodsVersionDetail> listStockTask(ItemTaskStatus status) {

        return listTask(ItemTaskCode.EDIT_STOCK, status);
    }

    @Override
    public List<GoodsVersionDetail> listSyncInventoryTask(ItemTaskStatus status) {

        return listTask(ItemTaskCode.SYNC_INVENTORY, status);
    }

    @Override
    public List<GoodsVersionDetail> listSyncListingTask(ItemTaskStatus status) {

        return listTask(ItemTaskCode.SYNC_LISTING, status);
    }

    @Override
    public List<GoodsVersionDetail> listTask(ItemTaskCode taskCode, ItemTaskStatus status) {
        // 1. 查询任务
        List<ItemTask> tasks = queryTasks(taskCode, status);

        // 2. 根据 biz_id（revisionId）批量取版本数据
        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> revisionIds = tasks.stream()
                .map(ItemTask::getBizId)
                .distinct()
                .collect(Collectors.toList());

        // 3. 一次性把需要的数据拿回来（Redis 缓存会自动命中）
        List<GoodsRevision> revisions = revisionService.listByRevisionIds(revisionIds);

        // 创建 revisionId -> GoodsRevision 映射
        Map<String, GoodsRevision> revisionMap = revisions.stream()
                .collect(Collectors.toMap(GoodsRevision::getRevisionId, v -> v));

        List<String> goods_ids = revisions.stream()
                .map(GoodsRevision::getGoodsId)
                .distinct()
                .collect(Collectors.toList());

        // 3. 并行查询三部分数据
        CompletableFuture<Map<String, Goods>> goodsFuture = CompletableFuture.supplyAsync(
                () -> goodsService.listByBatchIds(goods_ids).stream()
                        .collect(Collectors.toMap(Goods::getGoodsId, v -> v)));

        CompletableFuture<Map<String, GoodsRevisionSpu>> spuFuture = CompletableFuture.supplyAsync(
                () -> spuService.listByRevisionIds(revisionIds).stream()
                        .collect(Collectors.toMap(GoodsRevisionSpu::getRevisionId, v -> v)));

        CompletableFuture<Map<String, GoodsRevisionImage>> mainImageFuture = CompletableFuture.supplyAsync(
                () -> imageService.listByRevisionIds(revisionIds).stream()
                        .collect(Collectors.toMap(
                                GoodsRevisionImage::getRevisionId,
                                v -> v,
                                (v1, v2) -> v1))); // 重复取第一个

        // 等待所有查询完成
        CompletableFuture.allOf(goodsFuture, spuFuture, mainImageFuture).join();

        Map<String, Goods> goodsMap = goodsFuture.join();
        Map<String, GoodsRevisionSpu> spuMap = spuFuture.join();
        Map<String, GoodsRevisionImage> mainImageMap = mainImageFuture.join();
        // 4. 内存拼装
        // 4. 组装结果
        List<GoodsVersionDetail> result = new ArrayList<>();
        for (GoodsRevision rev : revisions) {
            Goods goods = goodsMap.get(rev.getGoodsId());
            GoodsRevisionSpu spu = spuMap.get(rev.getRevisionId());

            // 只获取主图（一张）
            GoodsRevisionImage mainImage = mainImageMap.get(rev.getRevisionId());
            List<GoodsRevisionImage> mainImages = mainImage != null
                    ? Collections.singletonList(mainImage)
                    : Collections.emptyList();

            // 创建 GoodsVersionDetail（其他图片列表为空）
            GoodsVersionDetail detail = GoodsVersionDetail.builder()
                    .goods(goods)
                    .revision(rev)
                    .spu(spu)
                    .mainImages(mainImages) // 只传一张主图
                    .main34Images(Collections.emptyList()) // 列表页不需要
                    .whiteImages(Collections.emptyList())
                    .guideImages(Collections.emptyList())
                    .descImages(Collections.emptyList())
                    .skuBundles(Collections.emptyList()) // 列表页不需要SKU详情
                    .build();

            result.add(detail);
        }
        return result;
    }

    @Override
    public List<GoodsVersionDetail> listTitleTask(ItemTaskStatus status) {
        return listTask(ItemTaskCode.EDIT_TITLE, status);
    }

    @Override
    public List<GoodsVersionDetail> listVideoTask(ItemTaskStatus status) {
        return listTask(ItemTaskCode.EDIT_VIDEO, status);
    }

    /**
     * 根据任务类型和状态查询任务列表
     * * @param taskCode 任务类型枚举
     * 
     * @param status 任务状态枚举
     * @return 任务列表
     */
    private List<ItemTask> queryTasks(ItemTaskCode taskCode, ItemTaskStatus status) {
        // 使用 LambdaQueryWrapper 替代对象传参查询，解决 selectItemTaskList 未定义的问题
        return itemTaskService.list(new LambdaQueryWrapper<ItemTask>()
                .eq(taskCode != null, ItemTask::getTaskCode, taskCode.getCode())
                .eq(status != null, ItemTask::getTaskStatus, status.getCode()));
    }
}
