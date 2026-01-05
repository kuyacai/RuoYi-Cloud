package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

/**
 * 任务状态
 */
@ExposeEnum(group = "product", description = "任务状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemTaskStatus implements BaseEnum, IEnum<String> {

    PENDING("pending", "待处理"),
    DONE("done", "已完成"),
    FAILED("failed", "已失败"),
    CANCELLED("cancelled", "已取消");

    private final String code;
    private final String label;

    ItemTaskStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getValue() {
        return this.code; // MyBatis Plus 数据库存储值
    }

    public static ItemTaskStatus of(String code) {
        for (ItemTaskStatus e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 判断是否为最终状态
     */
    public static boolean isFinalStatus(String status) {
        return DONE.getCode().equals(status) || FAILED.getCode().equals(status) || CANCELLED.getCode().equals(status);
    }

    /**
     * 判断是否为完成状态（含成功和失败）
     */
    public static boolean isCompletedStatus(String status) {
        return DONE.getCode().equals(status) || FAILED.getCode().equals(status);
    }

    /**
     * 判断是否可以重新处理
     */
    public static boolean canRetry(String status) {
        return FAILED.getCode().equals(status) || CANCELLED.getCode().equals(status);
    }
}