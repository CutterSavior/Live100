package com.lanjii.util;

import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanjii.core.annotation.Where;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.enums.Condition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 条件构造工具类
 * <p>
 * 参考 ： https://juejin.cn/post/6961694581738078215
 *
 * @author lizheng
 * @date 2024-09-25
 */
@Slf4j
@Component
public class WhereUtils {

    @SuppressWarnings("unchecked")
    private static final Map<Condition, Con3<QueryWrapper<?>, String, Object>> whereMap = new HashMap<>() {{
        put(Condition.EQ, Compare::eq);
        put(Condition.NE, Compare::ne);
        put(Condition.LT, Compare::lt);
        put(Condition.LE, Compare::le);
        put(Condition.GT, Compare::gt);
        put(Condition.GE, Compare::ge);
        put(Condition.LIKE, Compare::like);
        put(Condition.LIKE_LEFT, Compare::likeLeft);
        put(Condition.LIKE_RIGHT, Compare::likeRight);
        put(Condition.IN, (queryWrapper, column, value) -> queryWrapper.in(column, ((List<Object>) value).toArray()));
    }};

    public static <T, R> QueryWrapper<R> build(T filter, OrderParam orderParam) {
        QueryWrapper<R> queryWrapper = new QueryWrapper<>();
        if (filter == null) {
            return queryWrapper;
        }
        Class<?> filterClazz = filter.getClass();
        Field[] filterDeclaredFields = filterClazz.getDeclaredFields();
        Stream.of(filterDeclaredFields).peek(field -> field.setAccessible(true)).filter(field -> {
            try {
                Object o = field.get(filter);
                if (o instanceof String) {
                    // 字符串判断空需要判断空字符
                    return StringUtils.isNotBlank((String) o) && field.getAnnotation(Where.class) != null;
                }
                return o != null && field.getAnnotation(Where.class) != null;
            } catch (IllegalAccessException e) {
                log.error("Failed to retrieve the {} of the {}.", field.getName(), filterClazz.getName());
                return false;
            }

        }).forEach(field -> {
            try {
                Where where = field.getAnnotation(Where.class);
                Condition condition = where.value();
                String column = where.column();
                Object value = field.get(filter);
                whereMap.get(condition).invoke(queryWrapper, column, value);
            } catch (Exception e) {
                log.error("Exception during condition concatenation.", e);
            }
        });
        if (orderParam != null && orderParam.getAsc() != null && orderParam.getFieldName() != null) {
            queryWrapper.orderBy(true, orderParam.getAsc(), orderParam.getFieldName());
            return queryWrapper;
        }
        queryWrapper.orderByDesc("created_time");
        return queryWrapper;
    }

    @FunctionalInterface
    interface Con3<P1, P2, P3> {
        void invoke(P1 p1, P2 p2, P3 p3);
    }

}
