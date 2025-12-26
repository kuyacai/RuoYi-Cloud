package com.ruoyi.common.core.utils;

import com.ruoyi.common.core.domain.EnumVO;
import com.ruoyi.common.core.enums.BaseEnum;
import com.ruoyi.common.core.enums.ExposeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 枚举扫描器
 */
public class EnumScanner {
    
    private static final Logger log = LoggerFactory.getLogger(EnumScanner.class);
    
    /**
     * 扫描指定包下的所有暴露的枚举
     * 
     * @param scanPackages 要扫描的包路径，多个用逗号分隔
     * @return 枚举类列表
     */
    public List<Class<?>> scanExposedEnums(String scanPackages) {
        List<Class<?>> enumClasses = new ArrayList<>();
        
        if (!StringUtils.hasText(scanPackages)) {
            log.warn("扫描包路径为空");
            return enumClasses;
        }
        
        String[] packages = scanPackages.split(",");
        for (String scanPackage : packages) {
            scanPackage = scanPackage.trim();
            if (StringUtils.hasText(scanPackage)) {
                enumClasses.addAll(doScan(scanPackage));
            }
        }
        
        return enumClasses;
    }
    
    /**
     * 扫描单个包
     */
    private List<Class<?>> doScan(String scanPackage) {
        List<Class<?>> enumClasses = new ArrayList<>();
        
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(scanPackage) + "/**/*.class";
            
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(packageSearchPath);
            
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
            
            for (Resource resource : resources) {
                try {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    
                    // 跳过内部类
                    if (className.contains("$")) {
                        continue;
                    }
                    
                    Class<?> clazz = Class.forName(className);
                    
                    // 检查是否是枚举、是否实现了BaseEnum、是否有@ExposeEnum注解
                    if (clazz.isEnum() && 
                        BaseEnum.class.isAssignableFrom(clazz) && 
                        clazz.isAnnotationPresent(ExposeEnum.class)) {
                        enumClasses.add(clazz);
                        log.debug("找到暴露的枚举类: {}", className);
                    }
                } catch (ClassNotFoundException e) {
                    log.warn("无法加载类: {}", resource.getDescription(), e);
                }
            }
        } catch (IOException e) {
            log.error("扫描包路径失败: {}", scanPackage, e);
        }
        
        return enumClasses;
    }
    
    /**
     * 获取枚举的所有值
     */
    public List<EnumVO> getEnumValues(Class<?> enumClass) {
        List<EnumVO> result = new ArrayList<>();
        
        if (!enumClass.isEnum() || !BaseEnum.class.isAssignableFrom(enumClass)) {
            return result;
        }
        
        try {
            // 获取枚举实例
            Object[] enumConstants = enumClass.getEnumConstants();
            
            // 尝试获取order字段（如果有）
            boolean hasOrderField = hasOrderField(enumClass);
            
            for (int i = 0; i < enumConstants.length; i++) {
                BaseEnum baseEnum = (BaseEnum) enumConstants[i];
                
                EnumVO enumVO = new EnumVO();
                enumVO.setCode(baseEnum.getCode());
                enumVO.setLabel(baseEnum.getLabel());
                enumVO.setName(enumConstants[i].toString());
                
                // 设置顺序
                if (hasOrderField) {
                    try {
                        Field orderField = enumClass.getDeclaredField("order");
                        orderField.setAccessible(true);
                        Object orderValue = orderField.get(enumConstants[i]);
                        if (orderValue instanceof Integer) {
                            enumVO.setOrder((Integer) orderValue);
                        }
                    } catch (Exception e) {
                        // 忽略，使用默认顺序
                        enumVO.setOrder(i);
                    }
                } else {
                    enumVO.setOrder(i);
                }
                
                result.add(enumVO);
            }
            
            // 按order排序
            result.sort(Comparator.comparingInt(EnumVO::getOrder));
            
        } catch (Exception e) {
            log.error("获取枚举值失败: {}", enumClass.getName(), e);
        }
        
        return result;
    }
    
    /**
     * 检查枚举类是否有order字段
     */
    private boolean hasOrderField(Class<?> enumClass) {
        try {
            Field[] fields = enumClass.getDeclaredFields();
            for (Field field : fields) {
                if ("order".equals(field.getName()) && field.getType() == Integer.class) {
                    return true;
                }
            }
        } catch (Exception e) {
            // 忽略异常
        }
        return false;
    }
    
    /**
     * 根据枚举类名查找枚举类
     */
    public Class<?> findEnumClass(String enumName, String scanPackages) {
        List<Class<?>> allEnums = scanExposedEnums(scanPackages);
        
        // 先尝试精确匹配简单类名
        for (Class<?> clazz : allEnums) {
            if (clazz.getSimpleName().equals(enumName)) {
                return clazz;
            }
        }
        
        // 再尝试忽略大小写匹配
        for (Class<?> clazz : allEnums) {
            if (clazz.getSimpleName().equalsIgnoreCase(enumName)) {
                return clazz;
            }
        }
        
        // 尝试按完整类名查找
        try {
            Class<?> clazz = Class.forName(enumName);
            if (clazz.isEnum() && BaseEnum.class.isAssignableFrom(clazz) && 
                clazz.isAnnotationPresent(ExposeEnum.class)) {
                return clazz;
            }
        } catch (ClassNotFoundException e) {
            // 忽略
        }
        
        return null;
    }
    
    /**
     * 获取所有暴露的枚举信息
     */
    public Map<String, Map<String, Object>> getAllEnumInfo(String scanPackages) {
        Map<String, Map<String, Object>> result = new LinkedHashMap<>();
        
        List<Class<?>> enumClasses = scanExposedEnums(scanPackages);
        for (Class<?> enumClass : enumClasses) {
            ExposeEnum annotation = enumClass.getAnnotation(ExposeEnum.class);
            
            Map<String, Object> enumInfo = new HashMap<>();
            enumInfo.put("className", enumClass.getName());
            enumInfo.put("simpleName", enumClass.getSimpleName());
            enumInfo.put("group", annotation.group());
            enumInfo.put("description", annotation.description());
            enumInfo.put("values", getEnumValues(enumClass));
            
            result.put(enumClass.getSimpleName(), enumInfo);
        }
        
        return result;
    }
}