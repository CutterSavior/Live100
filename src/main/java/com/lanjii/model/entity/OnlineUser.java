package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.OnlineUserVo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;
import java.util.Date;

/**
 * 在线用户
 *
 * @author lizheng
 * @date 2025-04-09
 */
@Data
@Accessors(chain = true)
public class OnlineUser implements Serializable {

    public static final OnlineUserModelMapper INSTANCE = Mappers.getMapper(OnlineUserModelMapper.class);
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 最后访问时间
     */
    private Date lastActiveTime;
    /**
     * 位置
     */
    private String location;
    /**
     * 在线状态
     */
    private Integer onlineStatus;
    /**
     * 姓名
     */
    private String realName;
    /**
     * jwt生成的token
     */
    private String token;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户ID
     */
    private Long userid;

    @Mapper
    public interface OnlineUserModelMapper extends BaseModelMapper<OnlineUserVo, OnlineUser> {
        @Override
        OnlineUserVo modelToVo(OnlineUser model);

    }

}
