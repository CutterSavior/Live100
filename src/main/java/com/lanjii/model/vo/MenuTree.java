package com.lanjii.model.vo;

import com.lanjii.model.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 作前端属性结构展示
 *
 * @author lizheng
 * @date 2024-09-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuTree  extends SysMenu {

    /**
     * 子元素
     */
    private List<MenuTree> children;

}
