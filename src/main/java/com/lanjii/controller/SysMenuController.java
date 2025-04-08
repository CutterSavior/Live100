package com.lanjii.controller;


import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.util.AuthUtils;
import com.lanjii.util.PageDataUtils;
import com.lanjii.model.dto.SysMenuDto;
import com.lanjii.model.dto.SysUserDto;
import com.lanjii.model.entity.SysMenu;
import com.lanjii.model.entity.SysUser;
import com.lanjii.service.ISysMenuService;
import com.lanjii.model.vo.SysMenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@RestController
@RequestMapping("/admin/sys-menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final ISysMenuService sysMenuService;

    /**
     * 获取当前用户的菜单
     */
    @GetMapping("/current")
    public List<SysMenuVo> listMenu() {
        SysUser user = AuthUtils.getCurrentUser().getSysUser();
        List<SysMenu> menus = sysMenuService.getByUserId(user.getId(), true);
        return SysMenu.INSTANCE.modelToVo(menus);
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<SysMenuVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                       @MultiRequestBody(required = false) SysUserDto sysUserFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysMenu> list = sysMenuService.getListByFilter(sysUserFilter, orderParam);
        return R.success(PageDataUtils.make(list, SysMenu.INSTANCE));
    }

    /**
     * 详情
     */
    @GetMapping("/query")
    public R<SysMenuVo> query(Long id) {
        return R.success(sysMenuService.getByIdNew(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<SysMenu> save(@MultiRequestBody SysMenuDto sysMenuDto) {
        sysMenuService.saveOrUpdateNew(sysMenuDto);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<SysMenu> update(@MultiRequestBody SysMenuDto sysMenuDto) {
        SysMenu originalSysMenu = sysMenuService.getById(sysMenuDto.getId());
        if (originalSysMenu == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        sysMenuService.saveOrUpdateNew(sysMenuDto);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<SysMenu> delete(Long id) {
        if (sysMenuService.getById(id) == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        sysMenuService.removeByIdNew(id);
        return R.success();
    }

}
