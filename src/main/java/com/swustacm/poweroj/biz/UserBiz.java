package com.swustacm.poweroj.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.common.util.ListUtils;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.entity.User;
import com.swustacm.poweroj.service.UserService;
import jodd.util.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关操作处理
 * @author xingzi
 */
@Component
@Slf4j
public class UserBiz {
    @Autowired
    UserService userService;
    @Autowired
    Environment environment;

    public CommonResult<User> login(User user) {
        log.info("username:{},password:{}", user.getName(), user.getPassword());
        if (ListUtils.exist(environment.getActiveProfiles(), "dev")) {
            user.setPassword("li112411");
            user.setName("7220190127");
        }
        User userInfo = userService.getOne(new QueryWrapper<User>().eq("name", user.getName()));
        if (userInfo == null) {
            return CommonResult.error("用户不存在");
        }
        if (! BCrypt.checkpw(user.getPassword(),userInfo.getPassword())) {
            return CommonResult.error("密码错误");
        }
        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>(4);
        chaim.put("username", user.getName());
        String jwtToken = jwtUtil.encode(user.getName(), GlobalConstant.TOKEN_EXP, chaim);
        userInfo.setToken(jwtToken);
        userInfo.setPassword(null);
        return CommonResult.ok();
    }
}
