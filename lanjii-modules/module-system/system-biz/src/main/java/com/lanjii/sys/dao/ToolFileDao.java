package com.lanjii.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.sys.entity.ToolFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统工具-文件管理表(ToolFile)表数据库访问层
 *
 * @author lanjii
 */
@Mapper
public interface ToolFileDao extends BaseMapper<ToolFile> {

}
