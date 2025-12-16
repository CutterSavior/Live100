package com.lanjii.biz.admin.system.service;

import com.lanjii.biz.admin.system.model.dto.SysConfigDTO;
import com.lanjii.biz.admin.system.model.entity.SysConfig;
import com.lanjii.biz.admin.system.model.vo.SysConfigVO;
import com.lanjii.core.base.BaseService;

/**
 * 系统配置表(SysConfig)表服务接口
 *
 * @author lanjii
 */
public interface SysConfigService extends BaseService<SysConfig> {

    /**
     * 根据ID更新系统配置
     *
     * @param id  配置ID
     * @param dto 配置DTO
     */
    void updateByIdNew(Long id, SysConfigDTO dto);

    /**
     * 保存系统配置
     *
     * @param dto 配置DTO
     */
    void saveNew(SysConfigDTO dto);

    /**
     * 根据ID获取系统配置详情
     *
     * @param id 配置ID
     * @return 配置视图对象
     */
    SysConfigVO getByIdNew(Long id);

    String getConfigValue(String configKey);

	/**
	 * 校验配置键名唯一性；当存在冲突时抛出业务异常。
	 *
	 * @param configKey 待校验的键名
	 * @param excludeId 排除的记录ID（更新时传入当前ID，新增传null）
	 */
	void assertConfigKeyUnique(String configKey, Long excludeId);

	/**
	 * 清除系统配置缓存
	 */
	void clearCache();

}
