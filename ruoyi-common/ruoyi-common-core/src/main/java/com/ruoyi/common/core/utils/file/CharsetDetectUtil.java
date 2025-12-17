package com.ruoyi.common.core.utils.file;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * 文件编码探测工具
 *
 * @author ruoyi
 * @date 2025-12-14
 */
public class CharsetDetectUtil {

    /**
     * 探测上传文件的真实编码，返回 Java 标准 Charset
     */
    public static Charset detect(InputStream in) throws IOException {
        byte[] buf = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);
        int n;
        while ((n = in.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, n);
        }
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        // 常见中文编码兜底
        if (encoding == null) {
            return Charset.defaultCharset();
        }
        if ("GB2312".equalsIgnoreCase(encoding) || "GBK".equalsIgnoreCase(encoding)) {
            return Charset.forName("GBK");
        }
        if ("UTF-8".equalsIgnoreCase(encoding)) {
            return StandardCharsets.UTF_8;
        }
        return Charset.forName(encoding);
    }
}