package com.ruoyi.product.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Consumer;

//import com.ruoyi.product.utils.SimpleMultipartFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.product.feign.FileServiceClient;
import com.ruoyi.system.api.domain.SysFile;

/**
 * 商品域文件上传工具（调用 ruoyi-file）
 */
public class ProductFileUtils {

    private static final Logger log = LoggerFactory.getLogger(ProductFileUtils.class);

    private static FileServiceClient fileClient;

    /* 由 Spring 注入 */
    public static void setFileClient(FileServiceClient client) {
        fileClient = client;
    }

    /**
     * 核心重构方法：通过回调（Callback）机制写入文件并上传
     * 这样可以支持 EnhancedExcelUtil 的各种导出方法
     * * @param taskId 异步任务ID
     * 
     * @param fileName 存储文件名
     * @param writer   数据写入逻辑的回调
     * @return 文件访问URL
     */
    public static String uploadByWriter(String taskId, String fileName, Consumer<OutputStream> writer) {
        File tempFile = null;
        try {
            // 1. 创建临时文件
            tempFile = File.createTempFile("async_" + taskId, ".xlsx");

            // 2. 执行数据写入逻辑（由 EnhancedExcelUtil 提供实现）
            try (OutputStream os = new FileOutputStream(tempFile)) {
                writer.accept(os);
            }

            // 3. 封装为 MultipartFile 并调用 Feign 接口上传
            byte[] bytes = Files.readAllBytes(tempFile.toPath());
            MultipartFile multipartFile = new SimpleMultipartFile(
                    fileName,
                    fileName,
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    bytes);

            R<SysFile> rsp = fileClient.uploadFile(multipartFile);
            if (rsp != null && rsp.getCode() == 200) {
                return rsp.getData().getUrl();
            }
            log.error("上传Excel到ruoyi-file失败：{}", rsp != null ? rsp.getMsg() : "响应为空");
            return null;

        } catch (Exception e) {
            log.error("生成并上传异步文件异常", e);
            return null;
        } finally {
            // 4. 清理临时文件，防止磁盘溢出
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * 保持向下兼容：上传简单的错误信息列表
     */
    public static String uploadFailFile(String taskId, List<String> failLines) {
        if (failLines == null || failLines.isEmpty())
            return null;

        return uploadByWriter(taskId, taskId + "_fail.xlsx", os -> {
            try (Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("失败原因");
                for (int i = 0; i < failLines.size(); i++) {
                    Row row = sheet.createRow(i);
                    row.createCell(0).setCellValue(failLines.get(i));
                }
                wb.write(os);
            } catch (IOException e) {
                throw new RuntimeException("生成错误文件流失败", e);
            }
        });
    }

    /**
     * 上传任意 MultipartFile 到 ruoyi-file，返回 url
     */
    public static String upload(MultipartFile file, String bizId) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            R<SysFile> rsp = fileClient.uploadFile(file);
            if (rsp.getCode() == 200) {
                return rsp.getData().getUrl();
            }
            log.error("上传文件到ruoyi-file失败：{}", rsp.getMsg());
            return null;
        } catch (Exception e) {
            log.error("上传文件异常", e);
            return null;
        }
    }
}