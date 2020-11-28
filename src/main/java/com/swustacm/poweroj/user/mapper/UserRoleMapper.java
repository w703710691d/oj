package com.swustacm.poweroj.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swustacm.poweroj.user.entity.Role;
import com.swustacm.poweroj.user.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRoleMapper extends BaseMapper<UserRole> {
    List<Role> getRolesByUserId(
            @Param(value = "uid") Integer uid);
}
