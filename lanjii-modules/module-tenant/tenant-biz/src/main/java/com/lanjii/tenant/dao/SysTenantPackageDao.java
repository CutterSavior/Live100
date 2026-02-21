package com.lanjii.tenant.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.tenant.entity.SysTenantPackage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户套餐数据访问层
 *
 * @author lanjii
 */
@Mapper
public interface SysTenantPackageDao extends BaseMapper<SysTenantPackage> {

}
