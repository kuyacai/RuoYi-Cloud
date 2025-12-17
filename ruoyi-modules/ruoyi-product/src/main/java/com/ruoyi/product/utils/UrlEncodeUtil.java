package com.ruoyi.product.utils;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UrlEncodeUtil {

    /**
     * 对 URL 的“路径部分”进行编码，其余部分保持原样
     * @param rawUrl 原始 URL，例如：
     *        http://127.0.0.1:9300/statics/2025/12/17/导出SKU_20251207_男装-116-原价(1)_20251217180636A001.xlsx
     * @return 编码后可安全用于 HttpURLConnection 的 URL
     */
    public static String encodeUrlPath(String rawUrl) {
        try {
            // 1. 找到第一个问号，把查询串先拆出来（如果没有问号就整串当路径）
            int qMark = rawUrl.indexOf('?');
            String beforeQ;
            String afterQ = "";
            if (qMark != -1) {
                beforeQ = rawUrl.substring(0, qMark);
                afterQ = rawUrl.substring(qMark);   // 包含问号本身
            } else {
                beforeQ = rawUrl;
            }

            // 2. 找到协议://host:port 的结束位置
            int hostEnd = beforeQ.indexOf('/', beforeQ.indexOf("://") + 3);
            if (hostEnd == -1) {
                // 没有路径，直接返回
                return rawUrl;
            }
            String prefix = beforeQ.substring(0, hostEnd); // http://127.0.0.1:9300
            String pathPart = beforeQ.substring(hostEnd);  // /statics/2025/12/17/导出SKU....xlsx

            // 3. 对路径部分逐段编码
            String[] segments = pathPart.split("/");
            StringBuilder encodedPath = new StringBuilder();
            for (int i = 0; i < segments.length; i++) {
                if (segments[i].isEmpty()) continue; // 连续 / 会出现空串
                encodedPath.append('/')
                           .append(URLEncoder.encode(segments[i], "UTF-8")
                                              .replace("+", "%20")); // 空格统一转 %20
            }

            // 4. 把 %2F 还原成 /
            String fixedPath = encodedPath.toString().replace("%2F", "/");

            // 5. 拼回查询串
            return prefix + fixedPath + afterQ;
        } catch (UnsupportedEncodingException e) {
            // UTF-8 必支持，不会走到这里
            throw new RuntimeException(e);
        }
    }

    // 简单测试
    public static void main(String[] args) throws Exception {
        String raw = "http://127.0.0.1:9300/statics/2025/12/17/导出SKU_20251207_男装-116-原价(1)_20251217180636A001.xlsx";
        String encoded = encodeUrlPath(raw);
        System.out.println("编码后 URL：" + encoded);

        // 直接打开连接
        URL url = new URL(encoded);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        System.out.println("响应码：" + conn.getResponseCode());
    }
}