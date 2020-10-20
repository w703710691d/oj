package com.swustacm.poweroj.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken
 * @author xingzi
 */
public class JwtToken implements AuthenticationToken {
    private final String jwt;

    public String getJwt() {
        return jwt;
    }

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }
    @Override
    public Object getPrincipal() {
        return jwt;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }
}
