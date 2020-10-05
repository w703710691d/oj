package com.swustacm.poweroj.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.common.util.CollectionUtils;
import com.swustacm.poweroj.common.util.IPUtils;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.entity.User;
import com.swustacm.poweroj.params.LoginParam;
import com.swustacm.poweroj.service.UserService;
import jodd.util.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    CaptchaBiz captchaBiz;

    public CommonResult<User> login(LoginParam loginParam, HttpServletRequest request) {
        log.info("username:{},password:{}", loginParam.getName(), loginParam.getPassword());
        //验证码校验
        captchaBiz.verify(loginParam.getCode(),loginParam.getVerKey());
        User user = new User();

        if (CollectionUtils.exist(environment.getActiveProfiles(), "dev")) {
            user.setPassword("li112411");
            user.setName("7220190127");
            System.out.println("dev----");
        }

        user = userService.getOne(new QueryWrapper<User>().eq("name", loginParam.getName()));
        if (user == null) {
            return CommonResult.error("用户不存在");
        }
        if (! BCrypt.checkpw(loginParam.getPassword(),user.getPassword())) {
            return CommonResult.error("密码错误");
        }
        String ip = IPUtils.getIpAddr(request);
        user = userService.updateLogin(user,ip);
        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>(4);
        chaim.put("username", user.getName());
        String jwtToken = jwtUtil.encode(user.getName(), GlobalConstant.TOKEN_EXP, chaim);
        user.setToken(jwtToken);
        user.setPassword(null);
        return CommonResult.ok(user);
    }
}
