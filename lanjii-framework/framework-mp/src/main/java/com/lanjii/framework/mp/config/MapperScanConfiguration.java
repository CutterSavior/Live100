package com.lanjii.framework.mp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mapper 扫描配置
 *
 * @author lanjii
 */
@Configuration
@SuppressWarnings("SpringComponentScan")
@MapperScan("com.lanjii.**.dao")
public class MapperScanConfiguration {
}