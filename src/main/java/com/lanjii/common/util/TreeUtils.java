package com.lanjii.common.util;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeUtils {

    /**
     * 简化版构建树方法（使用默认字段）
     *
     * @param list 扁平列表
     * @param <E>  元素类型
     * @return 树形结构
     */
    public static <E> List<E> buildTree(List<E> list) {
        return buildTree(list, 0L); // 默认根parentId为0
    }

    /**
     * 简化版构建树方法（可指定根parentId）
     *
     * @param list         扁平列表
     * @param rootParentId 根节点的parentId值
     * @param <E>          元素类型
     * @param <T>          ID类型
     * @return 树形结构
     */
    public static <T, E> List<E> buildTree(List<E> list, T rootParentId) {
        // 使用反射创建默认的函数
        Function<E, T> idGetter = e -> getFieldValue(e, "id");
        Function<E, T> parentIdGetter = e -> getFieldValue(e, "parentId");
        BiConsumer<E, List<E>> childrenSetter = (e, children) -> setFieldValue(e, "children", children);

        // 检查是否有sort字段
        boolean hasSort = list.stream().anyMatch(e -> getFieldValue(e, "sortOrder") != null);
        Function<E, Comparable<?>> sortGetter = hasSort ?
                e -> getFieldValue(e, "sortOrder") :
                e -> getFieldValue(e, "id");

        return buildTree(list, idGetter, parentIdGetter, childrenSetter, sortGetter, rootParentId);
    }

    /**
     * 完整版构建树方法
     */
    public static <T, E> List<E> buildTree(
            List<E> list,
            Function<E, T> idGetter,
            Function<E, T> parentIdGetter,
            BiConsumer<E, List<E>> childrenSetter,
            Function<E, ? extends Comparable<?>> sortGetter,
            T rootParentId) {

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        Map<T, List<E>> parentIdToNodesMap = list.stream()
                .filter(node -> !Objects.equals(parentIdGetter.apply(node), rootParentId))
                .collect(Collectors.groupingBy(parentIdGetter));

        list.forEach(node -> {
            List<E> children = parentIdToNodesMap.get(idGetter.apply(node));
            if (children != null) {
                sortChildren(children, sortGetter);
                childrenSetter.accept(node, children);
            }
        });

        List<E> roots = list.stream()
                .filter(node -> Objects.equals(parentIdGetter.apply(node), rootParentId))
                .collect(Collectors.toList());

        sortChildren(roots, sortGetter);
        return roots;
    }

    /**
     * 排序子节点
     */
    @SuppressWarnings("unchecked")
    private static <E> void sortChildren(
            List<E> children,
            Function<E, ? extends Comparable<?>> sortGetter) {
        if (children == null || children.isEmpty()) return;

        Comparator<E> comparator = Comparator.comparing(
                e -> (Comparable<Object>) sortGetter.apply(e),
                Comparator.nullsLast(Comparator.naturalOrder())
        );
        children.sort(comparator);
    }

    // 反射辅助方法
    @SuppressWarnings("unchecked")
    private static <T> T getFieldValue(Object obj, String fieldName) {
        try {
            return (T) obj.getClass().getMethod("get" + capitalize(fieldName)).invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    private static void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            final Class<?> paramType;

            if (value == null) {
                paramType = Object.class;
            } else if (value instanceof ArrayList) {
                paramType = List.class;
            } else {
                paramType = value.getClass();
            }

            String methodName = String.format("set%s", capitalize(fieldName));

            Method setter = obj.getClass().getMethod(methodName, paramType);
            setter.invoke(obj, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field: " + fieldName, e);
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}