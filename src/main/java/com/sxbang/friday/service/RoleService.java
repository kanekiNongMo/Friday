package com.sxbang.friday.service;

import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dto.RoleDto;
import com.sxbang.friday.model.SysRole;
/**
 * @author kaneki
 */
public interface RoleService {
    // 获取所有角色信息
    Results<SysRole> getAllRoles();

    // 分页获取角色信息
    Results<SysRole> getAllRoleByPage(Integer offset, Integer limit);

    // 添加角色信息
    Results<SysRole> saveRole(RoleDto roleDto);

    // 根据角色名称获取角色信息
    SysRole getUserByRoleName(String name);

    // 根据id获取角色信息
    SysRole getRoleById(Integer id);

    // 删除角色信息
    Results<SysRole> deleteRole(Integer id);

    // 根据角色名称模糊查询
    Results<SysRole> getRoleByFuzzyRoleNameByPage(String name, Integer offset, Integer limit);

    // 批量删除角色信息
    Results<SysRole> batchDeleteRoleById(Integer[] ids);

    // 修改角色信息
    Results<SysRole> updateRole(RoleDto roleDto);
}
