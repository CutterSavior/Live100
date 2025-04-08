package com.lanjii.model.dto;

import lombok.Data;

/**
 * 修改密码
 *
 * @author LiZheng
 * @date 2024-11-07
 */
@Data
public class ChangePwdDto {

    private String oldPwd;

    private String newPwd;

}
