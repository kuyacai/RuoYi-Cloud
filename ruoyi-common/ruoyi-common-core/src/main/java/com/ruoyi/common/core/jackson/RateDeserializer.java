package com.ruoyi.common.core.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.ruoyi.common.core.annotation.Rate;
import com.ruoyi.common.core.utils.PriceUtils;

public class RateDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {
    private long base;
    private String patternStr;
    private JavaType targetType;

    public RateDeserializer() {
    }

    public RateDeserializer(long base, String patternStr, JavaType targetType) {
        this.base = base;
        this.patternStr = patternStr;
        this.targetType = targetType;
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        if (StringUtils.isBlank(text))
            return null;
        String input = text.trim();

        // 1. 契约校验
        if (patternStr != null && !Pattern.matches(patternStr, input)) {
            throw new IOException("比例格式非法: " + input);
        }

        // 2. 转换逻辑 (由 PriceUtils 统一处理清洗和换算)
        BigDecimal storageValue = PriceUtils.parseRateToStorage(input, base);

        // 3. 类型安全返回
        if (targetType != null
                && (targetType.isTypeOrSubTypeOf(Integer.class) || targetType.isTypeOrSubTypeOf(int.class))) {
            return storageValue.intValue();
        }
        return storageValue.longValue();
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        if (property != null) {
            Rate ann = property.getAnnotation(Rate.class);
            if (ann != null) {
                return new RateDeserializer(ann.base(), ann.validationPattern(), property.getType());
            }
        }
        return this;
    }
}