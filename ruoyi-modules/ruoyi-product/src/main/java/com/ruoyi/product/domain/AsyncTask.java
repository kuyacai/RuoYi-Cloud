package com.ruoyi.product.domain;

import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.mybatis.FastjsonMapTypeHandler; // 之前写的转换器
import java.util.Map;

/**
 * 异步任务对象 async_task
 * 
 * @author Rupert
 * @date 2025-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "async_task", autoResultMap = true)
public class AsyncTask extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    // 手动 UUID 模式
    @TableId(value = "task_id", type = IdType.INPUT)
    private String taskId;

    /** 任务编码 */
    
    private String taskCode;

    /** 任务名称 */
    private String taskName;

    /** 文件名 */
    private String fileName;

    /** 店铺ID */
    private String shopId;

    /** 导入文件链接 */
    private String importedFileUrl;

    /** 总数 */
    private Integer total;

    /** 成功数 */
    private Integer success;

    /** 跳过数 */
    private Integer skip;

    /** 失败数 */
    private Integer failure;

    /** 失败文件URL */
    private String failFileUrl;

    /** 导入文件链接 */
    private String exportedFileUrl;

    /** 任务状态 */
    private String taskStatus;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    @TableField(typeHandler = FastjsonMapTypeHandler.class)
    private Map<String, Object> paramsMap=new HashMap<>();;

}
