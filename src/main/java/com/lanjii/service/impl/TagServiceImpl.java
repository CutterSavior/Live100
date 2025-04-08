package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.TagMapper;
import com.lanjii.model.entity.Tag;
import com.lanjii.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 标签表 服务实现类
 *
 * @author lizheng
 * @date 2025-03-29
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends BaseServiceImpl<TagMapper, Tag> implements ITagService {

}
