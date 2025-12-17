package com.ruoyi.product.service.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.SocketTimeoutException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.product.mq.dto.AsyncTaskMsg;
import com.ruoyi.product.service.IAsyncTaskService;
import com.ruoyi.product.utils.ProductFileUtils;
import com.ruoyi.product.utils.UrlEncodeUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.product.constant.AsyncTaskStatus;
import com.ruoyi.product.domain.AsyncTask;
import com.ruoyi.product.domain.ItemProcessResult;
import com.ruoyi.product.domain.MiaoShouSKU;
import com.ruoyi.common.core.utils.DateUtils;

@Component
@RocketMQMessageListener(topic = "async-task-product-topic", selectorType = SelectorType.TAG, selectorExpression = "sku_import", consumerGroup = "product-sku-import-group", consumeMode = ConsumeMode.ORDERLY, // 顺序消费（如果需要）
        messageModel = MessageModel.CLUSTERING,
        // 关键配置：消费失败重试策略
        consumeTimeout = 15, // 消费超时时间（分钟）
        maxReconsumeTimes = 3 // 最大重试次数
)
public class SkuImportTaskConsumer implements RocketMQListener<AsyncTaskMsg> {

    private static final Logger log = LoggerFactory.getLogger(SkuImportTaskConsumer.class);
    private static final int BATCH_UPDATE_SIZE = 50; // 每处理50条更新一次数据库
    private final SkuImportService skuImportService;
    private final IAsyncTaskService asyncTaskService;

    @Autowired
    public SkuImportTaskConsumer(SkuImportService skuImportService,
            IAsyncTaskService asyncTaskService) {
        this.skuImportService = skuImportService;
        this.asyncTaskService = asyncTaskService;
    }

    @Override
    public void onMessage(AsyncTaskMsg msg) {
        log.debug("SkuImportTaskConsumer 收到消息: {}", msg);
        String taskId = msg.getTaskId();
        String fileUrl = msg.getExt().get("fileUrl");
        String shopId = msg.getExt().get("shopId"); // 如果前端上传时带上了
        if (StringUtils.isBlank(shopId))
            shopId = null;

        // 1.获取任务详情
        AsyncTask task = asyncTaskService.selectAsyncTaskByTaskId(taskId);
        if (task == null) {
            log.error("任务不存在。taskId:{}", taskId);
            return;
        }
        File tempFile = null;
        try {
            // 2. 下载文件
            log.info("开始下载文件: {}", fileUrl);
            tempFile = downloadToTemp(fileUrl);
            if (tempFile == null) {
                log.error("获取文件失败。fileUrl:{}", fileUrl);
                task.setTaskStatus(AsyncTaskStatus.DONE.getCode());
                task.setFinishTime(DateUtils.getNowDate());
                task.setFailFileUrl("获取文件失败。fileUrl:" + fileUrl);
                asyncTaskService.updateAsyncTask(task);
                return;
            }
            // 2. 解析Excel文件
            log.info("开始处理SKU导入任务: taskId={}, file={}", taskId, tempFile.getAbsolutePath());
            List<MiaoShouSKU> skuList = parseExcelFile(tempFile);

            log.info("解析到 {} 条SKU数据", skuList.size());

            task.setTaskStatus(AsyncTaskStatus.DOING.getCode());
            task.setTotal((long) skuList.size());
            asyncTaskService.updateAsyncTask(task);

            // 4. 收集错误信息（用于生成失败报告）
            List<String> errorMessages = new ArrayList<>();

            // 5. 逐条处理SKU
            for (int i = 0; i < skuList.size(); i++) {
                MiaoShouSKU sku = skuList.get(i);

                ItemProcessResult result = skuImportService.processSingleSku(sku, shopId);

                // 6. 根据处理结果更新进度
                if (result.isSuccess()) {
                    task.setSuccess(task.getSuccess() + 1);
                } else if (result.isSkipped()) {
                    task.setSkip(task.getSkip() + 1);
                    errorMessages.add(formatErrorMessage(sku, "跳过", result.getErrorReason()));
                } else {
                    task.setFailure(task.getFailure() + 1);
                    errorMessages.add(formatErrorMessage(sku, "失败", result.getErrorReason()));
                }

                // 7. 定期批量更新数据库（减少数据库压力）
                if ((i + 1) % BATCH_UPDATE_SIZE == 0 || (i + 1) == skuList.size()) {
                    asyncTaskService.updateAsyncTask(task);
                    log.info("进度: {}/{} (成功:{} 跳过:{} 失败:{})",
                            i + 1, skuList.size(),
                            task.getSuccess(), task.getSkip(), task.getFailure());
                }
            }
            // 8. 最终完成处理
            String failFileUrl = null;
            if (!errorMessages.isEmpty()) {
                failFileUrl = ProductFileUtils.uploadFailFile(taskId, errorMessages);
            }

            task.setTaskStatus(AsyncTaskStatus.DONE.getCode());
            task.setFinishTime(DateUtils.getNowDate());
            task.setFailFileUrl(failFileUrl);
            asyncTaskService.updateAsyncTask(task);

            log.info("SKU导入任务完成: taskId={}, 总计={}, 成功={}, 跳过={}, 失败={}",
                    taskId, skuList.size(), task.getSuccess(), task.getSkip(), task.getFailure());

        } catch (IOException e) {
            log.error("处理SKU导入任务失败: taskId={}", taskId, e);
            asyncTaskService.finish(taskId, null, AsyncTaskStatus.DONE.getCode());
        } finally {
            // 9. 清理临时文件
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }

    }

