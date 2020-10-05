package com.swustacm.poweroj.controller;

import com.swustacm.poweroj.biz.UserBiz;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.entity.User;
import com.swustacm.poweroj.params.LoginParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录操作类
 *
 * @author xingzi
 */
@Slf4j
@RestController
@RequestMapping("/dev/user")
public class UserController {
    @Autowired
    UserBiz userBiz;
    @Autowired
    Environment environment;


    /**
     * 用户登录
     *
     * @param loginParam 用户名和密码 name & password
     * @return
     */
    @PostMapping("/login")
    public CommonResult<User> login(@RequestBody @Validated LoginParam loginParam, HttpServletRequest request) {
        return userBiz.login(loginParam,request);
    }

    @GetMapping("/getUserInfo")
    public CommonResult<Object> getUserInfo() {
        return CommonResult.ok(SecurityUtils.getSubject().getPrincipal());
    }
}
