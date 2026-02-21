package com.lanjii.sys.base;

import com.lanjii.sys.entity.SysDept;
import com.lanjii.sys.entity.SysUser;
import com.lanjii.sys.service.SysConfigService;
import com.lanjii.sys.service.SysDeptService;
import com.lanjii.sys.service.SysUserService;
import com.lanjii.framework.web.util.SpringUtils;
import com.lanjii.framework.mp.base.BaseEntityMapper;
import org.mapstruct.Named;

/**
 * Admin 模块 MapStruct 基础接口
 * 继承 BaseEntityMapper，添加业务相关的辅助方法
 *
 * @author lanjii
 */
public interface AdminBaseMapper<E, V, D> extends BaseEntityMapper<E, V, D> {

    @Named("getConfig")
    default String getConfig(String configKey) {
        return SpringUtils.getBean(SysConfigService.class).getConfigValue(configKey);
    }

    @Named("deptIdToName")
    default String deptIdToName(Long deptId) {
        if (deptId == null) {
            return null;
        }
        SysDept dept = SpringUtils.getBean(SysDeptService.class).getById(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getDeptName();
    }

    @Named("usernameToDeptName")
    default String usernameToDeptName(String username) {
        if (username == null) {
            return null;
        }
        SysUser user = SpringUtils.getBean(SysUserService.class).getUserByUsername(username);
        if (user == null || user.getDeptId() == null) {
            return null;
        }
        SysDept dept = SpringUtils.getBean(SysDeptService.class).getById(user.getDeptId());
        if (dept == null) {
            return null;
        }
        return dept.getDeptName();
    }
}
