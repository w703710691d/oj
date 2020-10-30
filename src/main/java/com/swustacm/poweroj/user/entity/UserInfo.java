package com.swustacm.poweroj.user.entity;

import lombok.Data;

import java.util.List;
@Data
public class UserInfo {

    /*
    角色列表
     */
    private List<Role> listRole;
    /*
    权限列表
     */
    private List<Permission> listPer;
    /*
    用户信息
     */
    private User user;
}
