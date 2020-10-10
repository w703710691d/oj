package com.swustacm.poweroj.user;

import com.swustacm.poweroj.mapper.UserMapper;
import com.swustacm.poweroj.user.entity.User;
import com.swustacm.poweroj.user.entity.Role;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public boolean hasRole(String admin) {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null && admin==null)
            return false;
        try{

            return subject.hasRole(admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
