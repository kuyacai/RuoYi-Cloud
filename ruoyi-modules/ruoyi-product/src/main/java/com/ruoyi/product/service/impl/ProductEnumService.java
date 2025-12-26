package com.ruoyi.product.service.impl;


import com.ruoyi.common.core.domain.EnumVO;
import com.ruoyi.common.core.utils.EnumScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductEnumService {
    
    private final EnumScanner enumScanner = new EnumScanner();
    
    @Value("${product.enum-scan-packages:com.ruoyi.product.enums}")
    private String scanPackages;
    
    // 缓存已扫描的枚举类
    private final Map<String, Class<?>> enumClassCache = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void init() {
        // 启动时扫描并缓存
        List<Class<?>> enumClasses = enumScanner.scanExposedEnums(scanPackages);
        enumClasses.forEach(clazz -> enumClassCache.put(clazz.getSimpleName(), clazz));
    }
    
    /**
     * 获取单个枚举
     */
    public List<EnumVO> getEnum(String enumName) {
        Class<?> enumClass = getEnumClass(enumName);
        return enumClass != null ? enumScanner.getEnumValues(enumClass) : Collections.emptyList();
    }
    
    /**
     * 批量获取枚举
     */
    public Map<String, List<EnumVO>> getEnums(List<String> enumNames) {
        Map<String, List<EnumVO>> result = new HashMap<>();
        for (String enumName : enumNames) {
            result.put(enumName, getEnum(enumName));
        }
        return result;
    }
    
    /**
     * 获取所有暴露的枚举
     */
    public Map<String, List<EnumVO>> getAllEnums() {
        Map<String, List<EnumVO>> result = new HashMap<>();
        for (String enumName : enumClassCache.keySet()) {
            result.put(enumName, getEnum(enumName));
        }
        return result;
    }
    
    private Class<?> getEnumClass(String enumName) {
        // 先从缓存获取
        Class<?> enumClass = enumClassCache.get(enumName);
        if (enumClass == null) {
            // 缓存未命中，动态查找
            enumClass = enumScanner.findEnumClass(enumName, scanPackages);
            if (enumClass != null) {
                enumClassCache.put(enumName, enumClass);
            }
        }
        return enumClass;
    }
}