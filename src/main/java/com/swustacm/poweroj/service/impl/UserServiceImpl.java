package com.swustacm.poweroj.service.impl;

import com.swustacm.poweroj.entity.User;
import com.swustacm.poweroj.mapper.UserMapper;
import com.swustacm.poweroj.params.Role;
import com.swustacm.poweroj.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xingzi
 * @since 2020-09-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User updateLogin(User user,String ip) {
        user.setLoginTime((int)(System.currentTimeMillis()/1000));
        if(ip != null)
            user.setLoginIP(ip);
        this.updateById(user);
        user = this.getById(user.getUid());
        return user;
    }

    @Override
    public List<Role> getUserRole(int uid){
        return userMapper.getUserRole(uid);
    }

    @Override
    public List<String> getUserPermission(int id) {
        return userMapper.getUserPermission(id);
    }
}
