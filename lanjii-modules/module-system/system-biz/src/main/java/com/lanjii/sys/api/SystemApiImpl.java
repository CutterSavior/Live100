package com.lanjii.sys.api;

import com.lanjii.sys.entity.SysDept;
import com.lanjii.sys.entity.SysUser;
import com.lanjii.sys.service.SysConfigService;
import com.lanjii.sys.service.SysDeptService;
import com.lanjii.sys.service.SysUserService;
import com.lanjii.common.constant.SysConfigKeys;
import com.lanjii.framework.context.tenant.TenantContext;
import com.lanjii.system.api.SystemApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统模块API实现类
 *
 * @author lanjii
 */
@Service
@RequiredArgsConstructor
public class SystemApiImpl implements SystemApi {

    private final SysUserService sysUserService;
    private final SysDeptService sysDeptService;
    private final SysConfigService sysConfigService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTenantAdmin(Long tenantId, String tenantCode) {
        Long previousTenantId = TenantContext.getTenantId();
        try {
            TenantContext.setTenantId(tenantId);

            // 创建默认部门
            SysDept dept = new SysDept();
            dept.setTenantId(tenantId);
            dept.setParentId(0L);
            dept.setAncestors("0");
            dept.setDeptName(tenantCode + "总部");
            dept.setSortOrder(1);
            dept.setIsEnabled(1);
            dept.setLeader("admin");
            sysDeptService.save(dept);

            // 创建管理员用户
            String defaultPwd = sysConfigService.getConfigValue(SysConfigKeys.DEFAULT_USER_PWD);
            SysUser admin = new SysUser();
            admin.setTenantId(tenantId);
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(defaultPwd));
            admin.setNickname(tenantCode + "-管理员");
            admin.setIsEnabled(1);
            admin.setIsAdmin(1);
            admin.setDeptId(dept.getId());
            sysUserService.save(admin);

        } finally {
            TenantContext.setTenantId(previousTenantId);
        }
    }
}
