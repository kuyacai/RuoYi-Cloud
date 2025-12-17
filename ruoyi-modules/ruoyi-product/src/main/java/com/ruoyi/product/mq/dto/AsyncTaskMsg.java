// com.ruoyi.product.mq.dto.AsyncTaskMsg
package com.ruoyi.product.mq.dto;

import java.io.Serializable;
import java.util.Map;


public class AsyncTaskMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskId;
    private String taskCode;
    private Map<String,String> ext;   // 业务扩展字段
    public AsyncTaskMsg() {
    }
    public AsyncTaskMsg(String taskId) {
        this.taskId = taskId;
    }

    public AsyncTaskMsg(String taskId, String taskCode) {
        this.taskId = taskId;
        this.taskCode = taskCode;
    }
    public AsyncTaskMsg(String taskId, String taskCode, Map<String, String> ext) {
        this.taskId = taskId;
        this.taskCode = taskCode;
        this.ext = ext;
    }
    
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getTaskCode() {
        return taskCode;
    }
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
    public Map<String, String> getExt() {
        return ext;
    }
    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }

    
}