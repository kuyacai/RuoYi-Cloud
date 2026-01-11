package com.ruoyi.product.core.mybatisplus;

import java.time.Instant;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        Instant now = Instant.now(); // UTC 时间戳
        this.strictInsertFill(metaObject, "createdAtUtc", Instant.class, now);
        this.strictInsertFill(metaObject, "updatedAtUtc", Instant.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updatedAtUtc", Instant.class, Instant.now());
    }
}