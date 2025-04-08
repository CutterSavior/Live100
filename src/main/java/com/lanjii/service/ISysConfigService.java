package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.entity.SysConfig;

/**
 *  系统配置表 服务类
 *
 * @author lizheng
 * @date 2025-03-28
 */
public interface ISysConfigService extends IBaseService<SysConfig> {

    /**
     * 获取配置项值
     *
     * @param configName 配置名称
     */
    String getValue(String configName);


    /**
     * 获取配置项
     *
     * @param configName 配置名称
     */
    SysConfig getByName(String configName);

}
