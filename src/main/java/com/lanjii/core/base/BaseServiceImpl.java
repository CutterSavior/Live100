package com.lanjii.core.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanjii.core.annotation.Where;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.util.WhereUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 基础 Service 实现类
 *
 * @author lizheng
 * @date 2024-09-25
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    /**
     * 用参数对象作为过滤条件，获取查询结果。
     *
     * @param filter Dto 过滤对象，使用 {@link Where} 修饰过滤字段。
     * @return 返回过滤后的数据。
     */
    @Override
    public <D> List<T> getListByFilter(D filter) {
        return getListByFilter(filter, null);
    }

    /**
     * 用参数对象作为过滤条件，获取唯一查询结果。
     *
     * @param filter Dto 过滤对象，使用 {@link Where} 修饰过滤字段。
     * @return 返回过滤后的唯一数据。
     */
    @Override
    public <D> T getOneByFilter(D filter) {
        List<T> listByFilter = getListByFilter(filter);
        if (CollectionUtils.isEmpty(listByFilter)) {
            return null;
        }
        return listByFilter.get(0);
    }

    /**
     * 用参数对象作为过滤条件，获取查询结果。
     *
     * @param filter     Dto 过滤对象，使用 {@link Where} 修饰过滤字段。
     * @param orderParam 排序参数
     * @return 返回过滤后的数据。
     */
    @Override
    public <D> List<T> getListByFilter(D filter, OrderParam orderParam) {
        return this.baseMapper.selectList(WhereUtils.build(filter, orderParam));
    }
}
