package com.lanjii.core.base;

import com.lanjii.core.annotation.SortField;
import com.lanjii.core.enums.SortOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDTO {

    @SortField(order = SortOrder.DESC)
    private LocalDateTime createTime;

    @SortField(priority = 10, order = SortOrder.DESC)
    private LocalDateTime updateTime;

}
