package com.lanjii.biz.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.biz.admin.system.dao.SysPostDao;
import com.lanjii.biz.admin.system.model.dto.SysPostDTO;
import com.lanjii.biz.admin.system.model.vo.SysPostVO;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.biz.admin.system.model.entity.SysPost;
import com.lanjii.common.exception.BizException;
import com.lanjii.biz.admin.system.service.SysPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统岗位表(SysPost)表服务实现类
 *
 * @author lanjii
 */
@Service("sysPostService")
@AllArgsConstructor
public class SysPostServiceImpl extends BaseServiceImpl<SysPostDao, SysPost> implements SysPostService {

    @Override
    public void updateByIdNew(Long id, SysPostDTO dto) {
        SysPost originalPost = getById(id);
        if (originalPost == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "岗位不存在");
        }

        if (!originalPost.getPostCode().equals(dto.getPostCode())) {
            LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysPost::getPostCode, dto.getPostCode())
                    .ne(SysPost::getId, dto.getId());
            if (baseMapper.exists(queryWrapper)) {
                throw BizException.validationError(ResultCode.CONFLICT, "岗位编码已存在");
            }
        }

        SysPost sysPost = SysPost.INSTANCE.toEntity(dto);
        updateById(sysPost);
    }

    @Override
    public void saveNew(SysPostDTO dto) {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPost::getPostCode, dto.getPostCode());
        if (baseMapper.exists(queryWrapper)) {
            throw BizException.validationError(ResultCode.CONFLICT, "岗位编码已存在");
        }
        SysPost entity = SysPost.INSTANCE.toEntity(dto);
        save(entity);
    }

    @Override
    public SysPostVO getByIdNew(Long id) {
        SysPost entity = getById(id);
        return SysPost.INSTANCE.toVo(entity);
    }
}

