package com.lanjii.framework.mp.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * Service基础实现类
 *
 * @author lanjii
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {


    @Override
    public <D> List<T> listByFilter(D filter) {
        return this.baseMapper.selectList(QueryWrapperBuilder.build(filter));
    }
}