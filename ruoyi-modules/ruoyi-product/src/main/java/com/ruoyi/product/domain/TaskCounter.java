package com.ruoyi.product.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 任务计数器对象 task_counter
 * 
 * @author Rupert
 * @date 2025-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "task_counter", autoResultMap = true)
public class TaskCounter extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "biz_id", type = IdType.INPUT)
    private String bizId;

    private Integer totalTasks;

    private Integer completedTasks;

    private Date lastCheckTime;

    private String notifiedStatus;

    private Date notifiedTime;

    private Integer retryCount;

    /**
     * 判定该业务对应的所有任务是否已完成
     * 逻辑：总数大于 0 且 已完成数等于总数
     */
    public boolean isAllCompleted() {
        return totalTasks != null && totalTasks > 0 && totalTasks.equals(completedTasks);
    }

}
