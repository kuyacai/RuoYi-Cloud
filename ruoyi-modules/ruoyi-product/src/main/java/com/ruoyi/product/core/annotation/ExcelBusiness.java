package com.ruoyi.product.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelBusiness {
    /** 业务唯一标识，对应前端请求的路径参数 */
    String value();

    /** 导出文件名 */
    String exportName() default "数据导出";

    /** 导入模板名 */
    String templateName() default "导入模板";

    /** 负责处理该业务数据获取的 Spring Bean 名称 */
    String handlerBean() default "";
}