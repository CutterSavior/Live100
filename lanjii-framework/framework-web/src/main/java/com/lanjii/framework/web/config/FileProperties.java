package com.lanjii.framework.web.config;

import com.lanjii.framework.web.util.FileUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件上传配置属性类
 *
 * @author lanjii
 */
@Component
@ConfigurationProperties(prefix = "lanjii.file")
public class FileProperties {

    private static String STATIC_UPLOAD_PATH;
    private static String STATIC_ACCESS_PREFIX;

    @PostConstruct
    public void init() {
        FileUtils.setConfigSuppliers(FileProperties::getUploadPath, FileProperties::getAccessPrefix);
    }

    public void setUploadPath(String uploadPath) {
        STATIC_UPLOAD_PATH = uploadPath;
    }

    public void setAccessPrefix(String accessPrefix) {
        STATIC_ACCESS_PREFIX = accessPrefix;
    }

    public static String getUploadPath() {
        return STATIC_UPLOAD_PATH;
    }

    public static String getAccessPrefix() {
        return STATIC_ACCESS_PREFIX;
    }

}
