package com.swustacm.poweroj.service;

import com.swustacm.poweroj.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.params.Role;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xingzi
 * @since 2020-09-20
 */
public interface UserService extends IService<User> {

    User updateLogin(User user,String ip);

    List<Role> getUserRole(int uid);

    List<String> getUserPermission(int id);
}
