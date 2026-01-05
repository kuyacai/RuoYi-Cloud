package com.ruoyi.common.core.jackson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.ruoyi.common.core.annotation.Rate;

public class RateSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private long base;
    private int scale;
    private boolean showSymbol;

    public RateSerializer() {
    }

    public RateSerializer(long base, int scale, boolean showSymbol) {
        this.base = base;
        this.scale = scale;
        this.showSymbol = showSymbol;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        // 计算逻辑：展示值 = (存储整数 / 基数) * 100
        // 例如：(5657 / 10000) * 100 = 56.57
        BigDecimal storageValue = new BigDecimal(value.toString());
        BigDecimal displayValue = storageValue
                .multiply(new BigDecimal(100))
                .divide(new BigDecimal(base), scale, RoundingMode.HALF_UP);

        String result = displayValue.toPlainString() + (showSymbol ? "%" : "");
        gen.writeString(result);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        Rate ann = property.getAnnotation(Rate.class);
        if (ann != null) {
            return new RateSerializer(ann.base(), ann.scale(), ann.showSymbol());
        }
        return this;
    }
}