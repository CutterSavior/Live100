package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.exception.BusinessException;
import com.lanjii.dao.SysConfigMapper;
import com.lanjii.model.dto.SysConfigDto;
import com.lanjii.model.entity.SysConfig;
import com.lanjii.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  系统配置表 服务实现类
 *
 * @author lizheng
 * @date 2025-03-28
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    /**
     * 获取配置项值
     *
     * @param configName 配置名称
     */
    @Override
    public String getValue(String configName) {
        SysConfig sysConfig = getByName(configName);
        if (sysConfig == null) {
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR, "[" + configName + "]配值不存在");
        }
        return sysConfig.getConfigValue();
    }

    /**
     * 获取配置项
     *
     * @param configName 配置名称
     */
    @Override
    public SysConfig getByName(String configName) {
        SysConfigDto filter = new SysConfigDto();
        filter.setConfigName(configName);
        return getOneByFilter(filter);
    }

}
