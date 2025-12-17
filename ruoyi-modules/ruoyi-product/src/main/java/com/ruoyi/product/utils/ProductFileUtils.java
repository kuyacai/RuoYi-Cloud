package com.ruoyi.product.utils;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.product.feign.FileServiceClient;
import com.ruoyi.system.api.domain.SysFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
//import com.ruoyi.product.utils.SimpleMultipartFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

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

    public static String uploadFailFile(String originalTaskId, List<String> failLines) {
        if (failLines == null || failLines.isEmpty()) {
            return null;
        }
        String fileName = originalTaskId + "_fail.xlsx"; // 1. 扩展名改 xlsx
        File tempFile = null;
        try {
            tempFile = File.createTempFile(originalTaskId, ".xlsx");

            // 2. 用 XSSF 生成 xlsx
            try (Workbook wb = new XSSFWorkbook(); // 2007+ 格式
                    OutputStream os = Files.newOutputStream(tempFile.toPath())) {

                Sheet sheet = wb.createSheet("fail");
                int rowIdx = 0;
                for (String line : failLines) {
                    Row row = sheet.createRow(rowIdx++);
                    // 按逗号拆列，如业务需要别的分隔符自行调整
                    String[] cells = line.split(",", -1);
                    for (int i = 0; i < cells.length; i++) {
                        row.createCell(i).setCellValue(cells[i]);
                    }
                }
                wb.write(os);
            }

            // 3. 上传（其余逻辑不动）
            byte[] bytes = Files.readAllBytes(tempFile.toPath());
            MultipartFile multipartFile = new SimpleMultipartFile(fileName, fileName,
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", bytes); // 4. MIME 类型改 xlsx

            R<SysFile> rsp = fileClient.uploadFile(multipartFile);
            if (rsp.getCode() == 200) {
                return rsp.getData().getUrl();
            }
            log.error("上传失败文件到ruoyi-file出错：{}", rsp.getMsg());
            return null;

        } catch (Exception e) {
            log.error("生成或上传失败文件异常", e);
            return null;
        } finally {
            if (tempFile != null) {
                tempFile.delete(); // 5. 清理临时文件
            }
        }
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