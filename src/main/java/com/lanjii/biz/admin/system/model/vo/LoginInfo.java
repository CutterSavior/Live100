package com.lanjii.biz.admin.system.model.vo;

import com.lanjii.biz.admin.system.model.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// 登录返回VO
@Data
@AllArgsConstructor
public class LoginInfo {

    private String token;

    private List<SysMenuVO> menusTree;

    private SysUser sysUser;

    /**
     * 权限字符列表，供前端控制按钮显示
     */
    private List<String> permissions;

    /**
     * 显示用的UUID，用于前端识别自己的会话
     */
    private String displayUuid;

}