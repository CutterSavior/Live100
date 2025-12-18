package com.lanjii.core.base;

import com.lanjii.biz.admin.system.model.entity.SysDept;
import com.lanjii.biz.admin.system.service.SysConfigService;
import com.lanjii.biz.admin.system.service.SysDeptService;
import com.lanjii.biz.admin.system.service.SysDictDataService;
import com.lanjii.common.util.SpringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapstruct 基础接口
 *
 * @author lizheng
 */
public interface BaseEntityMapper<E, V, D> {

    V toVo(E entity);

    default List<V> toVo(List<E> model) {
        if (CollectionUtils.isEmpty(model)) {
            return Collections.emptyList();
        }
        return model.stream().map(this::toVo).collect(Collectors.toList());
    }

    E toEntity(D dto);

    @Named("dictValueToLabel")
    default String dictValueToLabel(Integer dictValue, String dictType) {
        if (dictValue == null || dictType == null) {
            return null;
        }
        return SpringUtils.getBean(SysDictDataService.class).getLabel(dictType, dictValue);
    }

    @Named("getConfig")
    default String getConfig(String configKey) {
        return SpringUtils.getBean(SysConfigService.class).getConfigValue(configKey);
    }

    @Named("deptIdToName")
    default String deptIdToName(Long deptId) {
        if (deptId == null) {
            return null;
        }
        SysDept dept = SpringUtils.getBean(SysDeptService.class).getById(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getDeptName();
    }
}
