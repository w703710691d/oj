package com.swustacm.poweroj.service;

import com.swustacm.poweroj.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
