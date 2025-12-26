// AbstractImportConsumer.java
package com.ruoyi.product.mq.consumer.base;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.product.utils.ProductFileUtils;
import com.ruoyi.product.utils.UrlEncodeUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.product.constant.AsyncTaskStatus;
import com.ruoyi.product.domain.AsyncTask;
import com.ruoyi.product.domain.dto.ItemProcessResult;

/**
 * 异步任务导入消费基类
 * 
 * @param <T> 处理的DTO类型
 */
@Slf4j
public abstract class AbstractImportConsumer<T> {

    protected static final int BATCH_UPDATE_SIZE = 50; // 每处理50条更新一次数据库

    @Autowired
    protected IAsyncTaskService asyncTaskService;

    /**
     * 处理消息的模板方法
     */
    protected void handleMessage(AsyncTaskMsg msg) {
        String taskId = msg.getTaskId();
        // 安全地从 Map<String, Object> 获取 String
        Map<String, Object> ext = msg.getExt();
        String fileUrl = ext != null && ext.get("fileUrl") != null ? String.valueOf(ext.get("fileUrl")) : null;
        String shopId = ext != null && ext.get("shopId") != null ? String.valueOf(ext.get("shopId")) : null;

        if (StringUtils.isBlank(shopId)) {
            shopId = null;
        }

        // 1.获取任务详情
        AsyncTask task = asyncTaskService.getById(taskId);
        if (task == null) {
            log.error("任务不存在。taskId:{}", taskId);
            return;
        }
        // 临时文件
        File tempFile = null;
        // 收集错误信息
        List<String> errorMessages = new ArrayList<>();
        // 失败信息记录文件
        String failFileUrl = null;
        try {
            // 2. 下载文件
            log.info("开始下载文件: {}", fileUrl);
            tempFile = downloadToTemp(fileUrl);
            if (tempFile == null) {
                log.error("获取文件失败。fileUrl:{}", fileUrl);
                // task.setTaskStatus(AsyncTaskStatus.DONE.getCode());
                // task.setFinishTime(DateUtils.getNowDate());
                errorMessages.add("获取文件失败。fileUrl:" + fileUrl);
                failFileUrl = ProductFileUtils.uploadFailFile(taskId, errorMessages);
                task.setFailFileUrl(failFileUrl);
                asyncTaskService.finish(taskId, failFileUrl, AsyncTaskStatus.DONE.getCode());
                return;
            }

            // 3. 解析Excel文件（抽象方法，子类实现）
            log.info("开始处理导入任务: taskId={}, file={}", taskId, tempFile.getAbsolutePath());
            List<T> itemList = parseExcelFile(tempFile);
            log.info("解析到 {} 条数据", itemList.size());

            // 更新任务为进行中，并设置总数
            AsyncTask doingTask = new AsyncTask();
            doingTask.setTaskId(taskId);
            doingTask.setTaskStatus(AsyncTaskStatus.DOING.getCode());
            doingTask.setTotal(itemList.size());
            asyncTaskService.updateById(doingTask);

            // 4. 逐条处理数据
            processItems(itemList, shopId, task, errorMessages);

            // 5. 最终完成处理

            if (!errorMessages.isEmpty()) {
                failFileUrl = ProductFileUtils.uploadFailFile(taskId, errorMessages);
            }

            // 使用优化后的 finish 方法更新最终状态
            asyncTaskService.finish(taskId, failFileUrl, AsyncTaskStatus.DONE.getCode());

            // 重新获取一次最新进度用于日志打印（可选）
            AsyncTask finalTask = asyncTaskService.getById(taskId);
            log.info("导入任务完成: taskId={}, 总计={}, 成功={}, 跳过={}, 失败={}",
                    taskId, itemList.size(), finalTask.getSuccess(), finalTask.getSkip(), finalTask.getFailure());

        } catch (IOException e) {
            log.error("处理导入任务失败: taskId={}", taskId, e);
            asyncTaskService.finish(taskId, null, AsyncTaskStatus.DONE.getCode());
        } finally {
            // 7. 清理临时文件
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * 处理数据项列表（可被子类覆盖）
     */
    protected void processItems(List<T> items, String shopId, AsyncTask task,
            List<String> errorMessages) {
        String taskId = task.getTaskId();
        int successCount = 0;
        int skipCount = 0;
        int failCount = 0;

        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);

            ItemProcessResult result = processSingleItem(item, shopId);

            // 根据处理结果更新进度
            if (result.isSuccess()) {
                successCount++;
            } else if (result.isSkipped()) {
                skipCount++;
                errorMessages.add(formatErrorMessage(item, "跳过", result.getErrorReason()));
            } else {
                failCount++;
                errorMessages.add(formatErrorMessage(item, "失败", result.getErrorReason()));
            }

            // 定期批量更新数据库（减少数据库压力）
            if ((i + 1) % BATCH_UPDATE_SIZE == 0 || (i + 1) == items.size()) {
                asyncTaskService.updateProgress(taskId, successCount, skipCount, failCount);

                log.info("进度批量同步成功: {}/{}", i + 1, items.size());

                // 同步后清空本地计数器，以便下次重新累加增量
                successCount = 0;
                skipCount = 0;
                failCount = 0;
            }
        }
    }

