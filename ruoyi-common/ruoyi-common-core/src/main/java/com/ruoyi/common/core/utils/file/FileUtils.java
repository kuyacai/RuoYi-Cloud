package com.ruoyi.common.core.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import com.ruoyi.common.core.utils.StringUtils;

/**
 * 文件处理工具类
 * 
 * @author ruoyi
 */
public class FileUtils
{
    /** 字符常量：斜杠 {@code '/'} */
    public static final char SLASH = '/';

    /** 字符常量：反斜杠 {@code '\\'} */
    public static final char BACKSLASH = '\\';

    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     * 
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 文件名称验证
     * 
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename)
    {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 校验文件路径合法性（安全性与扩展名）
     * 
     * @param fileUrl 待校验的文件地址
     * @return true 正常 false 非法
     */
    public static boolean validateFilePath(String fileUrl)
    {
        // 禁止目录上跳级别
        if (StringUtils.contains(fileUrl, ".."))
        {
            return false;
        }
        // 判断是否在允许下载的文件规则内
        return ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(fileUrl));
    }

    /**
     * 下载文件名重新编码
     * 
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException
    {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE"))
        {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        }
        else if (agent.contains("Firefox"))
        {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }
        else if (agent.contains("Chrome"))
        {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        else
        {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 返回文件名
     *
     * @param filePath 文件
     * @return 文件名
     */
    public static String getName(String filePath)
    {
        if (null == filePath)
        {
            return null;
        }
        int len = filePath.length();
        if (0 == len)
        {
            return filePath;
        }
        if (isFileSeparator(filePath.charAt(len - 1)))
        {
            // 以分隔符结尾的去掉结尾分隔符
            len--;
        }

        int begin = 0;
        char c;
        for (int i = len - 1; i > -1; i--)
        {
            c = filePath.charAt(i);
            if (isFileSeparator(c))
            {
                // 查找最后一个路径分隔符（/或者\）
                begin = i + 1;
                break;
            }
        }

        return filePath.substring(begin, len);
    }

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param c 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    public static boolean isFileSeparator(char c)
    {
        return SLASH == c || BACKSLASH == c;
    }

    /**
     * 下载文件名重新编码
     *
     * @param response 响应对象
     * @param realFileName 真实文件名
     * @return
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException
    {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.setHeader("Content-disposition", contentDispositionValue.toString());
        response.setHeader("download-filename", percentEncodedFileName);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException
    {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }
    /**
     * 获取文件后缀名（包括点）
     * 例如：test.txt -> .txt
     * 
     * @param filename 文件名
     * @return 文件后缀名，如 .txt、.xlsx
     */
    public static String getFileSuffix(String filename) {
        if (filename == null) {
            return null;
        }
        
        // 获取文件名（去掉路径）
        String name = getName(filename);
        if (name == null) {
            return null;
        }
        
        // 查找最后一个点号的位置
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < name.length() - 1) {
            return name.substring(dotIndex);
        }
        
        return "";
    }
    
    /**
     * 获取文件后缀名（不包括点）
     * 例如：test.txt -> txt
     * 
     * @param filename 文件名
     * @return 文件后缀名，如 txt、xlsx
     */
    public static String getFileExtension(String filename) {
        String suffix = getFileSuffix(filename);
        if (StringUtils.isNotEmpty(suffix) && suffix.length() > 1) {
            return suffix.substring(1);
        }
        return "";
    }
    
    /**
     * 获取不带后缀的文件名
     * 例如：test.txt -> test
     * 
     * @param filename 文件名
     * @return 不包含后缀的文件名
     */
    public static String getFileNameWithoutSuffix(String filename) {
        if (filename == null) {
            return null;
        }
        
        String name = getName(filename);
        if (name == null) {
            return null;
        }
        
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex > 0) {
            return name.substring(0, dotIndex);
        }
        
        return name;
    }
    
    /**
     * 判断是否是允许的文件类型
     * 
     * @param filename 文件名
     * @param allowedExtensions 允许的后缀数组，如 {"xlsx", "xls", "csv"}
     * @return 是否允许
     */
    public static boolean isAllowedExtension(String filename, String[] allowedExtensions) {
        if (StringUtils.isEmpty(filename) || allowedExtensions == null || allowedExtensions.length == 0) {
            return false;
        }
        
        String extension = getFileExtension(filename).toLowerCase();
        for (String allowedExt : allowedExtensions) {
            if (allowedExt.toLowerCase().equals(extension)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 检查是否是图片文件
     * 
     * @param filename 文件名
     * @return 是否是图片
     */
    public static boolean isImage(String filename) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp", "webp"};
        return isAllowedExtension(filename, imageExtensions);
    }
    
    /**
     * 检查是否是文档文件
     * 
     * @param filename 文件名
     * @return 是否是文档
     */
    public static boolean isDocument(String filename) {
        String[] docExtensions = {"doc", "docx", "pdf", "txt", "xls", "xlsx", "ppt", "pptx"};
        return isAllowedExtension(filename, docExtensions);
    }
    
    /**
     * 检查是否是视频文件
     * 
     * @param filename 文件名
     * @return 是否是视频
     */
    public static boolean isVideo(String filename) {
        String[] videoExtensions = {"mp4", "avi", "mov", "wmv", "flv", "mkv"};
        return isAllowedExtension(filename, videoExtensions);
    }
}
