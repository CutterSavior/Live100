package com.lanjii.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.sys.base.AdminBaseMapper;
import com.lanjii.system.api.dto.SysDeptDTO;
import com.lanjii.system.api.vo.SysDeptVO;
import com.lanjii.framework.mp.base.TenantBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 系统部门表(SysDept)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDept extends TenantBaseEntity {

    /**
     * 部门ID
     */
    private Long id;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表(逗号分隔)
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer sortOrder;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    @Mapper
    public interface SysDeptMapper extends AdminBaseMapper<SysDept, SysDeptVO, SysDeptDTO> {

        @Mapping(target = "children", ignore = true)
        SysDeptVO toVo(SysDept entity);

        @Mapping(target = "ancestors", ignore = true)
        SysDept toEntity(SysDeptDTO dto);

    }

    public static final SysDept.SysDeptMapper INSTANCE = Mappers.getMapper(SysDept.SysDeptMapper.class);
}
