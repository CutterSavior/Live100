package com.lanjii.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lanjii.util.AuthUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 字段的默认填充处理
 *
 * @author lizheng
 * @date 2024-09-04
 */
@Component
public class FillDefaultMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        this.setFieldValByName("createdTime", now, metaObject);
        this.setFieldValByName("createdBy", AuthUtils.getCurrentUser().getUsername(), metaObject);
        this.setFieldValByName("updatedTime", now, metaObject);
        this.setFieldValByName("updatedBy", AuthUtils.getCurrentUser().getUsername(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedTime", new Date(), metaObject);
        this.setFieldValByName("updatedBy", AuthUtils.getCurrentUser().getUsername(), metaObject);
    }
}
