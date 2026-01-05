package com.ruoyi.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.core.jackson.RateDeserializer;
import com.ruoyi.common.core.jackson.RateSerializer;

/**
 * 比例/折扣转换注解
 * * 符合规则的金额表示示例（以默认 validationPattern 为准）：
 * 1. 整数比例： "7", "10", "-5"
 * 2. 小数比例： "7.5", "0.12", "-0.5"
 * 3. 带百分号： "75%", "56.57%", "-10.5%"
 * * 逻辑约定：
 * - 序列化(出库)：将存储的整数(如 7500) 转换为展示字符串 (如 "75.00")
 * - 反序列化(入库)：将用户输入的字符串 (如 "7.5%") 转换为存储整数 (如 750)
 * public class FinancialDTO {
 * 
 * // 场景1：毛利率，数据库存万分位 5657，前端看到 "56.57%"
 * 
 * @Rate(base = 10000, showSymbol = true)
 *            private Integer grossMargin;
 * 
 *            // 场景2：折扣，数据库存百分位 75（代表7.5折），前端看到 "7.5"
 * @Rate(base = 100, scale = 1)
 *            private Integer discountRate;
 * 
 *            // 场景3：常规比例，数据库存万分位，前端看到 "0.12" (不带百分号)
 * @Rate
 *       private Long ratio;
 *       }
 * 
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = RateSerializer.class)
@JsonDeserialize(using = RateDeserializer.class)
public @interface Rate {
    /** 存储基数。默认 10000（万分位） */
    long base() default 10000L;

    /** 界面展示的小数位数 */
    int scale() default 2;

    /** 是否增加百分号后缀 */
    boolean showSymbol() default false;

    /** 反序列化校验正则：支持可选负号、数字、可选一个百分号 */
    String validationPattern() default "^-?\\d+(\\.\\d+)?%?$";
}