package com.sxbang.friday.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dao.PermissionDao;
import com.sxbang.friday.model.SysPermission;
import com.sxbang.friday.service.PermissionService;
import com.sxbang.friday.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @author kaneki
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Results<JSONArray> listAllPermission(Integer id) {
        // 获取权限信息
        List<SysPermission> datas = permissionDao.findAllPermission();
        JSONArray array = new JSONArray();
        // 将数据递归添加到array中形成菜单树
        if (null == id) {
            TreeUtils.setPermissionsTree(0, datas, array);
        } else {
            // 构建的菜单树不包括自己和子节点
            TreeUtils.setPermissionsTreeNoSonById(0, id, datas, array);
        }
        return Results.success(array);
    }

    @Override
    public Results<SysPermission> listByRoleId(Integer roleId) {
        List<SysPermission> datas = permissionDao.listByRoleId(roleId);
        return Results.success(datas.size(), datas);
    }

    @Override
    public Results<SysPermission> getMenuAll() {
        return Results.success(0, permissionDao.findAllPermission());
    }

    @Override
    public Results<SysPermission> savePermission(SysPermission sysPermission) {
        return permissionDao.savePermission(sysPermission) > 0 ? Results.success() : Results.failure();
    }

    @Override
    public SysPermission getPermissionById(Integer id) {
        return permissionDao.getPermissionById(id);
    }

    @Override
    public Results<SysPermission> updatePermission(SysPermission sysPermission) {
        return permissionDao.updatePermission(sysPermission) > 0 ? Results.success() : Results.failure();
    }

    @Override
    public Results deletePermission(Integer id) {
        permissionDao.deletePermission(id);
        permissionDao.deleteSonPermission(id);
        return Results.success();
    }

    @Override
    public Results getMenu(Integer userId) {
        List<SysPermission> permissionList = permissionDao.listByUserId(userId);
        permissionList = permissionList.stream().filter(p -> p.getType().equals(1)).collect(Collectors.toList());
        JSONArray array = new JSONArray();
        TreeUtils.setPermissionsTree(0, permissionList, array);
        return Results.success(array);
    }
}
