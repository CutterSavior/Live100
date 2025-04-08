package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.SysDictMapper;
import com.lanjii.model.dto.SysDictDetailDto;
import com.lanjii.model.entity.SysDict;
import com.lanjii.model.entity.SysDictDetail;
import com.lanjii.service.ISysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典表 服务实现类
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    private final SysDictMapper sysDictMapper;
    private final SysDictDetailServiceImpl sysDictDetailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        SysDict sysDict = sysDictMapper.selectById(id);
        SysDictDetailDto filter = new SysDictDetailDto();
        filter.setDictCode(sysDict.getDictCode());
        List<Long> detailIds = sysDictDetailService.getListByFilter(filter).stream().map(SysDictDetail::getId).collect(Collectors.toList());
        sysDictDetailService.removeByIds(detailIds);
        sysDictMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNew(SysDict sysDict, SysDict originalData) {
        SysDictDetailDto filter = new SysDictDetailDto();
        filter.setDictCode(originalData.getDictCode());
        List<SysDictDetail> dictDetails = sysDictDetailService.getListByFilter(filter);
        List<SysDictDetail> newDictDetails = dictDetails.stream().peek(detail -> detail.setDictCode(sysDict.getDictCode())).collect(Collectors.toList());
        sysDictDetailService.updateBatchById(newDictDetails);
        updateById(sysDict);
    }
}
