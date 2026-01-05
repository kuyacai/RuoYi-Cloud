package com.ruoyi.product.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.annotation.Excel.Type;
import com.ruoyi.common.core.utils.poi.ExcelUtil;

/**
 * 修复Excel导入问题的工具类
 * 支持自动将换行符转换为注解定义的 separator
 */
public class EnhancedExcelUtil<T> extends ExcelUtil<T> {

    private boolean cleanStrings = true;
    private boolean preserveAllWhitespace = false;
    private List<Field> cachedFields; // 缓存字段列表以提高性能

    public EnhancedExcelUtil(Class<T> clazz) {
        super(clazz);
        // 通过反射获取父类已经解析好的 fields 列表
        // TODO,可能存在bug，此时父类可能并没初始化fields列表。
        this.cachedFields = getFieldsFromParent();
    }

    public EnhancedExcelUtil<T> setCleanStrings(boolean cleanStrings) {
        this.cleanStrings = cleanStrings;
        return this;
    }

    public EnhancedExcelUtil<T> setPreserveAllWhitespace(boolean preserveAllWhitespace) {
        this.preserveAllWhitespace = preserveAllWhitespace;
        return this;
    }

    /**
     * 重写获取单元格值的方法
     */
    @Override
    public Object getCellValue(Row row, int column) {
        try {
            Cell cell = row.getCell(column);
            if (cell == null) {
                return "";
            }

            // 获取当前列的注解契约
            Excel attr = getExcelAnnotation(column);

            if (cell.getCellType() == CellType.NUMERIC) {
                return handleNumericCell(cell, attr);
            }

            String stringValue = cell.getStringCellValue();
            if (cleanStrings) {
                return cleanStringSmart(stringValue, attr);
            }
            return stringValue;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 原有的数字处理逻辑，已整合智能清理
     */
    private Object handleNumericCell(Cell cell, Excel attr) {
        double numericValue = cell.getNumericCellValue();

        if (DateUtil.isCellDateFormatted(cell)) {
            return DateUtil.getJavaDate(numericValue);
        }

        boolean isInteger = numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue);

        if (isInteger) {
            String stringValue;
            if (numericValue > 1E15) {
                stringValue = BigDecimal.valueOf(numericValue).toPlainString();
            } else {
                stringValue = new DecimalFormat("0").format(numericValue);
            }

            if (cleanStrings && !preserveAllWhitespace) {
                return cleanStringSmart(stringValue, attr);
            }
            return stringValue;
        } else {
            return BigDecimal.valueOf(numericValue);
        }
    }

    /**
     * 智能清理字符串：核心就在这里处理 separator
     */
    private String cleanStringSmart(String input, Excel attr) {
        if (StringUtils.isEmpty(input)) {
            return "";
        }

        // 1. 移除制表符和有害控制字符
        String result = input.replaceAll("[\\t\\u0000-\\u001F\\u007F]", "").trim();

        // 2. 获取分隔符：优先用注解的，没有就默认逗号
        String sep = (attr != null && StringUtils.isNotEmpty(attr.separator())) ? attr.separator() : ",";

        // 3. 【核心修改】针对图片链接的特殊处理
        // 如果字符串包含多个 https:// 且没有被分隔符隔开，手动强制分隔
        if (result.contains("http") && !result.contains(sep)) {
            // 先把可能的换行符替换为分隔符
            result = result.replaceAll("[\\r\\n]+", sep);

            // 如果依然没有分隔符（说明发生了粘连），利用 http 关键字强制切分
            if (!result.contains(sep)) {
                result = result.replace("https://", sep + "https://")
                        .replace("http://", sep + "http://");
            }
        } else {
            // 普通多行文本处理：换行变分隔符
            result = result.replaceAll("[\\r\\n]+", sep);
        }

        // 4. 最后统一清洗：去除重复的分隔符，处理首尾空格
        return Arrays.stream(result.split(java.util.regex.Pattern.quote(sep)))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(sep));
    }

    /**
     * 反射获取父类维护的字段列表，确保列索引与字段对应准确
     */
    @SuppressWarnings("unchecked")
    private List<Field> getFieldsFromParent() {
        try {
            java.lang.reflect.Field field = ExcelUtil.class.getDeclaredField("fields");
            field.setAccessible(true);
            List<Object[]> parentFields = (List<Object[]>) field.get(this);
            return parentFields.stream()
                    .map(obj -> (Field) obj[0])
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    private Excel getExcelAnnotation(int column) {
        if (cachedFields != null && column < cachedFields.size()) {
            return cachedFields.get(column).getAnnotation(Excel.class);
        }
        return null;
    }

    public Workbook exportExcelToWorkbook(List<T> list, String sheetName) {
        // 1. 调用父类 init 方法初始化表头字段映射
        // 参数：数据列表, 工作表名, 标题(null), 导出类型
        this.init(list, sheetName, null, Type.EXPORT);

        // 2. 调用父类 writeSheet 方法将数据写入内部的 this.wb
        this.writeSheet();

        // 3. 直接返回父类中定义的 protected 成员变量 wb
        return this.wb;
    }
}