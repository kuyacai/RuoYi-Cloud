package com.ruoyi.product.service;

import java.util.List;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.Opportunity;

public interface IOpportunityService extends IBaseService<Opportunity> {

    Opportunity getOpportunityWithDetails(String clueId);

    List<Opportunity> selectOpportunitiesWithDetails(List<String> clueIds);

    List<String> selectOpportunityPageIds(Opportunity opportunity);

    /**
     * 没有关联数据
     * 
     * @param opportunity
     * @return
     */
    List<Opportunity> selectOpportunityPage(Opportunity opportunity);

    /**
     * 删除商机（级联删除关联的权益和权益要求）
     * 
     * @param clueId 商机ID
     * @return 是否删除成功
     */
    boolean deleteOpportunityWithRelated(String clueId);

    /**
     * 批量删除商机（级联删除）
     * 
     * @param clueIds 商机ID列表
     * @return 是否全部删除成功
     */
    boolean batchDeleteOpportunityWithRelated(List<String> clueIds);
}
