package com.lanjii.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Jackson JSON 工具类
 *
 * @author lanjii
 */
public class JacksonUtils {
    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);

    /**
     * -- GETTER --
     * 获取配置好的ObjectMapper实例
     *
     * @return ObjectMapper实例
     */
    @Getter
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // 初始化配置
        mapper.registerModule(new JavaTimeModule()); // 支持Java8时间类型
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 禁用时间戳
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // 忽略未知属性
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT); // 空字符串转为null
    }

    private JacksonUtils() {
        // 防止实例化
    }

    /**
     * 对象转为JSON字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("对象转JSON失败", e);
            throw new RuntimeException("对象转JSON失败", e);
        }
    }

    /**
     * 对象转为格式化的JSON字符串
     *
     * @param obj 对象
     * @return 格式化后的JSON字符串
     */
    public static String toPrettyJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("对象转格式化JSON失败", e);
            throw new RuntimeException("对象转格式化JSON失败", e);
        }
    }

    /**
     * JSON字符串转为对象
     *
     * @param json  JSON字符串
     * @param clazz 目标类
     * @param <T>   泛型类型
     * @return 对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("JSON转对象失败", e);
            throw new RuntimeException("JSON转对象失败", e);
        }
    }

    /**
     * JSON字符串转为复杂类型对象
     *
     * @param json          JSON字符串
     * @param typeReference 类型引用
     * @param <T>           泛型类型
     * @return 对象
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("JSON转复杂对象失败", e);
            throw new RuntimeException("JSON转复杂对象失败", e);
        }
    }

    /**
     * JSON字符串转为List
     *
     * @param json  JSON字符串
     * @param clazz List元素类
     * @param <T>   泛型类型
     * @return List
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            log.error("JSON转List失败", e);
            throw new RuntimeException("JSON转List失败", e);
        }
    }

    /**
     * JSON字符串转为Map
     *
     * @param json       JSON字符串
     * @param keyClass   Map键类
     * @param valueClass Map值类
     * @param <K>        键类型
     * @param <V>        值类型
     * @return Map
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClass, Class<V> valueClass) {
        if (json == null || json.isEmpty()) {
            return Collections.emptyMap();
        }

        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (IOException e) {
            log.error("JSON转Map失败", e);
            throw new RuntimeException("JSON转Map失败", e);
        }
    }

}