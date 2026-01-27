package com.ruoyi.product.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.WfWorkflowInstance;

public interface IWfWorkflowInstanceService extends IBaseService<WfWorkflowInstance> {

    public String startWorkflow(String definitionId, Map<String, Object> variables, String creator);

    public List<WfWorkflowInstance> selectWfWorkflowInstanceList(WfWorkflowInstance query);

    public void retryNode(String nodeInstanceId);

    public void completeManualNode(String nodeInstanceId, Map<String, Object> manualData);

}
