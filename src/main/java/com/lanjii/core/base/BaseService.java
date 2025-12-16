package com.lanjii.core.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BaseService<T> extends IService<T> {

    /**
     * 通用列表查询方法
     *
     * @param filter 动态查询DTO
     * @return 列表结果
     */
    <D> List<T> listByFilter(D filter);
}