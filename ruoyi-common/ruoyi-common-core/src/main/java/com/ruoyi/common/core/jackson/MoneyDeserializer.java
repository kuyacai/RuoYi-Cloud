package com.ruoyi.common.core.jackson;

import java.io.IOException;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.ruoyi.common.core.annotation.Money;
import com.ruoyi.common.core.utils.PriceUtils;

public class MoneyDeserializer extends JsonDeserializer<Long> implements ContextualDeserializer {
    private String patternStr;
    private boolean allowNegative;

    public MoneyDeserializer() {
    }

    public MoneyDeserializer(String patternStr, boolean allowNegative) {
        this.patternStr = patternStr;
        this.allowNegative = allowNegative;
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        if (text == null || text.trim().isEmpty())
            return null;

        String input = text.trim();

        // 1. 契约校验：检查格式是否符合定义的正则
        if (patternStr != null && !Pattern.matches(patternStr, input)) {
            throw new IOException("金额格式非法，不匹配约定格式: " + patternStr);
        }

        // 2. 负数校验
        if (!allowNegative && input.contains("-")) {
            throw new IOException("该场景不允许输入负数金额");
        }

        // 3. 业务转换：通过 PriceUtils 清洗并转为分
        try {
            return PriceUtils.parseYuanToCent(input);
        } catch (Exception e) {
            throw new IOException("金额解析转换失败: " + input);
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        if (property != null) {
            Money ann = property.getAnnotation(Money.class);
            if (ann != null) {
                return new MoneyDeserializer(ann.validationPattern(), ann.allowNegative());
            }
        }
        return this;
    }
}