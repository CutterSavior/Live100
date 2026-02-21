package com.lanjii.common.base;

import com.lanjii.common.annotation.SortField;
import com.lanjii.common.enums.SortOrder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO基础类
 *
 * @author lanjii
 */
@Data
public class BaseDTO {

    @SortField(order = SortOrder.DESC)
    private LocalDateTime createTime;

    @SortField(priority = 10, order = SortOrder.DESC)
    private LocalDateTime updateTime;

}
