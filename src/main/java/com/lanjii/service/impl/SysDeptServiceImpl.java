
package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.SysDeptMapper;
import com.lanjii.model.entity.SysDept;
import com.lanjii.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 部门 服务实现类
 *
 * @author lizheng
 * @date 2024-10-29
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

}
