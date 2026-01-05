package com.ruoyi.common.core.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.ruoyi.common.core.annotation.Money;
import com.ruoyi.common.core.utils.PriceUtils;

public class MoneySerializer extends JsonSerializer<Long> implements ContextualSerializer {
    private int scale = 2;

    public MoneySerializer() {
    }

    public MoneySerializer(int scale) {
        this.scale = scale;
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 使用 PriceUtils 进行统一格式化输出
        gen.writeString(PriceUtils.formatCentToYuan(value, scale));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        if (property != null) {
            Money ann = property.getAnnotation(Money.class);
            if (ann != null) {
                return new MoneySerializer(ann.scale());
            }
        }
        return this;
    }
}