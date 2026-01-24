package com.ruoyi.product.service;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.WfNodeDefinition;

public interface IWfNodeDefinitionService extends IBaseService<WfNodeDefinition> {

    /**
     * 对指定流程下的所有节点重新排序（保证 1,2,3... 连续）
     */
    void resortNodes(String definitionId);

    /**
     * 物理删除并触发重排
     */
    boolean removeAndResort(String nodeDefId, String definitionId);
}