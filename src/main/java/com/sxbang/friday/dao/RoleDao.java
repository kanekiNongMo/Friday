package com.sxbang.friday.dao;

import com.sxbang.friday.model.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @author kaneki
 */
@Mapper
public interface RoleDao {

    // 查询所有角色信息
    @Select("select * from sys_role")
    List<SysRole> getAllRoles();

    // 分页查询角色信息
    List<SysRole> getAllRoleByPage(@Param("startPosition") Integer startPosition, @Param("limit") Integer limit);

    // 查询角色总数
    @Select("select count(*) from sys_role")
    Integer countAllRoles();

    // 根据角色名查询角色信息
    @Select("select r.id, r.name from sys_role r where r.name = #{name}")
    SysRole getUserByRoleName(String name);

    // 新增角色信息
    int saveRole(SysRole sysRole);

    // 根据id查询角色信息
    SysRole getRoleById(Integer id);

    // 根据id修改角色信息
    int updateRole(SysRole sysRole);

    // 根据角色名称模糊查询角色数量
    Integer getRoleByFuzzyRoleName(@Param("name") String name);

    // 根据角色名称模糊查询角色信息
    List<SysRole> getRoleByFuzzyRoleNameByPage(@Param("name") String name,
                                               @Param("startPosition") Integer startPosition,
                                               @Param("limit") Integer limit);

    // 根据id删除角色信息
    @Delete("delete from sys_role where id = #{id}")
    int deleteRole(Integer id);

    // 根据id批量删除角色信息
    int batchDeleteRoleById(Integer[] ids);

}
