package com.lanjii.core.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanjii.core.annotation.Where;
import com.lanjii.core.base.support.OrderParam;

import java.util.List;

/**
 * 基础 Service 接口
 *
 * @author lizheng
 * @date 2024-09-25
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 用参数对象作为过滤条件，获取查询结果。
     *
     * @param filter Dto 过滤对象，使用 {@link Where} 修饰过滤字段。
     * @return 返回过滤后的数据。
     */
    <D> List<T> getListByFilter(D filter);

    /**
     * 用参数对象作为过滤条件，获取唯一查询结果。
     *
     * @param filter Dto 过滤对象，使用 {@link Where} 修饰过滤字段。
     * @return 返回过滤后的唯一数据。
     */
    <D> T getOneByFilter(D filter);

    /**
     * 用参数对象作为过滤条件，获取查询结果。
     *
     * @param filter     Dto 过滤对象，使用 {@link Where} 修饰过滤字段。
     * @param orderParam 排序参数
     * @return 返回过滤后的数据。
     */
    <D> List<T> getListByFilter(D filter, OrderParam orderParam);

}
