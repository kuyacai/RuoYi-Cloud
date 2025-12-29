package com.ruoyi.product.core.excel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import com.ruoyi.product.core.annotation.ExcelBusiness;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExcelDtoRegistry {

    // 存储 businessKey -> DTO Class 的映射
    private final Map<String, Class<?>> handlerMap = new ConcurrentHashMap<>();

    @Value("${product.dto-scan-package:com.ruoyi.product.domain.dto}")
    private String scanPackage;

    @PostConstruct
    public void init() {
        log.info("开始扫描 Excel DTO, 包路径: {}", scanPackage);
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        // 只扫描带有 @ExcelBusiness 注解的类
        scanner.addIncludeFilter(new AnnotationTypeFilter(ExcelBusiness.class));

        Set<BeanDefinition> definitions = scanner.findCandidateComponents(scanPackage);
        for (BeanDefinition bd : definitions) {
            try {
                Class<?> clazz = Class.forName(bd.getBeanClassName());
                ExcelBusiness anno = clazz.getAnnotation(ExcelBusiness.class);
                String key = anno.value();

                if (handlerMap.containsKey(key)) {
                    log.error("Excel业务Key重复: {}，类名: {}", key, clazz.getName());
                    continue;
                }
                handlerMap.put(key, clazz);
                log.info("注册 Excel 业务: [{}] -> {}", key, clazz.getSimpleName());
            } catch (ClassNotFoundException e) {
                log.error("类加载失败", e);
            }
        }
    }

    public Class<?> getDtoClass(String businessKey) {
        return handlerMap.get(businessKey);
    }
}