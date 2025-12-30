package com.ruoyi.product.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;

@ExposeEnum(group = "product", description = "异步任务状态") // 供 EnumScanner 扫描
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AsyncTaskStatus implements BaseEnum, IEnum<String> {

    INIT("init", "新建任务"),
    DOING("doing", "进行中"),
    CANCELLED("cancelled", "已取消"),
    DONE("done", "已完成");

    private final String code;
    private final String label;

    /* 构造器 */
    AsyncTaskStatus(String code, String label) {
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

    /* 数据库 code -> 枚举 */
    public static AsyncTaskStatus of(String code) {
        for (AsyncTaskStatus c : values()) {
            if (c.code.equals(code)) {
                return c;
            }
        }
        return null;
    }
}
