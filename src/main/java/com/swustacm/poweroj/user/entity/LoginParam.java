package com.swustacm.poweroj.user.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 登录参数
 * @author zcy
 */
@Data
public class LoginParam {

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String name;
    /**
     * 用户密码
     */
    @NotEmpty(message = "用户密码不能为空")
    private String password;
    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空")
    private String code;
    /**
     * 验证码token
     */
    @NotEmpty
    private String verKey;

}