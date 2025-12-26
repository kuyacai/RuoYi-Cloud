package com.ruoyi.common.core.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要暴露给前端的枚举类
 */
@Target(ElementType.TYPE)  // 只能用在类、接口、枚举上
@Retention(RetentionPolicy.RUNTIME)  // 运行时保留，可以通过反射获取
public @interface ExposeEnum {
    /**
     * 分组名称，用于前端分类展示
     */
    String group() default "";
    
    /**
     * 枚举描述
     */
    String description() default "";
}