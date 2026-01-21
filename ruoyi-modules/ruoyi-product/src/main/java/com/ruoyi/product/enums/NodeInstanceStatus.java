
package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "workflow", description = "任务节点实例状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NodeInstanceStatus implements BaseEnum, IEnum<String> {
    INIT("init", "初始化"),
    RUNNING("running", "执行中"),
    SUCCESS("success", "成功"),
    FAILED("failed", "失败"),
    SKIPPED("skipped", "已跳过"),
    // 当节点失败后，你点击“重试”，状态从 FAILED 转为 RETRYING 再转回 RUNNING。
    RETRYING("retrying", "重试中"),
    AWAITING_HUMAN("awaiting_human", "等待人工确认");

    private final String code;
    private final String label;

    NodeInstanceStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getValue() {
        return code;
    }
}
