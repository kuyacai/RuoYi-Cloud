package com.ruoyi.product.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty; // 必须导入这个
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException; // 必须导入这个
import com.fasterxml.jackson.databind.deser.ContextualDeserializer; // 必须导入这个
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ruoyi.common.core.enums.BaseEnum;

@Configuration
public class EnumJacksonConfig {

    @Bean
    public SimpleModule baseEnumModule() {
        SimpleModule module = new SimpleModule();
        // 注册自定义的反序列化器
        module.addDeserializer(Enum.class, new BaseEnumDeserializer());
        return module;
    }

    /**
     * 实现 ContextualDeserializer 接口
     */
    public static class BaseEnumDeserializer extends JsonDeserializer<Enum> implements ContextualDeserializer {

        private Class<?> targetClass;

        // 默认构造函数必须保留
        public BaseEnumDeserializer() {
        }

        // 带参构造函数用于在运行时锁定具体的枚举类
        public BaseEnumDeserializer(Class<?> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public Enum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            String text = jp.getText();
            // 只有当 targetClass 是实现了 BaseEnum 的枚举时才处理
            if (targetClass != null && targetClass.isEnum() && BaseEnum.class.isAssignableFrom(targetClass)) {
                for (Object enumConstant : targetClass.getEnumConstants()) {
                    BaseEnum baseEnum = (BaseEnum) enumConstant;
                    if (baseEnum.getCode().equalsIgnoreCase(text)) {
                        return (Enum) enumConstant;
                    }
                }
            }
            return null;
        }

        /**
         * 关键方法：Jackson 在解析具体属性前会调用此方法
         */
        @Override
        public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
                throws JsonMappingException {
            // 获取当前需要反序列化的具体类型
            Class<?> rawClass = null;
            if (ctxt.getContextualType() != null) {
                rawClass = ctxt.getContextualType().getRawClass();
            } else if (property != null) {
                rawClass = property.getType().getRawClass();
            }

            // 返回一个带有具体目标类型的反序列化器实例
            return new BaseEnumDeserializer(rawClass);
        }
    }
}