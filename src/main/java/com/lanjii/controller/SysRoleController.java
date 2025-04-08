
package com.lanjii.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.model.dto.SysRoleDto;
import com.lanjii.model.entity.SysRole;
import com.lanjii.service.ISysRoleService;
import com.lanjii.util.ModelUtils;
import com.lanjii.util.PageDataUtils;
import com.lanjii.model.vo.SysRoleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色表管理
 *
 * @author lizheng
 * @date 2024-09-28
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys-role")
@RequiredArgsConstructor
public class SysRoleController {

    private final ISysRoleService sysRoleService;

    /**
     * 获取菜单
     *
     * @return 结果
     */
    @GetMapping("/list-menu-ids")
    public R<List<Long>> listMenuIds(Long id) {
        return R.success(sysRoleService.listMenuIds(id));
    }

    /**
     * 分配菜单
     *
     * @return 结果
     */
    @PostMapping("/assign-menu")
    public R<SysRole> assignMenu(@MultiRequestBody Long id, @MultiRequestBody List<Long> menuIds) {
        sysRoleService.assignMenu(id, menuIds);
        return R.success();
    }

    /**
     * 分页查询
     *
     * @param pageParam     分页对象
     * @param orderParam    排序对象
     * @param sysRoleFilter 过滤对象
     * @return 分页结果
     */
    @PostMapping("/list")
    public R<PageData<SysRoleVo>> list(@MultiRequestBody PageParam pageParam, @MultiRequestBody OrderParam orderParam,
                                       @MultiRequestBody SysRoleDto sysRoleFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysRole> list = sysRoleService.getListByFilter(sysRoleFilter, orderParam);
        return R.success(PageDataUtils.make(list, SysRole.INSTANCE));
    }


    /**
     * 详情
     *
     * @param id 主键
     * @return 查询对象
     */
    @GetMapping("/query")
    public R<SysRole> query(Long id) {
        return R.success(sysRoleService.getById(id));
    }

    /**
     * 保存
     *
     * @param sysRoleDto 保存的对象
     * @return 结果
     */
    @PostMapping("/save")
    public R<SysRole> save(@MultiRequestBody SysRoleDto sysRoleDto) {
        SysRole sysRole = ModelUtils.copyTo(sysRoleDto, SysRole.class);
        sysRoleService.save(sysRole);
        return R.success();
    }

    /**
     * 更新
     *
     * @param sysRoleDto 更新的对象
     * @return 结果
     */
    @PostMapping("/update")
    public R<SysRole> update(@MultiRequestBody SysRoleDto sysRoleDto) {
        if (sysRoleDto.getId() == 1) {
            return R.fail("超级管理员禁止操作");
        }
        SysRole originalSysRole = sysRoleService.getById(sysRoleDto.getId());
        if (originalSysRole == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        SysRole sysRole = ModelUtils.copyTo(sysRoleDto, SysRole.class);
        sysRoleService.updateById(sysRole);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 需要删除对象的主键
     * @return 结果
     */
    @GetMapping("/del")
    public R<SysRole> delete(Long id) {
        if (id == 1) {
            return R.fail("超级管理员禁止操作");
        }
        sysRoleService.removeById(id);
        return R.success();
    }
}

