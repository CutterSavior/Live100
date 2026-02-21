package com.lanjii.sys.controller;


import com.github.pagehelper.PageHelper;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import com.lanjii.common.response.R;
import com.lanjii.sys.entity.SysConfig;
import com.lanjii.system.api.dto.SysConfigDTO;
import com.lanjii.sys.service.SysConfigService;
import com.lanjii.framework.mp.base.PageDataUtils;
import com.lanjii.system.api.vo.SysConfigVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/configs")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService sysConfigService;

    /**
     * 查询配置项
     *
     * @param configKey 配置键名
     */
    @GetMapping("/key/{configKey}")
    public R<String> getConfigValue(@PathVariable String configKey) {
        return R.success(sysConfigService.getConfigValue(configKey));
    }

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:config:view')")
    @GetMapping("/{id}")
    public R<SysConfigVO> getById(@PathVariable Long id) {
        return R.success(sysConfigService.getByIdNew(id));
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter    查询条件
     */
    @PreAuthorize("hasAuthority('sys:config:page')")
    @GetMapping
    public R<PageData<SysConfigVO>> page(PageParam pageParam, SysConfigDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysConfig> sysConfigList = sysConfigService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysConfigList, SysConfig.INSTANCE));
    }

    /**
     * 保存
     *
     * @param dto 保存的对象
     */
    @Log(title = "系统配置管理", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:config:save')")
    @PostMapping
    public R<Void> save(@Validated @RequestBody SysConfigDTO dto) {
        sysConfigService.saveNew(dto);
        return R.success();
    }

    /**
     * 更新
     *
     * @param id  数据ID
     * @param dto 更新的对象
     */
    @Log(title = "系统配置管理", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:config:update')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysConfigDTO dto) {
        sysConfigService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 数据 ID
     */
    @Log(title = "系统配置管理", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:config:delete')")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        sysConfigService.removeById(id);
        return R.success();
    }

    /**
     * 清除系统配置缓存
     */
    @Log(title = "清除配置缓存", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:config:cache')")
    @PostMapping("/cache/clear")
    public R<Void> clearCache() {
        sysConfigService.clearCache();
        return R.success();
    }
}
