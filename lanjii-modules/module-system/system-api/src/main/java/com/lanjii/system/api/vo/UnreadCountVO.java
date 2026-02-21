package com.lanjii.system.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 未读数统计 VO
 *
 * @author lanjii
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnreadCountVO {

    /**
     * 未读总数
     */
    private Long unreadCount;


}
