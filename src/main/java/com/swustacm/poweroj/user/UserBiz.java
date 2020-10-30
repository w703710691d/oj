package com.swustacm.poweroj.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swustacm.poweroj.captcha.CaptchaBiz;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.GlobalConstant;
import com.swustacm.poweroj.common.email.MailEnum;
import com.swustacm.poweroj.common.email.MailService;
import com.swustacm.poweroj.common.util.CollectionUtils;
import com.swustacm.poweroj.common.util.IPUtils;
import com.swustacm.poweroj.config.shiro.JwtUtil;
import com.swustacm.poweroj.user.entity.*;
import jodd.util.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    MailService mailService;
    @Autowired
    JwtUtil jwtUtil;

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
        if(user.getStatus() == 0){
            return CommonResult.error("用户账号未激活,请前往邮箱激活");
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

    public CommonResult signup(SignupParam signupParam) {
        if(!emailCheck(signupParam.getEmail()))
            return CommonResult.error("邮箱以存在");
        if(!nameCheck(signupParam.getName()))
            return CommonResult.error("用户名已存在");
        if(!signupParam.getPassword().equals(signupParam.getRePassword()))
            return CommonResult.error("2次密码不一致");

        int ctime = (int)(System.currentTimeMillis()/1000);
        User newUser = new User();
        newUser.setName(signupParam.getName()).setNick(signupParam.getNick()).setEmail(signupParam.getEmail()).setRegEmail(signupParam.getEmail());
        newUser.setPassword(BCrypt.hashpw(signupParam.getPassword(),BCrypt.gensalt()));
        newUser.setCtime(ctime).setMtime(ctime);
        //用于验证邮箱
        String verifyEmailToken = UUID.randomUUID().toString();
        newUser.setToken(verifyEmailToken);
        //待加入邮箱测试
        newUser.setEmailVerified(false);
        newUser.setStatus(0);
        if(userService.save(newUser)){
            int uid = userService.getUserId(newUser.getName());
            userService.addRoleById(uid);
            userService.createUserExt(uid);

        }
        Map<String,Object> map = new HashMap<>();
        map.put("emailToken",newUser.getToken());
        map.put("name",newUser.getName());
        mailService.sendMailForActivate(newUser.getEmail(), MailEnum.CODE_MAIL,map);
        System.out.println("开启异步。。。");



        return CommonResult.ok();
    }

    private boolean nameCheck(String name) {
        String nick = userService.nameCheck(name);
        if(nick != null){
            return false;
        }
        return true;
    }

    private boolean emailCheck(String email) {
        String nick = userService.emailCheck(email);
        if (nick != null)
            return false;

        return true;
    }

    public CommonResult emailVerify(String name, String token) {

        User user = userService.getUserByName(name);
        if(!user.getToken().equals(token)){
            return CommonResult.error("邮箱验证失败");
        }
        user.setEmailVerified(true).setStatus(1);
        if(userService.updateById(user)){
            return CommonResult.ok();
        }


        return null;
    }

    public CommonResult<UserInfo> getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(jwtUtil.getUserInfo());

        List<Role> roleList = userService.getUserRole(userInfo.getUser().getUid());
        userInfo.setListRole(roleList);
        for(Role role :roleList){
            List<Permission> listPer = userService.getRolePermission(role.getId());
            userInfo.setListPer(listPer);
        }
        return CommonResult.ok(userInfo);

    }
}
