package com.swustacm.poweroj.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swustacm.poweroj.common.util.CollectionUtils;
import com.swustacm.poweroj.user.entity.User;
import com.swustacm.poweroj.user.entity.Role;
import com.swustacm.poweroj.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证授权
 * @author xingzi
 */
@Component
@Slf4j
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    Environment environment;
    @Autowired
    UserService userService;

    public static final String TOKEN_DEV = "12345";

    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //获取当前登录用户
        String name;
        if (CollectionUtils.exist(environment.getActiveProfiles(), "dev")) {
            name = "7220190127";
        } else {
            name = jwtUtil.getUserName();
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("name", name));

        List<Role> roleList = userService.getUserRole(user.getUid());
        if (roleList != null && roleList.size() > 0) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            Set<String> roles = new HashSet<>();
            Set<String> pers = new HashSet<>();

            for (Role r : roleList) {
                roles.add(r.getName());
                log.info("role: " + r.getName());
                List<String> perList = userService.getUserPermission(r.getId());
                for (String s : perList) {
                    pers.add(s);
                    log.info("permission: " + s);
                }
            }
            info.setRoles(roles);
            info.setStringPermissions(pers);
            return info;
        }
        return null;
//        //根据用户查询对应权限和角色信息
//        Set<String> permsSet = new HashSet<String>(){{add("get");}};
//        Set<String> roleSet = new HashSet<String>(){{add("get");add("post");}};
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions(permsSet);
//        info.setRoles(roleSet);
//        return info;

    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String jwt;
        if (CollectionUtils.exist(environment.getActiveProfiles(), "dev")) {
           jwt = TOKEN_DEV;
        }
        else {
            jwt = (String) token.getPrincipal();
            if (jwt == null) {
                throw new NullPointerException("jwtToken 不允许为空");
            }
            //判断
            JwtUtil jwtUtil = new JwtUtil();
            if (!jwtUtil.isVerify(jwt)) {
                throw new UnknownAccountException();
            }
            //判断数据库中username是否存在
            String username = (String) jwtUtil.decode(jwt).get("username");
            log.info("在使用token登录" + username);
        }
        log.info("在使用token登录" + jwt);

        return new SimpleAuthenticationInfo(jwt, jwt, "JwtRealm");
    }
    /**
     * 清除所有用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }

        }
    }
}
