package com.lanjii.tenant.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.tenant.entity.SysTenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户数据访问层
 *
 * @author lanjii
 */
@Mapper
public interface SysTenantDao extends BaseMapper<SysTenant> {

}
