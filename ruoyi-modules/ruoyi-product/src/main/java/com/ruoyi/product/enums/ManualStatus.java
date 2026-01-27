package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 节点人工参与状态枚举
 * *
 * <p>
 * 流程驱动逻辑说明：
 * </p>
 * <ul>
 * <li><b>YES (人工节点):</b>
 * 生命周期由 UI 驱动。Java 引擎在执行到此节点时，会识别此状态并直接将节点设为
 * {@code AWAITING_HUMAN}，不会触发任何后台执行器（Python/Java）。
 * 直到操作员通过 {@code completeManualNode} 接口手动提交，流程才会流转至下一节点。
 * </li>
 * <li><b>NO (自动化节点):</b>
 * 生命周期由 执行器 驱动。Java 引擎会根据 {@code handler_type} 将任务分发给
 * Python Agent 或本地 Java 方法。
 * 若在执行过程中（如碰到验证码）需要干预，由执行器返回挂起信号，此时才进入人工干预模式。
 * </li>
 * </ul>
 */
@ExposeEnum(group = "workflow", description = "任务节点是否需要人工处理")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ManualStatus implements BaseEnum, IEnum<String> {
    YES("yes", "人工"),
    NO("no", "自动");

    private final String code;
    private final String label;

    ManualStatus(String code, String label) {
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
