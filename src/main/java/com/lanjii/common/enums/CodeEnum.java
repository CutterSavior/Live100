package com.lanjii.common.enums;

/**
 * 枚举编码接口
 *
 * @author lizheng
 */
public interface CodeEnum<T> {

    T getCode();

    String getDesc();

    /**
     * 根据 code 获取对应的枚举值
     *
     * @param enumClass 枚举类
     * @param code      要查找的 code
     * @param <E>       枚举类型
     * @param <T>       code 类型
     * @return 匹配的枚举值，未找到返回 null
     */
    static <E extends Enum<E> & CodeEnum<T>, T> E getByCode(Class<E> enumClass, T code) {
        if (code == null) {
            return null;
        }
        for (E e : enumClass.getEnumConstants()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }
}