package com.lanjii.system.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 登录返回信息
 *
 * @author lanjii
 */
@Data
@AllArgsConstructor
public class LoginInfo {

    private String token;

    private List<SysMenuVO> menusTree;

    private SysUserVO sysUser;

    /**
     * 权限字符列表，供前端控制按钮显示
     */
    private List<String> permissions;

    /**
     * 显示用的UUID，用于前端识别自己的会话
     */
    private String displayUuid;

}