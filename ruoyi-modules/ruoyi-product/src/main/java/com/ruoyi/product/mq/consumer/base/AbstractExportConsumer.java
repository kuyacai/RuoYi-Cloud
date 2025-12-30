package com.ruoyi.product.mq.consumer.base;

import java.util.List;

import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruoyi.product.domain.AsyncTask;
import com.ruoyi.product.enums.AsyncTaskStatus;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.product.service.excel.ExcelDataHandler;
import com.ruoyi.product.utils.EnhancedExcelUtil;
import com.ruoyi.product.utils.ProductFileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractExportConsumer<T> implements RocketMQListener<AsyncTaskMsg> {

    @Autowired
    protected IAsyncTaskService asyncTaskService;

    @Override
    public void onMessage(AsyncTaskMsg msg) {
        String taskId = msg.getTaskId();
        log.info("开始处理异步导出任务: {}", taskId);

        // 1. 更新任务状态为“进行中”
        asyncTaskService.updateProgress(taskId, 0, 0, 0);
        AsyncTask task = asyncTaskService.getById(taskId);

        try {
            // 2. 获取业务数据
            List<T> data = getExportHandler().getExportData(task.getParams());

            // 3. 生成 Excel 并保存

            // 生成文件名：业务名_时间戳.xlsx
            String fileName = task.getTaskName() + "_" + System.currentTimeMillis() + ".xlsx";

            // 执行异步导出并上传
            String fileUrl = ProductFileUtils.uploadByWriter(taskId, fileName, os -> {
                Class<T> clazz = getDtoClass();
                EnhancedExcelUtil<T> util = new EnhancedExcelUtil<>(clazz);
                // 调用你在 ExcelUtil 中新加的 exportExcel(OutputStream, List, String)
                util.exportExcel(os, data, "数据详情");
            });

            // 4. 结束任务
            // 复用 finish 方法，将生成的 URL 填入 importedFileUrl 字段（导出场景即为结果链接）
            asyncTaskService.finish(taskId, fileUrl, AsyncTaskStatus.DONE);
            log.info("异步导出任务完成: {}", taskId);

        } catch (Exception e) {
            log.error("异步导出任务失败: {}", taskId, e);
            asyncTaskService.finish(taskId, null, AsyncTaskStatus.DONE); // 扩展状态或记录错误
        }
    }

    protected abstract ExcelDataHandler<T> getExportHandler();

    protected abstract Class<T> getDtoClass();

}