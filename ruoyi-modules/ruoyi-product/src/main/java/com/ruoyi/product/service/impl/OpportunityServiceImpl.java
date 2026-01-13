package com.ruoyi.product.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.Benefit;
import com.ruoyi.product.domain.BenefitRequirement;
import com.ruoyi.product.domain.Opportunity;
import com.ruoyi.product.mapper.OpportunityMapper;
import com.ruoyi.product.service.IBenefitRequirementService;
import com.ruoyi.product.service.IBenefitService;
import com.ruoyi.product.service.IOpportunityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpportunityServiceImpl extends BaseServiceImpl<OpportunityMapper, Opportunity>
                implements IOpportunityService {

        private final IBenefitService benefitService;
        private final IBenefitRequirementService requirementService;

        @Override
        public List<Opportunity> selectOpportunitiesWithDetails(List<String> clueIds) {
                if (CollectionUtils.isEmpty(clueIds)) {
                        return new ArrayList<>();
                }

                // 1. 批量查询机会基本信息
                List<Opportunity> opportunities = this.listByIds(clueIds);
                if (CollectionUtils.isEmpty(opportunities)) {
                        return opportunities;
                }

                // 2. 批量查询所有benefits
                List<Benefit> allBenefits = benefitService.lambdaQuery()
                                .in(Benefit::getClueId, clueIds)
                                .list();

                if (CollectionUtils.isEmpty(allBenefits)) {
                        // 如果没有benefits，设置空列表并返回
                        opportunities.forEach(opp -> opp.setBenefits(new ArrayList<>()));
                        return opportunities;
                }

                // 3. 批量查询所有requirements
                List<String> benefitIds = allBenefits.stream()
                                .map(Benefit::getBenefitId)
                                .distinct()
                                .collect(Collectors.toList());

                List<BenefitRequirement> allRequirements = requirementService.lambdaQuery()
                                .in(BenefitRequirement::getBenefitId, benefitIds)
                                .list();

                // 4. 构建映射关系
                Map<String, List<BenefitRequirement>> requirementMap = allRequirements.stream()
                                .collect(Collectors.groupingBy(BenefitRequirement::getBenefitId));

                Map<String, List<Benefit>> benefitMap = allBenefits.stream()
                                .collect(Collectors.groupingBy(Benefit::getClueId));

                // 5. 组装数据
                opportunities.forEach(opportunity -> {
                        List<Benefit> benefits = benefitMap.get(opportunity.getClueId());
                        if (CollectionUtils.isNotEmpty(benefits)) {
                                benefits.forEach(benefit -> benefit.setRequirements(
                                                requirementMap.getOrDefault(benefit.getBenefitId(),
                                                                new ArrayList<>())));
                                opportunity.setBenefits(benefits);
                        } else {
                                opportunity.setBenefits(new ArrayList<>());
                        }
                });

                return opportunities;
        }

        @Override
        public Opportunity getOpportunityWithDetails(String clueId) {
                List<Opportunity> opportunities = this
                                .selectOpportunitiesWithDetails(Collections.singletonList(clueId));
                return CollectionUtils.isNotEmpty(opportunities) ? opportunities.get(0) : null;
        }

        @Override
        public List<String> selectOpportunityPageIds(Opportunity opportunity) {
                LambdaQueryWrapper<Opportunity> wrapper = buildQueryWrapper(opportunity);
                wrapper.select(Opportunity::getClueId); // 只查询clueId
                return this.listObjs(wrapper, clueId -> (String) clueId);
        }

        private LambdaQueryWrapper<Opportunity> buildQueryWrapper(Opportunity opportunity) {
                LambdaQueryWrapper<Opportunity> wrapper = new LambdaQueryWrapper<>();

                if (StringUtils.isNotBlank(opportunity.getClueTitle())) {
                        wrapper.like(Opportunity::getClueTitle, opportunity.getClueTitle());
                }
                // TODO 其他查询条件待定

                wrapper.orderByDesc(Opportunity::getUpdatedAtUtc);
                return wrapper;
        }

        /**
         * 分页查询机会（不带关联数据）
         */
        @Override
        public List<Opportunity> selectOpportunityPage(Opportunity opportunity) {
                LambdaQueryWrapper<Opportunity> wrapper = buildQueryWrapper(opportunity);
                return this.list(wrapper);
        }

        /**
         * 删除单个商机（级联删除）
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        public boolean deleteOpportunityWithRelated(String clueId) {
                log.info("开始删除商机及关联数据，商机ID: {}", clueId);

                try {
                        // 1. 先查询该商机下所有的权益ID
                        List<Benefit> benefits = benefitService.lambdaQuery()
                                        .eq(Benefit::getClueId, clueId)
                                        .select(Benefit::getBenefitId)
                                        .list();

                        List<String> benefitIds = benefits.stream()
                                        .map(Benefit::getBenefitId)
                                        .collect(Collectors.toList());

                        // 2. 删除权益要求（如果有权益的话）
                        if (CollectionUtils.isNotEmpty(benefitIds)) {
                                boolean reqDeleted = requirementService.lambdaUpdate()
                                                .in(BenefitRequirement::getBenefitId, benefitIds)
                                                .remove();
                                log.info("删除权益要求，共删除 {} 条数据", benefitIds.size());
                        }

                        // 3. 删除权益
                        boolean benefitDeleted = benefitService.lambdaUpdate()
                                        .eq(Benefit::getClueId, clueId)
                                        .remove();
                        log.info("删除权益，商机ID: {}, 删除结果: {}", clueId, benefitDeleted);

                        // 4. 删除商机
                        boolean opportunityDeleted = this.lambdaUpdate()
                                        .eq(Opportunity::getClueId, clueId)
                                        .remove();
                        log.info("删除商机，商机ID: {}, 删除结果: {}", clueId, opportunityDeleted);

                        return opportunityDeleted;

                } catch (Exception e) {
                        log.error("删除商机及关联数据失败，商机ID: {}", clueId, e);
                        throw new RuntimeException("删除商机失败", e);
                }
        }

        /**
         * 批量删除商机（级联删除）
         */
        @Override
        @Transactional(rollbackFor = Exception.class)
        public boolean batchDeleteOpportunityWithRelated(List<String> clueIds) {
                if (CollectionUtils.isEmpty(clueIds)) {
                        log.warn("批量删除商机ID列表为空");
                        return false;
                }

                log.info("开始批量删除商机及关联数据，商机IDs: {}", clueIds);

                try {
                        // 1. 先查询这些商机下所有的权益
                        List<Benefit> allBenefits = benefitService.lambdaQuery()
                                        .in(Benefit::getClueId, clueIds)
                                        .select(Benefit::getBenefitId)
                                        .list();

                        List<String> allBenefitIds = allBenefits.stream()
                                        .map(Benefit::getBenefitId)
                                        .collect(Collectors.toList());

                        // 2. 批量删除权益要求
                        if (CollectionUtils.isNotEmpty(allBenefitIds)) {
                                boolean reqDeleted = requirementService.lambdaUpdate()
                                                .in(BenefitRequirement::getBenefitId, allBenefitIds)
                                                .remove();
                                log.info("批量删除权益要求，涉及权益数量: {}", allBenefitIds.size());
                        }

                        // 3. 批量删除权益
                        boolean benefitsDeleted = benefitService.lambdaUpdate()
                                        .in(Benefit::getClueId, clueIds)
                                        .remove();
                        log.info("批量删除权益，涉及商机数量: {}, 删除结果: {}", clueIds.size(), benefitsDeleted);

                        // 4. 批量删除商机
                        boolean opportunitiesDeleted = this.lambdaUpdate()
                                        .in(Opportunity::getClueId, clueIds)
                                        .remove();
                        log.info("批量删除商机，数量: {}, 删除结果: {}", clueIds.size(), opportunitiesDeleted);

                        return opportunitiesDeleted;

                } catch (Exception e) {
                        log.error("批量删除商机及关联数据失败，商机IDs: {}", clueIds, e);
                        throw new RuntimeException("批量删除商机失败", e);
                }
        }

        /**
         * 带验证的删除（删除前检查是否存在关联业务）
         */
        public AjaxResult deleteOpportunityWithValidation(String clueId) {
                // 1. 检查商机是否存在
                Opportunity opportunity = this.lambdaQuery()
                                .eq(Opportunity::getClueId, clueId)
                                .one();

                if (opportunity == null) {
                        return AjaxResult.error("商机不存在");
                }

                // 2. 检查商机状态（根据业务需求添加）
                /**
                 * if (opportunity.getStatus() != null && opportunity.getStatus() == 2) {
                 * // 假设状态2是"进行中"，不允许删除
                 * return AjaxResult.error("进行中的商机不允许删除");
                 * }
                 */

                // 3. 执行删除
                try {
                        boolean success = deleteOpportunityWithRelated(clueId);
                        if (success) {
                                return AjaxResult.success("删除成功");
                        } else {
                                return AjaxResult.error("删除失败");
                        }
                } catch (Exception e) {
                        log.error("删除商机失败，商机ID: {}", clueId, e);
                        return AjaxResult.error("删除失败: " + e.getMessage());
                }
        }

        /**
         * 带验证的批量删除
         */
        public AjaxResult batchDeleteOpportunityWithValidation(List<String> clueIds) {
                if (CollectionUtils.isEmpty(clueIds)) {
                        return AjaxResult.error("请选择要删除的商机");
                }

                // 1. 检查所有商机是否存在且状态允许删除
                List<Opportunity> opportunities = this.lambdaQuery()
                                .in(Opportunity::getClueId, clueIds)
                                .list();

                if (opportunities.size() != clueIds.size()) {
                        return AjaxResult.error("部分商机不存在");
                }

                // 检查状态
                List<Opportunity> invalidOpportunities = opportunities.stream()
                                // .filter(opp -> opp.getStatus() != null && opp.getStatus() == 2)
                                .collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(invalidOpportunities)) {
                        String invalidIds = invalidOpportunities.stream()
                                        .map(Opportunity::getClueId)
                                        .collect(Collectors.joining(","));
                        return AjaxResult.error("以下商机进行中，不允许删除: " + invalidIds);
                }

                // 2. 执行批量删除
                try {
                        boolean success = batchDeleteOpportunityWithRelated(clueIds);
                        if (success) {
                                return AjaxResult.success("批量删除成功，共删除 " + clueIds.size() + " 条记录");
                        } else {
                                return AjaxResult.error("批量删除失败");
                        }
                } catch (Exception e) {
                        log.error("批量删除商机失败，商机IDs: {}", clueIds, e);
                        return AjaxResult.error("批量删除失败: " + e.getMessage());
                }
        }

}
