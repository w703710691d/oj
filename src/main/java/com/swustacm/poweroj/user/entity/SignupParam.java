package com.swustacm.poweroj.user.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class SignupParam {
    /**
     * 登录账号，使用学号作为登录账号
     */
    @Pattern(regexp = "^[0-9]\\d{5,15}$")
    @NotEmpty(message = "account 不能为空")
    public String name;//登录账号
    /**
     * 用户姓名
     */
    @NotEmpty(message =  "nick 不能为空")
    public String nick;

    @NotEmpty(message = "密码不能为空")
    public String password;
    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空")
    public String rePassword;
    /**
     * 邮箱号
     */
    @Email
    @NotEmpty(message = "邮箱不能为空")
    public String email;



}
