
package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysMenuVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 *
 * @author lizheng
 * @date 2024-10-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysMenu extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 父菜单
     */
    private Long parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 外部链接
     */
    private String extLink;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 菜单类型 （1 - 菜单 、2 - 按钮、3 - 外链）
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;


    @Mapper
    public interface SysMenuModelMapper extends BaseModelMapper<SysMenuVo, SysMenu> {
        @Override
        SysMenuVo modelToVo(SysMenu model);

    }

    public static final SysMenuModelMapper INSTANCE = Mappers.getMapper(SysMenuModelMapper.class);

}
