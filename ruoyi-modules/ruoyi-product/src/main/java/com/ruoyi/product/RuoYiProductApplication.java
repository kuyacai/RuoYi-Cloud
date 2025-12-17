package com.ruoyi.product;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

@EnableCustomConfig
@SpringBootApplication
@EnableDiscoveryClient
@EnableRyFeignClients
@MapperScan("com.ruoyi.product.mapper")
public class RuoYiProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuoYiProductApplication.class, args);
    }
}
