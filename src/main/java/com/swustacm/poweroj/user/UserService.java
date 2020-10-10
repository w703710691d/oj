package com.swustacm.poweroj.user;

import com.swustacm.poweroj.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swustacm.poweroj.user.entity.Role;

import java.util.List;

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

    boolean hasRole(String admin);
}
