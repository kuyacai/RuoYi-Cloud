package com.ruoyi.product.config;

//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
//import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.ruoyi.common.core.mybatis.FastjsonMapTypeHandler;

@Configuration
@MapperScan("com.ruoyi.product.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 如果需要使用 MP 自带的分页，取消下面注释。目前你继续用 PageHelper
        // interceptor.addInnerInterceptor(new
        // PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 全局注册类型处理器
            configuration.getTypeHandlerRegistry().register(
                    FastjsonMapTypeHandler.class);
        };
    }
}