package com.swustacm.poweroj.user;


import com.swustacm.poweroj.mapper.UserMapper;
import com.swustacm.poweroj.user.entity.LogicalEnum;
import com.swustacm.poweroj.user.entity.Permission;
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
public  class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
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
    @Override
    public boolean hasRole(List<String> strings, LogicalEnum str){
        Subject subject = SecurityUtils.getSubject();
        boolean[] list = subject.hasRoles(strings);
        if(str.equals(LogicalEnum.OR)){
            for (boolean flag: list) {
                if (flag)
                    return true;
            }
            return false;
        }
        if (str.equals(LogicalEnum.AND)) {

            for (boolean flag : list) {
                if (!flag)
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String nameCheck(String name) {
        return userMapper.nameCheck(name);
    }

    @Override
    public int getUserId(String name) {
        return userMapper.getUserId(name);
    }

    @Override
    public void addRoleById(int uid) {
        userMapper.addRoleById(uid);
    }

    @Override
    public void createUserExt(int uid) {
        userMapper.createUserExt(uid);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);

    }

    @Override
    public List<Permission> getRolePermission(int id) {
        return userMapper.getRolePermission(id);
    }

    @Override
    public String emailCheck(String email) {

        return userMapper.emailCheck(email);
    }


}
