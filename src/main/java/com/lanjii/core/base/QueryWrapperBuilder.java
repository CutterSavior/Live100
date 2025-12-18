package com.lanjii.core.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.annotation.SortField;
import com.lanjii.core.enums.QueryType;
import com.lanjii.core.enums.SortOrder;
import com.lanjii.common.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * 查询构造器
 *
 * @author lizheng
 */
public class QueryWrapperBuilder {

    public static <T, D> QueryWrapper<T> build(D dto) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (dto == null) {
            return queryWrapper;
        }

        Field[] fields = dto.getClass().getDeclaredFields();

        // 先处理查询条件
        processQueryConditions(queryWrapper, dto, fields);

        // 再处理排序字段
        processSortFields(queryWrapper, dto, fields);

        return queryWrapper;
    }

    private static <T, D> void processQueryConditions(QueryWrapper<T> wrapper, D dto, Field[] fields) {
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(dto);
                if (value == null) {
                    continue;
                }

                QueryCondition queryCondition = field.getAnnotation(QueryCondition.class);
                if (queryCondition == null) {
                    continue;
                }

                String fieldName = queryCondition.field().isEmpty()
                        ? camelToUnderline(field.getName())
                        : queryCondition.field();

                handleQueryCondition(wrapper, queryCondition.type(), fieldName, value);
            } catch (IllegalAccessException e) {
                throw new BizException("构建查询条件失败", e);
            }
        }
    }

    private static <T, D> void processSortFields(QueryWrapper<T> wrapper, D dto, Field[] fields) {
        // 创建一个列表来保存排序字段信息
        List<SortFieldInfo> sortFields = new ArrayList<>();
        boolean hasUserSortField = false;

        // 首先收集所有需要排序的字段
        for (Field field : fields) {
            field.setAccessible(true);
            SortField sortField = field.getAnnotation(SortField.class);

            if (sortField == null) {
                continue;
            }

            String fieldName = sortField.field().isEmpty()
                    ? camelToUnderline(field.getName())
                    : sortField.field();

            // 检查是否有用户指定的排序字段（非默认优先级）
            if (sortField.priority() != Integer.MAX_VALUE) {
                hasUserSortField = true;
            }

            // 将排序字段信息添加到列表中
            sortFields.add(new SortFieldInfo(fieldName, sortField.order(), sortField.priority()));
        }

        // 按照优先级排序（数值越小优先级越高）
        sortFields.sort(Comparator.comparingInt(SortFieldInfo::getPriority));

        // 按顺序应用到QueryWrapper
        for (SortFieldInfo sortField : sortFields) {
            if (sortField.getOrder() == SortOrder.ASC) {
                wrapper.orderByAsc(sortField.getFieldName());
            } else {
                wrapper.orderByDesc(sortField.getFieldName());
            }
        }

        // 如果没有用户指定的排序字段，则添加默认的时间排序
        if (!hasUserSortField) {
            // 添加更新时间排序（倒序，优先级第二）
            wrapper.orderByDesc("update_time");
            // 添加创建时间排序（倒序，优先级最低）
            wrapper.orderByDesc("create_time");
        }
    }

    private static <T> void handleQueryCondition(QueryWrapper<T> wrapper, QueryType type,
                                                 String fieldName, Object value) {
        switch (type) {
            case EQUAL:
                wrapper.eq(fieldName, value);
                break;
            case LIKE:
                wrapper.like(fieldName, value);
                break;
            case LEFT_LIKE:
                wrapper.likeLeft(fieldName, value);
                break;
            case RIGHT_LIKE:
                wrapper.likeRight(fieldName, value);
                break;
            case GT:
                wrapper.gt(fieldName, value);
                break;
            case LT:
                wrapper.lt(fieldName, value);
                break;
            case GE:
                wrapper.ge(fieldName, value);
                break;
            case LE:
                wrapper.le(fieldName, value);
                break;
            case IN:
                if (value instanceof Collection) {
                    wrapper.in(fieldName, (Collection<?>) value);
                } else if (value.getClass().isArray()) {
                    wrapper.in(fieldName, (Object[]) value);
                }
                break;
            case BETWEEN:
                if (value instanceof Object[] betweenValues) {
                    if (betweenValues.length == 2) {
                        wrapper.between(fieldName, betweenValues[0], betweenValues[1]);
                    }
                }
                break;
        }
    }

    /**
     * 驼峰转下划线
     */
    private static String camelToUnderline(String camelStr) {
        if (StringUtils.isEmpty(camelStr)) {
            return camelStr;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < camelStr.length(); i++) {
            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {
                builder.append('_').append(Character.toLowerCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    @Data
    @AllArgsConstructor
    private static class SortFieldInfo {
        private final String fieldName;
        private final SortOrder order;
        private final int priority;
    }
}