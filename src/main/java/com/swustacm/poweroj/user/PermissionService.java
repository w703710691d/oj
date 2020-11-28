package com.swustacm.poweroj.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swustacm.poweroj.user.entity.Permission;
import com.swustacm.poweroj.user.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> implements IService<Permission> {
    @Autowired
    PermissionMapper permissionMapper;

    private static final HashMap<Integer, Set<Integer>> permissionTree = new HashMap<>();
    private static final HashMap<Integer, Permission> permissionName = new HashMap<>();

    @PostConstruct
    public void loadPermission() {
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<>());
        for (Permission permission : permissionList) {
            permissionName.put(permission.getId(), permission);
            Integer parentId = permission.getParentID();
            if (parentId == 0) {
                continue;
            }
            Set<Integer> subPermissionList = permissionTree.computeIfAbsent(parentId, k -> new HashSet<>());
            subPermissionList.add(permission.getId());
        }
    }

    public Set<String> getPermissionsStrByPermissionId(Integer pid) {
        Set<String> permissions = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(pid);
        while (!queue.isEmpty()) {
            pid = queue.poll();
            permissions.add(permissionName.get(pid).getName());
            Set<Integer> subPermissions = permissionTree.get(pid);
            if(subPermissions == null) {
                continue;
            }
            for (Integer subPid : subPermissions) {
                queue.offer(subPid);
            }
        }
        return permissions;
    }
}
