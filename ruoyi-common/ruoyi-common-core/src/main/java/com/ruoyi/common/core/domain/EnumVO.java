package com.ruoyi.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 枚举值对象
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnumVO {
    /**
     * 枚举编码
     */
    private String code;
    
    /**
     * 枚举标签
     */
    private String label;
    
    /**
     * 枚举名称
     */
    private String name;
    
    /**
     * 枚举顺序
     */
    private Integer order;
    
    public EnumVO() {
    }
    
    public EnumVO(String code, String label) {
        this.code = code;
        this.label = label;
    }
    
    public EnumVO(String code, String label, String name) {
        this.code = code;
        this.label = label;
        this.name = name;
    }
    
    public EnumVO(String code, String label, String name, Integer order) {
        this.code = code;
        this.label = label;
        this.name = name;
        this.order = order;
    }
    
    // getter和setter
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getOrder() {
        return order;
    }
    
    public void setOrder(Integer order) {
        this.order = order;
    }
}