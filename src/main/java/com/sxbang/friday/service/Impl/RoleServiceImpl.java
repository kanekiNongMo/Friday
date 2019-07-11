package com.sxbang.friday.service.Impl;

import com.sxbang.friday.base.result.ResponseCode;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dao.RoleDao;
import com.sxbang.friday.dao.RolePermissionDao;
import com.sxbang.friday.dao.RoleUserDao;
import com.sxbang.friday.dto.RoleDto;
import com.sxbang.friday.model.SysRole;
import com.sxbang.friday.model.SysRoleUser;
import com.sxbang.friday.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
/**
 * @author kaneki
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleUserDao roleUserDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Results<SysRole> getAllRoles() {
        return Results.success(50, roleDao.getAllRoles());
    }

    @Override
    public Results<SysRole> getAllRoleByPage(Integer offset, Integer limit) {
        return Results.success(roleDao.countAllRoles(), roleDao.getAllRoleByPage(offset, limit));
    }

    @Override
    public Results<SysRole> saveRole(RoleDto roleDto) {
        // 保存角色信息
        roleDao.saveRole(roleDto);
        // 获取选中权限信息，并判断是否为空，有则设置其权限
        List<Integer> permissionIds = roleDto.getPermissionIds();
        if (!CollectionUtils.isEmpty(permissionIds)) {
            permissionIds.remove(0);
            rolePermissionDao.saveRolePermission(roleDto.getId(), permissionIds);
        }
        return Results.success();
    }

    @Override
    public SysRole getUserByRoleName(String name) {
        return roleDao.getUserByRoleName(name);
    }

    @Override
    public SysRole getRoleById(Integer id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public Results<SysRole> deleteRole(Integer id) {
        // 判断id中是否有关联用户，有则禁止删除
        List<SysRoleUser> datas = roleUserDao.listAllSysRoleUserByRoleId(id);
        if (datas.size() <= 0) {
            roleDao.deleteRole(id);
            return Results.success();
        }
        return Results.failure(ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getCode(), ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
    }

    @Override
    public Results<SysRole> getRoleByFuzzyRoleNameByPage(String name, Integer offset, Integer limit) {
        return Results.success(roleDao.getRoleByFuzzyRoleName(name), roleDao.getRoleByFuzzyRoleNameByPage(name, offset, limit));
    }

    @Override
    public Results<SysRole> batchDeleteRoleById(Integer[] ids) {
        // 判断ids中是否有关联用户，只要有一个id满足则禁止删除
        List<SysRoleUser> datas = roleUserDao.listAllSysRoleUserByRoleIds(ids);
        if (datas.size() <= 0) {
            roleDao.batchDeleteRoleById(ids);
            return Results.success();
        }
        return Results.failure(ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getCode(), ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
    }

    @Override
    public Results<SysRole> updateRole(RoleDto roleDto) {
        // 获取选中权限信息
        List<Integer> permissionIds = roleDto.getPermissionIds();
        // 删除原有的角色权限信息
        rolePermissionDao.deleteRolePermission(roleDto.getId());
        // 判断选中权限是否为空
        if (!CollectionUtils.isEmpty(permissionIds)) {
            // 删除根节点0
            permissionIds.remove(0);
            // 保存该角色设置的权限
            rolePermissionDao.saveRolePermission(roleDto.getId(), permissionIds);
        }
        // 修改角色信息
        int countData = roleDao.updateRole(roleDto);
        if (countData > 0) {
            return Results.success();
        } else {
            return Results.failure();
        }
    }
}
