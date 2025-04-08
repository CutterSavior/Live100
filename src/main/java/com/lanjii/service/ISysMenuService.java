package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.dto.SysMenuDto;
import com.lanjii.model.entity.SysMenu;
import com.lanjii.model.vo.SysMenuVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
public interface ISysMenuService extends IBaseService<SysMenu> {

    List<SysMenu> getByUserId(Long userId);

    List<SysMenu> getByUserId(Long userId, Boolean onlyMenu);

    List<SysMenu> getByUsername(String username);

    List<String> getResourceByUserId(Long userId);

    SysMenuVo getByIdNew(Long id);

    void saveOrUpdateNew(SysMenuDto sysMenuDto);

    void removeByIdNew(Long id);
}
