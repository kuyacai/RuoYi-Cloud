package com.ruoyi.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysFile;

// com.ruoyi.product.feign.FileServiceClient
@FeignClient(value = "ruoyi-file", contextId = "fileClient")
public interface FileServiceClient {

    /**
     * 上传文件到 ruoyi-file 模块
     * 注意：必须使用 @PostMapping + @RequestPart，否则 MultipartFile 传不过去
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<SysFile> uploadFile(@RequestPart("file") MultipartFile file);
}