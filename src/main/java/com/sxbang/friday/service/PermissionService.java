package com.sxbang.friday.service;

import com.alibaba.fastjson.JSONArray;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dto.SysPermissionDto;
import com.sxbang.friday.model.SysPermission;
/**
 * @author kaneki
 */
public interface PermissionService {
    // 获取所有权限数据并转为菜单树，根据是否有id构建不同的菜单树，当存在id时构建的菜单树不包括自己和子节点
    Results<JSONArray> listAllPermission(Integer id);

    // 根据角色id获取其权限数据
    Results<SysPermission> listByRoleId(Integer roleId);

    // 获取所有权限信息
    Results<SysPermission> getMenuAll();

    // 新增菜单信息
    Results<SysPermission> savePermission(SysPermission sysPermission);

    // 根据id获取菜单信息
    SysPermission getPermissionById(Integer id);

    // 修改权限信息
    Results<SysPermission> updatePermission(SysPermission sysPermission);

    // 删除菜单
    Results deletePermission(Integer id);

    // 根据用户id获取菜单
    Results getMenu(Integer userId);
}
