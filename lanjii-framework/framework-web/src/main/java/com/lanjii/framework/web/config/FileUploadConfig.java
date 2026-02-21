package com.lanjii.framework.web.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;

/**
 * 文件上传配置类
 *
 * @author lanjii
 */
@Configuration
public class FileUploadConfig {

    /**
     * 配置MultipartConfigElement
     * 设置文件上传大小限制
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        
        // 单个文件最大大小 (100MB)
        factory.setMaxFileSize(DataSize.ofMegabytes(100));
        
        // 总上传数据最大大小 (200MB，支持多文件上传)
        factory.setMaxRequestSize(DataSize.ofMegabytes(200));

        return factory.createMultipartConfig();
    }

    /**
     * 配置MultipartResolver
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

}
