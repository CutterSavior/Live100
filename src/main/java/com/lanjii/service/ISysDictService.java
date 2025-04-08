package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.entity.SysDict;

/**
 * 字典表 服务类
 *
 * @author lizheng
 * @date 2024-10-10
 */
public interface ISysDictService extends IBaseService<SysDict> {

    void removeByIdNew(Long id);

    void updateNew(SysDict sysDict, SysDict originalData);
}
