package com.ruoyi.product.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.product.core.mybatisplus.impl.BaseServiceImpl;
import com.ruoyi.product.domain.WfNodeDefinition;
import com.ruoyi.product.mapper.NodeDefinitionMapper;
import com.ruoyi.product.service.IWfNodeDefinitionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WfNodeDefinitionServiceImpl extends BaseServiceImpl<NodeDefinitionMapper, WfNodeDefinition>
        implements IWfNodeDefinitionService {

    /**
     * 重新排序：防止由于中间节点被删除导致的序号断层
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resortNodes(String definitionId) {
        // 1. 获取当前流程下所有节点，按原有顺序排列
        List<WfNodeDefinition> nodes = this.list(new LambdaQueryWrapper<WfNodeDefinition>()
                .eq(WfNodeDefinition::getDefinitionId, definitionId)
                .orderByAsc(WfNodeDefinition::getNodeOrder));

        if (nodes.isEmpty())
            return;

        // 2. 重新分配连续的序号
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).setNodeOrder(i + 1);
        }

        // 3. 批量更新
        this.updateBatchById(nodes);
    }

    /**
     * 删除节点并自动平滑序号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAndResort(String nodeDefId, String definitionId) {
        // 执行删除
        boolean removed = this.removeById(nodeDefId);
        if (removed) {
            // 触发重排
            this.resortNodes(definitionId);
        }
        return removed;
    }

}
