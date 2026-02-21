package com.lanjii.framework.mp.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service基础接口
 *
 * @author lanjii
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 通用列表查询方法
     *
     * @param filter 动态查询DTO
     * @return 列表结果
     */
    <D> List<T> listByFilter(D filter);
}