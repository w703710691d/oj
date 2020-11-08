package com.swustacm.poweroj.user.entity;


import lombok.Data;

import java.util.List;

@Data
public class Role {
    /**
    角色id
     */
    private int id;
    /**
     * 角色名称
     */
    private String name;
    /*
    权限
     */
    private List<Permission> listPer;
    /*
    用户信息
     */
    public User user;
}
