package com.swustacm.poweroj.config.shiro;
import com.google.gson.Gson;
import com.swustacm.poweroj.common.CommonResult;
import com.swustacm.poweroj.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import static com.swustacm.poweroj.config.shiro.JwtRealm.TOKEN_DEV;

/**
 * Jwt过滤器
 * @author xingzi
 */
@Slf4j
@Component
public class JwtFilter extends AccessControlFilter {
    @Autowired
    Environment environment;
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt;
        jwt = request.getHeader("Authorization");
//        if (CollectionUtils.exist(environment.getActiveProfiles(), "dev")) {
//            jwt = TOKEN_DEV;
//        }else {
//            jwt = request.getHeader("Authorization");
//        }
        JwtToken jwtToken = new JwtToken(jwt);
        /*+
         * 下面就是固定写法
         **/
        try {
            // 委托 realm 进行登录认证
            //所以这个地方最终还是调用JwtRealm进行的认证
            getSubject(servletRequest, servletResponse).login(jwtToken);
            //也就是subject.login(token)
        } catch (Exception e) {
            e.printStackTrace();
            onLoginFail(servletResponse);
            //调用下面的方法向客户端返回错误信息
            return false;
        }

        return true;
    }
    /**
     *  登录失败时默认返回 401 状态码
     */
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json;charset=utf-8");
        String json = new Gson().toJson(CommonResult.error(HttpServletResponse.SC_UNAUTHORIZED, "invalid token"));
        httpResponse.getWriter().write(json);

    }
}
