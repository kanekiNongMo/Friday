package com.sxbang.friday.dao;

import com.sxbang.friday.model.SysRoleUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * @author kaneki
 */
@Mapper
public interface RoleUserDao {
    // 添加用户角色信息
    @Insert("insert into sys_role_user(userId, roleId) values(#{userId}, #{roleId})")
    int saveRoleUser(SysRoleUser sysRoleUser);

    // 根据用户id查询用户角色信息
    @Select("select userId, roleId from sys_role_user where userId = #{userId}")
    SysRoleUser getRoleUserByUserId(Integer userId);

    // 根据用户id修改用户角色信息
    int updateSysRoleUser(SysRoleUser sysRoleUser);

    // 根据用户id删除其用户角色信息
    @Delete("delete from sys_role_user where userId = #{userId}")
    int deleteRoleUserByUserId(Integer userId);

    // 根据用户id批量删除用户角色信息
    int batchDeleteRoleUserByUserId(Integer[] userIds);

    // 根据角色id查询用户角色信息
    @Select("select userId, roleId from sys_role_user t where t.roleId = #{roleId}")
    List<SysRoleUser> listAllSysRoleUserByRoleId(Integer id);

    // 根据用户id查询用户角色信息
    List<SysRoleUser> listAllSysRoleUserByRoleIds(Integer[] roleIds);
}
