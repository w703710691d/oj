package com.swustacm.poweroj.user.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class SignupParam {

    @Pattern(regexp = "^[0-9]\\d{5,15}$")
    @NotEmpty(message = "account 不能为空")
    public String name;//登录账号

    @NotEmpty(message =  "nick 不能为空")
    public String nick;

    @NotEmpty(message = "密码不能为空")
    public String password;

    @NotEmpty(message = "密码不能为空")
    public String rePassword;


    @Email
    @NotEmpty(message = "邮箱不能为空")
    public String email;



}
