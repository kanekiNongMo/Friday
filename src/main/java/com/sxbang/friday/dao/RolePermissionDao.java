package com.sxbang.friday.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author kaneki
 */
@Mapper
public interface RolePermissionDao {

    // 新增角色权限信息
    int saveRolePermission(@Param("roleId") Integer id, @Param("permissionIds") List<Integer> permissionIds);

    // 根据角色id删除角色权限信息
    @Delete("delete from sys_role_permission where roleId = #{roleId}")
    int deleteRolePermission(Integer id);
}
