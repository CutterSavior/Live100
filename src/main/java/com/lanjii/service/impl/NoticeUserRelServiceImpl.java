package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.NoticeUserRelMapper;
import com.lanjii.model.entity.NoticeUserRel;
import com.lanjii.service.INoticeUserRelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  文章和人员关联表 服务实现类
 *
 * @author lizheng
 * @date 2025-04-01
 */
@Service
@RequiredArgsConstructor
public class NoticeUserRelServiceImpl extends BaseServiceImpl<NoticeUserRelMapper, NoticeUserRel> implements INoticeUserRelService {

}
