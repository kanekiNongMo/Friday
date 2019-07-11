package com.sxbang.friday.dao;
/**
 * @author kaneki
 */
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.model.SysPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionDao {

    // 查询所有权限信息
    List<SysPermission> findAllPermission();

    // 根据角色id获取查询权限信息
    List<SysPermission> listByRoleId(Integer roleId);

    // 新增权限
    int savePermission(SysPermission sysPermission);

    // 根据id查询权限信息
    SysPermission getPermissionById(Integer id);

    // 修改权限信息
    int updatePermission(SysPermission sysPermission);

    // 根据id删除菜单
    @Delete("delete from sys_permission where id = #{id}")
    int deletePermission(Integer id);

    // 根据父id删除菜单
    @Delete("delete from sys_permission where parentId= #{parentId}")
    int deleteSonPermission(Integer pid);

    // 通过用户id查询权限
    List<SysPermission> listByUserId(Integer userId);
}
