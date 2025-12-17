package com.ruoyi.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 异步任务对象 async_task
 * 
 * @author Rupert
 * @date 2025-12-16
 */
public class AsyncTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String taskId;

    /** 任务编码 */
    @Excel(name = "任务编码")
    private String taskCode;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 总数 */
    @Excel(name = "总数")
    private Long total;

    /** 成功数 */
    @Excel(name = "成功数")
    private Long success;

    /** 跳过数 */
    @Excel(name = "跳过数")
    private Long skip;

    /** 失败数 */
    @Excel(name = "失败数")
    private Long failure;

    /** 失败文件URL */
    @Excel(name = "失败文件URL")
    private String failFileUrl;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskStatus;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    public void setTaskId(String taskId) 
    {
        this.taskId = taskId;
    }

    public String getTaskId() 
    {
        return taskId;
    }

    public void setTaskCode(String taskCode) 
    {
        this.taskCode = taskCode;
    }

    public String getTaskCode() 
    {
        return taskCode;
    }

    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }

    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }

    public void setTotal(Long total) 
    {
        this.total = total;
    }

    public Long getTotal() 
    {
        return total;
    }

    public void setSuccess(Long success) 
    {
        this.success = success;
    }

    public Long getSuccess() 
    {
        return success;
    }

    public void setSkip(Long skip) 
    {
        this.skip = skip;
    }

    public Long getSkip() 
    {
        return skip;
    }

    public void setFailure(Long failure) 
    {
        this.failure = failure;
    }

    public Long getFailure() 
    {
        return failure;
    }

    public void setFailFileUrl(String failFileUrl) 
    {
        this.failFileUrl = failFileUrl;
    }

    public String getFailFileUrl() 
    {
        return failFileUrl;
    }

    public void setTaskStatus(String taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus() 
    {
        return taskStatus;
    }

    public void setFinishTime(Date finishTime) 
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() 
    {
        return finishTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("taskCode", getTaskCode())
            .append("taskName", getTaskName())
            .append("fileName", getFileName())
            .append("total", getTotal())
            .append("success", getSuccess())
            .append("skip", getSkip())
            .append("failure", getFailure())
            .append("failFileUrl", getFailFileUrl())
            .append("taskStatus", getTaskStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("finishTime", getFinishTime())
            .toString();
    }
}
