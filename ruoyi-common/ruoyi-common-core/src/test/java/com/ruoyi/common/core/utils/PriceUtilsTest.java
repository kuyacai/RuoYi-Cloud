package com.ruoyi.common.core.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("价格与财务工具类测试")
class PriceUtilsTest {

    @Test
    @DisplayName("分转元：正常及负数场景")
    void testFormatCentToYuan() {
        // 正常转换
        assertEquals("15.80", PriceUtils.formatCentToYuan(1580L, 2));
        // 负数转换
        assertEquals("-5.00", PriceUtils.formatCentToYuan(-500L, 2));
        // 零位小数
        assertEquals("16", PriceUtils.formatCentToYuan(1600L, 0));
        // 空值处理
        assertNull(PriceUtils.formatCentToYuan(null, 2));
    }

    @ParameterizedTest
    @DisplayName("元转分：正常、符号及千分位清洗")
    @CsvSource({
            "15.80, 1580",
            "¥1234, 123400",
            "$5.4, 540",
            "'-¥1,234.5', -123450",
            "0, 0"
    })
    void testParseYuanToCent(String input, Long expected) {
        assertEquals(expected, PriceUtils.parseYuanToCent(input));
    }

    @ParameterizedTest
    @DisplayName("元转分：异常格式校验（严谨版）")
    @ValueSource(strings = {
            "1.2.3", // 多个小数点
            "abc", // 乱码
            "12-34", // 负号位置非法
            "¥1.2.3", // 带符号但数字非法
            "¥¥100", // 重复符号（严谨版应拦截）
            "$$50", // 重复符号
            "  ", // 纯空格
            "100 ¥" // 符号位置错误
    })
    void testParseYuanToCentException(String input) {
        // 验证这些非法输入是否都会抛出 IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.parseYuanToCent(input));
    }

    @Test
    @DisplayName("存储比例转展示：正常场景")
    void testFormatStorageToRate() {
        // 1. 万分位场景：存 5657 代表 56.57%
        assertEquals("56.57", PriceUtils.formatStorageToRate(5657L, 10000L, 2));

        // 2. 百分位场景：存 75 代表 75%
        assertEquals("75.0", PriceUtils.formatStorageToRate(75, 100L, 1));

        // 3. 千分位场景：存 75 代表 7.5%
        assertEquals("7.5", PriceUtils.formatStorageToRate(75, 1000L, 1));

        // 4. 负数场景
        assertEquals("-10.00", PriceUtils.formatStorageToRate(-1000L, 10000L, 2));
    }

    @Test
    @DisplayName("比例转存储：带百分号清洗")
    void testParseRateToStorage() {
        // "56.57%" -> 5657 (万分位)
        assertEquals(0, new java.math.BigDecimal("5657").compareTo(PriceUtils.parseRateToStorage("56.57%", 10000L)));
        // "7.5" -> 750 (万分位)
        assertEquals(0, new java.math.BigDecimal("750").compareTo(PriceUtils.parseRateToStorage("7.5", 10000L)));
    }

    @Test
    @DisplayName("比例计算：基数为0应抛出异常")
    void testBaseZeroException() {
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.formatStorageToRate(100, 0L, 2));
    }

    @ParameterizedTest
    @DisplayName("比例转存储：非法输入校验（应抛出异常）")
    @ValueSource(strings = {
            "abc", // 纯字母
            "7.5.5%", // 多个小数点
            "75%%", // 多个百分号
            "12-34", // 符号位置错误
            "  " // 空白字符（parseRateToStorage 返回 null，但如果非空且不匹配正则应报错）
    })
    void testParseRateToStorageException(String input) {
        // 验证非法比例输入是否抛出异常
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.parseRateToStorage(input, 10000L));
    }

    @Test
    @DisplayName("存储比例转展示：Object类型非法输入校验")
    void testFormatStorageToRateException() {
        // 1. 验证非数字的 Object 传入
        Object dirtyData = "not-a-number";
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.formatStorageToRate(dirtyData, 10000L, 2));

        // 2. 验证基数为0的情况
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.formatStorageToRate(5657L, 0L, 2));

        // 3. 验证格式错误的字符串对象
        Object wrongFormat = "1.2.3";
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.formatStorageToRate(wrongFormat, 10000L, 2));
    }
}