package com.ruoyi.product.constant;

public enum AsyncTaskStatus {

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

    /* getter */
    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
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
