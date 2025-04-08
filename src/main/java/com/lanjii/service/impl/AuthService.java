package com.lanjii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.security.AuthUser;
import com.lanjii.util.JwtTokenUtil;
import com.lanjii.dao.SysUserRoleRelMapper;
import com.lanjii.model.dto.LoginBody;
import com.lanjii.model.entity.SysUser;
import com.lanjii.model.entity.SysUserRoleRel;
import com.lanjii.service.INoticeService;
import com.lanjii.model.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务类
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Component
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final SysUserRoleRelMapper sysUserRoleRelMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final INoticeService noticeService;

    public Map<String, Object> login(LoginBody loginBody) {
        // 将表单数据封装到 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword());
        // authenticate方法会调用loadUserByUsername
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (authenticate == null) {
            throw new UsernameNotFoundException(ResultCode.WRONG_USERNAME_OR_PASSWORD.getMsg());
        }
        AuthUser authUser = (AuthUser) authenticate.getPrincipal();
        if (authUser.getSysUser().getStatus() == 0) {
            throw new DisabledException(ResultCode.USER_IS_DISABLED.getMsg());
        }

        Map<String, Object> m = new HashMap<>();
        m.put("token", jwtTokenUtil.generateToken(authUser.getUsername()));
        m.put("permissions", authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray());

        SysUserVo sysUserVo = SysUser.INSTANCE.modelToVo(authUser.getSysUser());
        LambdaQueryWrapper<SysUserRoleRel> relQuery = new LambdaQueryWrapper<>();
        relQuery.eq(SysUserRoleRel::getUserId, authUser.getSysUser().getId());
        m.put("userInfo", sysUserVo);
        return m;
    }


}
