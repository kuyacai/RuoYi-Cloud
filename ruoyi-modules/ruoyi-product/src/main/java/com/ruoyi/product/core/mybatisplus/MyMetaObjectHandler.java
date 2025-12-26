package com.ruoyi.product.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.core.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        // strictInsertFill 会检查字段是否存在且值是否为 null
        // 使用 RuoYi 统一的 DateUtils 获取时间，确保时区一致性
        Date now = DateUtils.getNowDate(); 
        this.strictInsertFill(metaObject, "gmtCreate", Date.class, now);
        this.strictInsertFill(metaObject, "gmtModified", Date.class, now);
        
        // 如果能获取到当前登录人，也可以一并填充
        // this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getUsername());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        // 更新时同样使用工具类获取时间
        this.strictUpdateFill(metaObject, "gmtModified", Date.class, DateUtils.getNowDate());
        
        // 如果是 updateBy 也可以这样处理
        // this.setFieldValByName("updateBy", SecurityUtils.getUsername(), metaObject);
    }
}