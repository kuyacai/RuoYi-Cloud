package com.ruoyi.product.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.product.core.mybatisplus.ProductBaseEntity;
import com.ruoyi.product.enums.ItemTaskCode;
import com.ruoyi.product.enums.ItemTaskStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 任务实例对象 item_task
 * 
 * @author Rupert
 * @date 2025-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "item_task", autoResultMap = true)
public class ItemTask extends ProductBaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "task_id", type = IdType.INPUT)
    private String taskId;

    private ItemTaskCode taskCode;

    private String bizId;

    private ItemTaskStatus taskStatus;

}
