package com.lanjii.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Json 序列化工具
 *
 * @author lizheng
 * @date 2024-09-12
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = Optional.ofNullable(SpringUtils.getApplicationContext())
                .map(applicationContext -> SpringUtils.getBean(ObjectMapper.class))
                .orElse(new ObjectMapper());
    }

    public static String objToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("序列化失败", e);
        }
        return null;
    }

    @SneakyThrows
    public static <T> T jsonToObj(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

}
