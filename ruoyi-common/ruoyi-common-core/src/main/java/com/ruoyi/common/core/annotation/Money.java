package com.ruoyi.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.core.jackson.MoneyDeserializer;
import com.ruoyi.common.core.jackson.MoneySerializer;

/**
 * 金额转换注解
 * 约定：数据库存 Long (分)，前端传 String (元)
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = MoneySerializer.class)
@JsonDeserialize(using = MoneyDeserializer.class)
public @interface Money {
    /**
     * 反序列化校验正则：默认支持货币符号、千分位、最多2位小数
     * 符合规则的示例：
     * 1. 纯数字： "165", "165.0", "165.00"
     * 2. 带符号： "¥165", "$165.00"
     * 3. 带千分位： "1,234,567.89", "¥1,234.5"
     * 4. 负数： "-165", "-¥1,234.50"
     */
    String validationPattern() default "^[¥$]?(-?\\d{1,3}(,\\d{3})*|-?\\d+)(\\.\\d{1,2})?$";

    /**
     * 是否允许负数
     */
    boolean allowNegative() default true;

    /**
     * 序列化输出时的小数位数
     */
    int scale() default 2;
}