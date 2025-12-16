package com.lanjii.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private static String STATIC_UPLOAD_PATH;
    private static String STATIC_ACCESS_PREFIX;

    public FileProperties() {
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