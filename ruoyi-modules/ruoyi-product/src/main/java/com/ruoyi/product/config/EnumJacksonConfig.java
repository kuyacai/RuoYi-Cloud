package com.ruoyi.product.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ruoyi.common.core.enums.BaseEnum;

@Configuration
public class EnumJacksonConfig {

    @Bean
    public SimpleModule baseEnumModule() {
        SimpleModule module = new SimpleModule();
        // 专门针对实现了 BaseEnum 接口的类进行拦截处理
        module.addDeserializer(Enum.class, new JsonDeserializer<Enum>() {
            @Override
            public Enum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
                // 获取当前目标字段的实际类型（例如 ActivityProductStatus.class）
                Class<?> targetClass = ctxt.getContextualType().getRawClass();
                String text = jp.getText(); // 获取前端传来的值，可能是 "active"

                // 只有实现了 BaseEnum 且是枚举的才处理
                if (targetClass.isEnum() && BaseEnum.class.isAssignableFrom(targetClass)) {
                    for (Object enumConstant : targetClass.getEnumConstants()) {
                        BaseEnum baseEnum = (BaseEnum) enumConstant;
                        // 匹配前端传来的 code 字符串
                        if (baseEnum.getCode().equalsIgnoreCase(text)) {
                            return (Enum) enumConstant;
                        }
                    }
                }
                return null;
            }
        });
        return module;
    }
}