package com.ruoyi.product.service;

import com.ruoyi.product.core.mybatisplus.IBaseService;
import com.ruoyi.product.domain.WfWorkflowInstance;

public interface IWfWorkflowInstanceService extends IBaseService<WfWorkflowInstance> {

    String startWorkflow(String definitionId, String creator);

}
