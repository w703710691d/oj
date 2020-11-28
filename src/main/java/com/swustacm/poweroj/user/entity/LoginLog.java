package com.swustacm.poweroj.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录日志
 * @author 张成云
 * @since 2020-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("uid")
    private Integer uid;
    /**
     * 用户名
     */
    @TableField("name")
    private String name;

    @TableField("ip")
    private String ip;

    @TableField("ctime")
    private Integer ctime;

    @TableField("success")
    private Boolean success;
    /**
     * 登陆时间
     */
    @TableField(exist = false)
    private String time;


}
