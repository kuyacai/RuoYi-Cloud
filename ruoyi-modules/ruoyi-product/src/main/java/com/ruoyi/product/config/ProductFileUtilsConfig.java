package com.ruoyi.product.config;

import com.ruoyi.product.feign.FileServiceClient;
import com.ruoyi.product.utils.ProductFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductFileUtilsConfig {

    @Autowired
    public void setFileClient(FileServiceClient client) {
        ProductFileUtils.setFileClient(client);
    }
}