package com.lanjii.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.sys.dao.SysConfigDao;
import com.lanjii.system.api.dto.SysConfigDTO;
import com.lanjii.system.api.vo.SysConfigVO;
import com.lanjii.framework.mp.base.BaseServiceImpl;
import com.lanjii.common.response.ResultCode;
import com.lanjii.sys.entity.SysConfig;
import com.lanjii.common.exception.BizException;
import com.lanjii.sys.service.SysConfigService;
import com.lanjii.sys.config.SystemCacheConstants;
import com.lanjii.framework.cache.helper.CacheHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import static com.lanjii.sys.entity.SysConfig.INSTANCE;

/**
 * 系统配置表(SysConfig)表服务实现类
 *
 * @author lanjii
 */
@Service("sysConfigService")
@RequiredArgsConstructor
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {

    private final CacheManager cacheManager;
    private final CacheHelper cacheHelper;

    private Cache getConfigCache() {
        return cacheManager.getCache(SystemCacheConstants.SYS_CONFIG.getName());
    }

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
        getConfigCache().evict(originalConfig.getConfigKey());
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
        Cache cache = getConfigCache();
        
        // 先从缓存中获取
        SysConfig config = cache.get(configKey, SysConfig.class);
        if (config != null) {
            return config;
        }

        // 缓存中没有，从数据库查询
        config = getOne(new LambdaQueryWrapper<SysConfig>()
                .eq(SysConfig::getConfigKey, configKey));

        // 存入缓存
        if (config != null) {
            cache.put(configKey, config);
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
		cacheHelper.clearAll(SystemCacheConstants.SYS_CONFIG.getName());
	}
}
