package com.lanjii.biz.admin.system.service.impl;

import com.lanjii.biz.admin.system.dao.SysDeptDao;
import com.lanjii.biz.admin.system.model.dto.SysDeptDTO;
import com.lanjii.biz.admin.system.model.vo.SysDeptVO;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.biz.admin.system.model.entity.SysDept;
import com.lanjii.common.exception.BizException;
import com.lanjii.biz.admin.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统部门表(SysDept)表服务实现类
 *
 * @author lanjii
 */
@Service("sysDeptService")
@RequiredArgsConstructor
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptDao, SysDept> implements SysDeptService {

    @Override
    public void updateByIdNew(Long id, SysDeptDTO dto) {
        SysDept originalDept = getById(id);
        if (originalDept == null) {
           throw BizException.validationError(ResultCode.NOT_FOUND, "部门不存在");
        }

        SysDept entity = SysDept.INSTANCE.toEntity(dto);
        entity.setId(id);

        if (entity.getParentId() != null && !entity.getParentId().equals(originalDept.getParentId())) {
            entity.setAncestors(buildAncestors(entity.getParentId()));
            updateDescendantsAncestors(id);
        }

        updateById(entity);
    }
    
    @Override
    public void saveNew(SysDeptDTO dto) {
        SysDept entity = SysDept.INSTANCE.toEntity(dto);
        entity.setAncestors(buildAncestors(entity.getParentId()));
        save(entity);
    }

    @Override
    public SysDeptVO getByIdNew(Long id) {
        SysDept entity = getById(id);
        return SysDept.INSTANCE.toVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeptWithChildren(Long id) {
        List<SysDept> descendants = baseMapper.selectDescendantsByDeptId(id);

        if (!descendants.isEmpty()) {
            List<Long> descendantIds = descendants.stream()
                    .map(SysDept::getId)
                    .toList();
            removeBatchByIds(descendantIds);
        }

        removeById(id);
    }

    /**
     * 构建ancestors字符串
     *
     * @param parentId 父级部门ID
     * @return ancestors字符串
     */
    private String buildAncestors(Long parentId) {
        if (parentId == null || parentId == 0) {
            return "0";
        }
        SysDept parent = getById(parentId);
        if (parent == null || parent.getAncestors() == null) {
            return "0," + parentId;
        }
        return parent.getAncestors() + "," + parentId;
    }

    /**
     * 更新所有子孙节点
     *
     * @param deptId 部门ID
     */
    private void updateDescendantsAncestors(Long deptId) {
        List<SysDept> children = baseMapper.selectChildrenByParentId(deptId);
        for (SysDept child : children) {
            child.setAncestors(buildAncestors(child.getParentId()));
            updateById(child);
            updateDescendantsAncestors(child.getId());
        }
    }
}