    private File downloadToTemp(String fileUrl) {
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
     * 安静地关闭资源（不抛出异常）
     */
    private void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // 安静地忽略关闭异常
                log.trace("关闭资源时忽略异常", e);
            }
        }
    }

    /**
     * 更完善的文件名提取方法（处理URL编码）
     */
    private String getFileNameFromUrl(String fileUrl) {
        try {
            // 创建URL对象
            URL url = new URL(fileUrl);
            String path = url.getPath();

            if (StringUtils.isBlank(path) || "/".equals(path)) {
                return "import_file_" + System.currentTimeMillis() + ".xlsx";
            }

            // 提取最后一个斜杠后的部分
            int lastSlashIndex = path.lastIndexOf('/');
            String fileName = (lastSlashIndex != -1 && lastSlashIndex < path.length() - 1)
                    ? path.substring(lastSlashIndex + 1)
                    : path;

            // 处理URL编码（如 %20 -> 空格, %E4%B8%AD -> 中）
            try {
                fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
            } catch (Exception e) {
                log.warn("URL解码失败: {}, 使用原始名称", fileName);
            }

            // 清理文件名（移除非法字符）
            fileName = cleanFileName(fileName);

            // 如果还是没有有效的文件名，使用默认
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
     * 清理文件名，移除非法字符
     */
    private String cleanFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return fileName;
        }

        // 移除Windows/Unix非法字符
        String cleaned = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");

        // 移除开头和结尾的点或空格
        cleaned = cleaned.trim().replaceAll("^\\.+", "").replaceAll("\\.+$", "");

        // 确保不为空
        if (StringUtils.isBlank(cleaned)) {
            cleaned = "file_" + System.currentTimeMillis();
        }

        return cleaned;
    }

    /**
     * 格式化错误信息
     */
    private String formatErrorMessage(MiaoShouSKU sku, String status, String reason) {
        String msg = String.format("商品ID:%s, SKUID:%s, 状态:%s, 原因:%s",
                sku.getProductId(), sku.getSkuId(), status, reason);
        log.info(msg);
        return msg;
    }

    private List<MiaoShouSKU> parseExcelFile(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            ExcelUtil<MiaoShouSKU> util = new ExcelUtil<>(MiaoShouSKU.class);
            return util.importExcel(is, 0);
        }
    }
}