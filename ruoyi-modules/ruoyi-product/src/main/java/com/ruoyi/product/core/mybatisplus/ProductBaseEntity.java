package com.ruoyi.product.core.mybatisplus;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.web.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//product模块中使用了mp，而BaseEntity中这些字段在对应的数据库表中并不存在
//所以添加ProductBaseEntity作为垫片，重写BaseEntity的属性，但是又能兼容
//前端的相关传参。
//另gmt_create和gmt_modified在product模块中都有，也一并添加在此处。
@Getter
@Setter
@ToString(callSuper = true) // 让 toString 包含 BaseEntity 的字段
public class ProductBaseEntity extends BaseEntity {

    /** 搜索值 */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /** 创建者 */
    @TableField(exist = false)
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date createTime;

    /** 更新者 */
    @TableField(exist = false)
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    private Date updateTime;

    /** 备注 */
    @TableField(exist = false)
    private String remark;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    // 在插入时填充
    @TableField(fill = FieldFill.INSERT)
    private Instant createdAtUtc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    // 在插入和更新时都填充（确保每次修改都记录最新时间）
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Instant updatedAtUtc;

}