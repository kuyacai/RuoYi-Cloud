package com.ruoyi.common.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 价格与财务业务计算工具类
 * * 核心逻辑约定：
 * 1. 存储：数据库一律使用 Long 或 Integer 存储（如金额存“分”，折扣存“万分位”）。
 * 2. 转换：提供元/分、百分比/万分位之间的精确换算，严控精度丢失。
 * 3. 校验：所有输入进行正则强校验，非法格式显式抛出异常，拒绝静默失败。
 * * @author Gemini
 */
public class PriceUtils {
    /** 换算基数：100 */
    private static final BigDecimal HUNDRED = new BigDecimal("100");

    /** 严格数字格式正则：支持可选负号、纯数字、可选小数点及小数位 */
    private static final String NUMERIC_PATTERN = "^-?\\d+(\\.\\d+)?$";

    /**
     * 【金额：分 -> 元】
     * 将分单位的长整型转为元单位的字符串，支持负数。
     * * 示例：
     * 1580L, 2 -> "15.80"
     * -500L, 2 -> "-5.00"
     * 100L, 0 -> "1"
     * * @param cent 金额（分）
     * 
     * @param scale 保留小数位数
     * @return 格式化后的元字符串
     */
    public static String formatCentToYuan(Long cent, int scale) {
        if (cent == null) {
            return null;
        }
        return BigDecimal.valueOf(cent)
                .divide(HUNDRED, scale, RoundingMode.HALF_UP)
                .toPlainString();
    }

    /**
     * 【金额：元 -> 分】
     * 强校验输入格式，清洗符号后转为分单位。
     * * 示例：
     * "15.80" -> 1580L
     * "¥1,234" -> 123400L
     * "-$5.4" -> -540L
     * "1.2.3" -> 抛出 IllegalArgumentException
     * * @param yuanStr 元单位字符串（支持 ¥, $, , 等符号）
     * 
     * @return 金额（分）
     * @throws IllegalArgumentException 格式不合法时抛出
     */
    public static Long parseYuanToCent(String yuanStr) {
        // 1. 严格检查：纯空格视为非法
        if (yuanStr != null && yuanStr.length() > 0 && yuanStr.trim().isEmpty()) {
            throw new IllegalArgumentException("金额输入不能为空格");
        }
        if (StringUtils.isBlank(yuanStr))
            return null;

        // 2. 精确剥离符号：只允许去掉开头的一个符号，并去掉千分位逗号
        String cleanText = yuanStr.trim().replace(",", "");
        cleanText = stripCurrencySymbol(cleanText);

        // 3. 正则强校验：此时 cleanText 必须是纯数字结构（如 -1234.56）
        if (!Pattern.matches(NUMERIC_PATTERN, cleanText)) {
            throw new IllegalArgumentException("非法的金额输入格式: " + yuanStr);
        }

        return new BigDecimal(cleanText)
                .multiply(HUNDRED)
                .setScale(0, RoundingMode.HALF_UP)
                .longValue();
    }

    /**
     * 内部私有方法：仅剥离字符串开头的一个货币符号
     */
    private static String stripCurrencySymbol(String text) {
        if (text.startsWith("¥") || text.startsWith("$")) {
            return text.substring(1);
        }
        return text;
    }

    /**
     * 【比例：字符串 -> 存储整数】
     * 将界面输入的折扣或比例转为数据库存储的整数（根据 base 换算）。
     * * 示例 (当 base=10000 时):
     * "56.57%" -> 5657
     * "7.5" -> 750
     * "-10%" -> -1000
     * * @param rateStr 比例字符串（支持 % 结尾）
     * 
     * @param base 存储基数（如万分位传 10000，百分位传 100）
     * @return 存储用的整数
     */
    public static BigDecimal parseRateToStorage(String rateStr, long base) {
        // 1. 严格检查：如果是纯空格但非空，应视为非法格式
        if (rateStr != null && rateStr.length() > 0 && rateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("比例输入不能为空格: " + rateStr);
        }
        // 注意，不能交换位置。
        if (StringUtils.isBlank(rateStr)) {
            return null;
        }

        // 2. 改进清洗逻辑：只允许去除末尾的一个百分号
        String cleanText = rateStr.trim();
        if (cleanText.endsWith("%")) {
            cleanText = cleanText.substring(0, cleanText.length() - 1);
        }
        // 3. 正则校验：此时如果还有百分号（如 75%% 变成 75%），正则会直接拦截
        if (!Pattern.matches(NUMERIC_PATTERN, cleanText)) {
            throw new IllegalArgumentException("非法的比例输入格式: " + rateStr);
        }

        return new BigDecimal(cleanText)
                .multiply(new BigDecimal(base))
                .divide(HUNDRED, 0, RoundingMode.HALF_UP);
    }

    /**
     * 【比例：存储整数 -> 展示字符串】
     * * 示例 (当 base=10000, scale=2 时):
     * 5657L -> "56.57"
     * 750L -> "7.50"
     * "abc" -> 抛出 IllegalArgumentException
     * * @param value 数据库存储的整数值 (Integer 或 Long)
     * 
     * @param base  存储基数
     * @param scale 展示的小数位数
     * @return 格式化后的比例字符串
     * @throws IllegalArgumentException 当 value 非数字或 base 为 0 时
     */
    public static String formatStorageToRate(Object value, long base, int scale) {
        if (value == null) {
            return null;
        }
        if (base == 0) {
            throw new IllegalArgumentException("基数(base)不能为0");
        }

        String valStr = value.toString().trim();
        if (!Pattern.matches(NUMERIC_PATTERN, valStr)) {
            throw new IllegalArgumentException("非法的存储数值格式: " + valStr);
        }

        return new BigDecimal(valStr)
                .multiply(HUNDRED)
                .divide(new BigDecimal(base), scale, RoundingMode.HALF_UP)
                .toPlainString();
    }
}