    // ==================== 抽象方法 ====================

    /**
     * 解析Excel文件
     */
    protected abstract List<T> parseExcelFile(File file) throws IOException;

    /**
     * 处理单个数据项
     */
    protected abstract ItemProcessResult processSingleItem(T item, String shopId);

    /**
     * 格式化错误信息
     */
    protected abstract String formatErrorMessage(T item, String status, String reason);

    // ==================== 通用工具方法 ====================

    /**
     * 下载文件到临时目录
     */
    protected File downloadToTemp(String fileUrl) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            log.debug("开始下载文件: {}", fileUrl);

            // 1. 创建HTTP连接
            String encoded = UrlEncodeUtil.encodeUrlPath(fileUrl);
            URL url = new URL(encoded);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000); // 15秒连接超时
            connection.setReadTimeout(300000); // 5分钟读取超时
            connection.setRequestProperty("User-Agent", "RuoYi-Product-Import/1.0");
            connection.setRequestProperty("Accept", "*/*");

            // 2. 检查响应
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                log.error("下载文件失败，HTTP状态码: {}, 错误信息: {}",
                        responseCode, connection.getResponseMessage());
                return null;
            }

            // 3. 获取文件大小
            long contentLength = connection.getContentLengthLong();
            log.debug("文件大小: {} bytes", contentLength);

            // 4. 安全限制：最大500MB
            long MAX_SIZE = 500L * 1024 * 1024; // 500MB
            if (contentLength > MAX_SIZE) {
                log.error("文件过大: {} bytes > 最大限制 {} bytes",
                        contentLength, MAX_SIZE);
                return null;
            }

            // 5. 创建临时文件
            String fileName = getFileNameFromUrl(fileUrl);
            File tempFile = File.createTempFile("import_", "_" + fileName, null);
            tempFile.deleteOnExit(); // JVM退出时删除

            log.debug("临时文件: {}", tempFile.getAbsolutePath());

            // 6. 下载文件
            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(tempFile);

            byte[] buffer = new byte[16384]; // 16KB缓冲区
            long totalRead = 0;
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalRead += bytesRead;

                // 进度日志（每5MB）
                if (totalRead % (5 * 1024 * 1024) == 0) {
                    log.debug("下载进度: {} bytes", totalRead);
                }
            }

            outputStream.flush();

            log.info("✅ 文件下载成功: {} -> {} ({} bytes)",
                    fileName, tempFile.getAbsolutePath(), totalRead);

            // 7. 验证文件
            if (tempFile.length() == 0) {
                log.error("下载的文件为空");
                tempFile.delete();
                return null;
            }

            return tempFile;

        } catch (SocketTimeoutException e) {
            log.error("下载超时: {}", fileUrl, e);
            return null;
        } catch (IOException e) {
            log.error("下载IO错误: {}", fileUrl, e);
            return null;
        } catch (Exception e) {
            log.error("下载失败: {}", fileUrl, e);
            return null;
        } finally {
            // 确保资源关闭
            closeQuietly(inputStream);
            closeQuietly(outputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 安静地关闭资源
     */
    protected void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                log.trace("关闭资源时忽略异常", e);
            }
        }
    }

    /**
     * 从URL提取文件名
     */
    protected String getFileNameFromUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath();

            if (StringUtils.isBlank(path) || "/".equals(path)) {
                return "import_file_" + System.currentTimeMillis() + ".xlsx";
            }

            int lastSlashIndex = path.lastIndexOf('/');
            String fileName = (lastSlashIndex != -1 && lastSlashIndex < path.length() - 1)
                    ? path.substring(lastSlashIndex + 1)
                    : path;

            try {
                fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
            } catch (Exception e) {
                log.warn("URL解码失败: {}, 使用原始名称", fileName);
            }

            fileName = cleanFileName(fileName);

            if (StringUtils.isBlank(fileName) || fileName.length() > 255) {
                return "import_file_" + System.currentTimeMillis() + ".xlsx";
            }

            return fileName;

        } catch (Exception e) {
            log.warn("提取文件名失败: {}, 使用默认名称", fileUrl, e);
            return "import_file_" + System.currentTimeMillis() + ".xlsx";
        }
    }

    /**
     * 清理文件名
     */
    protected String cleanFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return fileName;
        }

        String cleaned = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
        cleaned = cleaned.trim().replaceAll("^\\.+", "").replaceAll("\\.+$", "");

        if (StringUtils.isBlank(cleaned)) {
            cleaned = "file_" + System.currentTimeMillis();
        }

        return cleaned;
    }
}