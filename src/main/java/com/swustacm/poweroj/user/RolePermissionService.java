package com.swustacm.poweroj.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.user.entity.RolePermission;
import com.swustacm.poweroj.user.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RolePermissionService
        extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements IService<RolePermission> {

    final static HashMap<Integer, Set<String>> rolePermissions = new HashMap<>();

    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Autowired
    PermissionService permissionService;

    @PostConstruct
    public void loadRolePermissions() {
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<>());
        for(RolePermission rolePermission : rolePermissionList) {
            Integer rid = rolePermission.getRid();
            Set<String> permissionList = rolePermissions.computeIfAbsent(rid, k -> new HashSet<>());
            permissionList.addAll(permissionService.getPermissionsStrByPermissionId(rolePermission.getPid()));
        }
    }

    public Set<String> getPermissionsStrByRoleId(Integer rid) {
        return rolePermissions.get(rid);
    }
}
