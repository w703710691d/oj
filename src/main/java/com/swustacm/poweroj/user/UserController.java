package com.swustacm.poweroj.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.params.PageParam;
import com.swustacm.poweroj.user.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

/**
 * 用户登录操作类
 *
 * @author xingzi
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
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
        return userBiz.login(loginParam, request);
    }

    @GetMapping("/getGuestToken")
    public CommonResult<User> getGuestToken() {
        return userBiz.getGuestToken();
    }


    @GetMapping("/getUserRolesAndPermissions")
    public CommonResult<Map<String, Set<String>>> getUserRoles(@RequestParam(value = "uid", required = false) Integer uid) {
        return userBiz.getUserRoles(uid);
    }

    @GetMapping("/getUserInfo")
    public CommonResult<UserInfo> getUserInfo() {
        return userBiz.getUserInfo();
    }

    /**
     * 用户注册
     *
     * @param signupParam
     * @return
     */

    @PostMapping("/signup")
    public CommonResult signup(@RequestBody @Validated SignupParam signupParam) {
        return userBiz.signup(signupParam);
    }

    /**
     * email验证链接
     */
    @GetMapping("/email/verify")
    public CommonResult emailVerify(@RequestParam @NotNull String name, @RequestParam @NotNull String token) {
        return userBiz.emailVerify(name, token);
    }
    /*
     *  找回password
     */

    /**
     * 获得用户信息profile（解决题数）
     */
    @GetMapping("/profile")
    public CommonResult<UserProblemInfo> getUserProfile() {
        return userBiz.getUserProfile();
    }

    /**
     * RankList 排名列表
     */
    @PostMapping("/rankList")
    public CommonResult<IPage<User>> getRankList(@RequestBody @Validated PageParam page) {
        return userBiz.getRankList(page);
    }

    /**
     * 登录日志
     */
    @PostMapping("/loginlog")
    public CommonResult<IPage<LoginLog>> getLoginLog(@RequestBody @Validated PageParam page) {
        return userBiz.getLoginLog(page);
    }

}
