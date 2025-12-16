package com.lanjii.biz.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.lanjii.biz.admin.system.dao.SysConfigDao;
import com.lanjii.biz.admin.system.model.dto.SysConfigDTO;
import com.lanjii.biz.admin.system.model.vo.SysConfigVO;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.biz.admin.system.model.entity.SysConfig;
import com.lanjii.common.exception.BizException;
import com.lanjii.biz.admin.system.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.lanjii.biz.admin.system.model.entity.SysConfig.INSTANCE;

/**
 * 系统配置表(SysConfig)表服务实现类
 *
 * @author lanjii
 */
@Service("sysConfigService")
@RequiredArgsConstructor
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {

    private final Cache<String, SysConfig> configCache;

    @Override
    public void updateByIdNew(Long id, SysConfigDTO dto) {
        SysConfig originalConfig = getById(id);
        if (originalConfig == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "配置不存在");
        }

        assertConfigKeyUnique(dto.getConfigKey(), id);

        SysConfig entity = INSTANCE.toEntity(dto);
        entity.setId(id);
        updateById(entity);
        configCache.invalidate(originalConfig.getConfigKey());
    }

    @Override
    public void saveNew(SysConfigDTO dto) {

		assertConfigKeyUnique(dto.getConfigKey(), null);
        SysConfig entity = INSTANCE.toEntity(dto);
        save(entity);
    }

    @Override
    public SysConfigVO getByIdNew(Long id) {
        SysConfig entity = getById(id);
        return INSTANCE.toVo(entity);
    }

    /**
     * 根据配置键获取配置信息
     */
    public SysConfig getConfigByKey(String configKey) {
        // 先从缓存中获取
        SysConfig config = configCache.getIfPresent(configKey);
        if (config != null) {
            return config;
        }

        // 缓存中没有，从数据库查询
        config = getOne(new LambdaQueryWrapper<SysConfig>()
                .eq(SysConfig::getConfigKey, configKey));

        // 存入缓存
        if (config != null) {
            configCache.put(configKey, config);
        }

        return config;
    }

    /**
     * 根据配置键获取配置值
     */
    public String getConfigValue(String configKey) {
        SysConfig config = getConfigByKey(configKey);
        return config != null ? config.getConfigValue() : null;
    }

	@Override
	public void assertConfigKeyUnique(String configKey, Long excludeId) {
		LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<SysConfig>()
				.eq(SysConfig::getConfigKey, configKey);
		if (excludeId != null) {
			wrapper.ne(SysConfig::getId, excludeId);
		}
		boolean exists = baseMapper.exists(wrapper);
		if (exists) {
			throw BizException.validationError(ResultCode.CONFLICT, "配置键名已存在");
		}
	}

	@Override
	public void clearCache() {
		configCache.invalidateAll();
	}
}
