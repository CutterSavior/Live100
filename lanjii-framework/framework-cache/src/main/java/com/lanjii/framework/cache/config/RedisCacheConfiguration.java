package com.lanjii.framework.cache.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lanjii.framework.cache.core.CacheDef;
import com.lanjii.framework.cache.core.CacheRegistry;
import com.lanjii.framework.cache.properties.LanjiiCacheProperties;
import com.lanjii.framework.context.tenant.TenantContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis缓存自动配置
 *
 * @author lanjii
 */
@Configuration
@ConditionalOnProperty(name = "lanjii.cache.type", havingValue = "REDIS")
public class RedisCacheConfiguration {

    @Bean
    @ConditionalOnClass(RedisConnectionFactory.class)
    public CacheManager redisCacheManager(CacheRegistry cacheRegistry,
                                          LanjiiCacheProperties properties,
                                          RedisConnectionFactory connectionFactory) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.activateDefaultTyping(
                objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        org.springframework.data.redis.cache.RedisCacheConfiguration defaultConfig = org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(properties.getDefaultTtl())
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .computePrefixWith(cacheName -> {
                    Long tenantId = TenantContext.getTenantId();
                    String prefix = (tenantId != null) ? tenantId.toString() : "0";
                    return prefix + ":" + cacheName + "::";
                });

        if (!properties.isCacheNullValues()) {
            defaultConfig = defaultConfig.disableCachingNullValues();
        }

        Map<String, org.springframework.data.redis.cache.RedisCacheConfiguration> configMap = new HashMap<>();
        org.springframework.data.redis.cache.RedisCacheConfiguration finalDefaultConfig = defaultConfig;
        for (CacheDef cacheDef : cacheRegistry.getAll()) {
            configMap.put(cacheDef.getName(), finalDefaultConfig.entryTtl(cacheDef.getTtl()));
        }

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(configMap)
                .build();
    }
}
