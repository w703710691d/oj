package com.swustacm.poweroj.config.shiro;

import com.swustacm.poweroj.user.RolePermissionService;
import com.swustacm.poweroj.user.UserRoleService;
import com.swustacm.poweroj.user.UserService;
import com.swustacm.poweroj.user.entity.Role;
import com.swustacm.poweroj.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证授权
 *
 * @author xingzi
 */
@Component
@Slf4j
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RolePermissionService rolePermissionService;

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
        User user = (User) principals.getPrimaryPrincipal();
        List<Role> roleList = userRoleService.getRolesByUid(user.getUid());
        Set<String> rolesStr = new HashSet<>();
        Set<String> permissionsStr = new HashSet<>();

        for (Role role : roleList) {
            rolesStr.add(role.getName());
            permissionsStr.addAll(rolePermissionService.getPermissionsStrByRoleId(role.getId()));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(rolesStr);
        info.setStringPermissions(permissionsStr);
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwt = (String) token.getPrincipal();
        if (null == jwt) {
            throw new NullPointerException("jwtToken 不允许为空");
        }
        JwtUtil jwtUtil = new JwtUtil();
        if (!jwtUtil.isVerify(jwt)) {
            throw new UnknownAccountException();
        }
        Integer uid = (Integer) jwtUtil.decode(jwt).get("uid");
        User user = userService.getById(uid);
        return new SimpleAuthenticationInfo(user, jwt, "JwtRealm");
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
