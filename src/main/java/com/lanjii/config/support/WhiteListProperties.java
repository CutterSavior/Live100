package com.lanjii.config.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author lizheng
 * @date 2024-10-10
 */
@Data
@ConfigurationProperties(prefix = "security.white")
public class WhiteListProperties {

    List<String> urls;
}
