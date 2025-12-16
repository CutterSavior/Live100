package com.lanjii.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaginationConfig {

    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }
}