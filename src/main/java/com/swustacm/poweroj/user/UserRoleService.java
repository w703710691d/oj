package com.swustacm.poweroj.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.user.entity.Role;
import com.swustacm.poweroj.user.entity.UserRole;
import com.swustacm.poweroj.user.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService
        extends ServiceImpl<UserRoleMapper, UserRole>
        implements IService<UserRole> {

    @Autowired
    UserRoleMapper userRoleMapper;

    public List<Role> getRolesByUid(Integer uid) {
        return userRoleMapper.getRolesByUserId(uid);
    }
}
