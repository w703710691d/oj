package com.swustacm.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swustacm.oj.common.CommonResult;
import com.swustacm.oj.common.util.ListUtils;
import com.swustacm.oj.config.shiro.JwtUtil;
import com.swustacm.oj.entity.User;
import com.swustacm.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *  用户登录操作类
 * @author xingzi
 */
@Slf4j
@RestController
@RequestMapping("/dev/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    Environment environment;
    /**
     * 用户登录
     * @param user 用户名和密码 name & password
     * @return
     */
    @PostMapping("/login")
    public CommonResult<User> login(@RequestBody @Validated  User user){
        log.info("username:{},password:{}",user.getName(),user.getPassword());
        if(ListUtils.exist(environment.getActiveProfiles(),"dev")) {
            user.setPassword("$2a$10$AMxkUgVLhm8JD.esSr9vi./Inwy9m8wpa/9eKcZlT4lslVALcha/C");
            user.setName("7220190127");
        }
       User userInfo = userService.getOne(new QueryWrapper<User>().eq("name",user.getName()));
        if(userInfo==null){
            return CommonResult.error("用户不存在");
        }
        if(!user.getPassword().equals(userInfo.getPassword())){
            return CommonResult.error("密码错误");
        }
        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>(4);
        chaim.put("username", user.getName());
        String jwtToken = jwtUtil.encode(user.getName(), 5 * 60 * 1000, chaim);
        userInfo.setToken(jwtToken);
        userInfo.setPassword(null);
        return CommonResult.ok(userInfo);
    }

    @GetMapping("/getUserInfo")
    public CommonResult<Object> getUserInfo(){
        return CommonResult.ok(  SecurityUtils.getSubject().getPrincipal());
    }
}
