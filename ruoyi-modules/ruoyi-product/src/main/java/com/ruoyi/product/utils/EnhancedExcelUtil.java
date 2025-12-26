package com.ruoyi.product.utils;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 修复Excel导入问题的工具类
 */
public class EnhancedExcelUtil<T> extends ExcelUtil<T> {
    
    private boolean cleanStrings = true;
    private boolean preserveAllWhitespace = false;
    
    public EnhancedExcelUtil(Class<T> clazz) {
        super(clazz);
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
                return null;
            }
            
            // 处理数字类型的大数字（防止精度丢失）
            if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                return handleNumericCell(cell);
            }
            
            // 调用父类方法获取值
            Object value = super.getCellValue(row, column);
            
            // 如果是字符串且需要清理
            if (value instanceof String && cleanStrings && !preserveAllWhitespace) {
                return cleanString((String) value);
            }
            
            return value;
            
        } catch (Exception e) {
            // 发生异常时返回空字符串
            return "";
        }
    }
    
    /**
     * 处理数字单元格，解决大数字精度问题
     */
    private Object handleNumericCell(Cell cell) {
        double numericValue = cell.getNumericCellValue();
        
        // 检查是否是日期
        if (DateUtil.isCellDateFormatted(cell)) {
            return DateUtil.getJavaDate(numericValue);
        }
        
        // 检查是否是整数
        boolean isInteger = numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue);
        
        if (isInteger) {
            // 对于大整数，使用BigDecimal保持精度
            if (numericValue > 1E15) {
                BigDecimal bd = BigDecimal.valueOf(numericValue);
                String stringValue = bd.toPlainString();
                
                // 如果需要清理字符串
                if (cleanStrings && !preserveAllWhitespace) {
                    return cleanString(stringValue);
                }
                return stringValue;
            } else {
                // 小整数正常格式化
                DecimalFormat df = new DecimalFormat("0");
                String stringValue = df.format(numericValue);
                
                if (cleanStrings && !preserveAllWhitespace) {
                    return cleanString(stringValue);
                }
                return stringValue;
            }
        } else {
            // 小数
            return BigDecimal.valueOf(numericValue);
        }
    }
    
    /**
     * 清理字符串
     * - 移除首尾空白
     * - 移除所有空白字符（制表符、换行符等）
     * - 移除控制字符
     */
    private String cleanString(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        
        // 1. 去除首尾空白
        String cleaned = input.trim();
        
        // 2. 移除所有空白字符（空格、制表符、换行符等）
        cleaned = cleaned.replaceAll("\\s+", "");
        
        // 3. 移除控制字符（ASCII 0-31, 127）
        cleaned = cleaned.replaceAll("[\\u0000-\\u001F\\u007F]", "");
        
        return cleaned;
    }
}