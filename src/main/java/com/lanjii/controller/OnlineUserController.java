package com.lanjii.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.obj.R;
import com.lanjii.model.dto.OnlineUserDto;
import com.lanjii.model.entity.OnlineUser;
import com.lanjii.model.vo.OnlineUserVo;
import com.lanjii.service.IOnlineUserService;
import com.lanjii.util.PageDataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 在线用户
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
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<OnlineUserVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                          @MultiRequestBody(required = false) OnlineUserDto onlineUserFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<OnlineUser> list = onlineUserService.getListByFilter(onlineUserFilter, orderParam);
        return R.success(PageDataUtils.make(list, OnlineUser.INSTANCE));
    }

}
