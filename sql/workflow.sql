-- 1. 系统健康检查算子 (Skill)
INSERT INTO `wf_node_capability` (
    `capability_id`, 
    `name`, 
    `handler_type`, 
    `description`, 
    `config_schema`, 
    `is_active`, 
    `is_manual`, 
    `created_at_utc`, 
    `updated_at_utc`
) VALUES (
    'system_health_checker', 
    '系统健康检查器', 
    'python_agent', 
    '用于检查 Python 运行环境状态（OS、版本、资源占用等）', 
    '{"check_level": "string"}', 
    'enable', 
    'no', 
    UTC_TIMESTAMP(3), 
    UTC_TIMESTAMP(3)
);

-- 2. 逻辑连通性测试算子 (Skill)
INSERT INTO `wf_node_capability` (
    `capability_id`, 
    `name`, 
    `handler_type`, 
    `description`, 
    `config_schema`, 
    `is_active`, 
    `is_manual`, 
    `created_at_utc`, 
    `updated_at_utc`
) VALUES (
    'logic_connectivity_tester', 
    '链路连通性测试', 
    'python_agent', 
    '用于测试跨节点数据流转，接收上游输出并通过 SpEL 解析回传', 
    '{"source_node_data": "string"}', 
    'enable', 
    'no', 
    UTC_TIMESTAMP(3), 
    UTC_TIMESTAMP(3)
);


-- 3. 全人工审核算子 (场景一)
INSERT INTO `wf_node_capability` (
    `capability_id`, `name`, `handler_type`, `description`, 
    `config_schema`, `is_active`, `is_manual`, `created_at_utc`
) VALUES (
    'manual_data_review', '人工合规审核', 'python_agent', '该节点需要管理员手动查看数据并填写审核结论', 
    '{"review_comment": "string"}', 'enable', 'no', UTC_TIMESTAMP(3)
);

-- 4. 验证码协同算子 (场景二)
INSERT INTO `wf_node_capability` (
    `capability_id`, `name`, `handler_type`, `description`, 
    `config_schema`, `is_active`, `is_manual`, `created_at_utc`
) VALUES (
    'captcha_resolver', '验证码人工辅助', 'python_agent', '自动化采集时若遇验证码，自动挂起并等待人工过码', 
    '{"is_resolved": "boolean"}', 'enable', 'no', UTC_TIMESTAMP(3)
);

-- 5. 初始化通用人工节点能力
INSERT INTO `wf_node_capability` (
    `capability_id`, 
    `name`, 
    `handler_type`, 
    `description`, 
    `config_schema`, 
    `is_active`, 
    `is_manual`, 
    `created_at_utc`, 
    `updated_at_utc`
) VALUES (
    'manual_operation', 
    '通用人工处理', 
    'java_local', 
    '由人工手动执行的任务节点。执行到此步时流程会自动挂起，等待操作员在界面处理并点击确认后继续。', 
    '{"fields": [{"name": "remark", "label": "处理说明", "type": "textarea"}]}', 
    'enable', 
    'yes', 
    UTC_TIMESTAMP(3), 
    UTC_TIMESTAMP(3)
);

-- 补充说明：
-- 1. handler_type 虽然设为 java_local，但由于 is_manual='yes'，代码逻辑会提前拦截，不会触发执行。
-- 2. config_schema 中定义了一个 remark，方便在添加工作流节点时，作为默认参考。

-- 修改审核算子，确保它是自动分发的（is_manual='no'）
UPDATE wf_node_capability 
SET is_manual = 'no', 
    handler_type = 'python_agent',
    config_schema = '{"fields":[{"name":"audit_result","label":"审核结果(正确/错误)","type":"text"}]}'
WHERE capability_id = 'manual_data_review';

-- 修改验证码算子
UPDATE wf_node_capability 
SET is_manual = 'no', 
    handler_type = 'python_agent',
    config_schema = '{"fields":[{"name":"is_resolved","label":"是否已处理","type":"boolean"}]}'
WHERE capability_id = 'captcha_resolver';