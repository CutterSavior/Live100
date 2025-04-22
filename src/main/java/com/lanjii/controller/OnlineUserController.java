package com.lanjii.controller;

import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.obj.R;
import com.lanjii.model.dto.OnlineUserDto;
import com.lanjii.model.vo.OnlineUserVo;
import com.lanjii.service.IOnlineUserService;
import com.lanjii.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 在线用户控制器
 * 负责在线用户的查询、踢下线等操作
 * 使用缓存而非数据库存储在线用户
 *
 * @author lizheng
 * @date 2025-04-09
 */
@Slf4j
@RestController
@RequestMapping("/admin/online-user")
@RequiredArgsConstructor
public class OnlineUserController {

    private final IOnlineUserService onlineUserService;

    /**
     * 用户列表查询 - 从缓存中获取在线用户
     *
     * @param pageParam 分页参数
     * @param orderParam 排序参数
     * @param onlineUserFilter 过滤条件
     * @return 符合条件的用户列表
     */
    @PostMapping("/list")
    public R<PageData<OnlineUserVo>> list(@MultiRequestBody(required = false) PageParam pageParam, 
                                          @MultiRequestBody(required = false) OrderParam orderParam,
                                          @MultiRequestBody(required = false) OnlineUserDto onlineUserFilter) {
        // 从缓存中获取在线用户列表
        PageData<OnlineUserVo> pageData = onlineUserService.getOnlineUserList(pageParam, orderParam, onlineUserFilter);
        return R.success(pageData);
    }

    /**
     * 获取指定用户的在线信息
     * 
     * @param userId 要查询的用户ID
     * @return 用户的在线信息，如果用户不在线则返回错误信息
     */
    @GetMapping("/getUserOnline")
    public R<OnlineUserVo> getUserOnline(@RequestParam Long userId) {
        OnlineUserVo onlineUser = onlineUserService.getOnlineUserById(userId);
        if (onlineUser == null) {
            return R.fail("用户未在线");
        }
        return R.success(onlineUser);
    }

    /**
     * 踢下线操作
     * 将指定用户踢下线，会清除用户的token缓存
     * 注意：不能踢自己下线
     *
     * @param userId 要踢下线的用户ID
     * @return 处理结果
     */
    @GetMapping("/kickOut")
    public R<String> kickOut(@RequestParam Long userId) {
        Long currentUserId = AuthUtils.getCurrentUser().getSysUser().getId();
        boolean success = onlineUserService.kickOut(userId, currentUserId);

        if (!success) {
            return R.fail("操作失败");
        }

        return R.success("用户已被踢下线");
    }
}
