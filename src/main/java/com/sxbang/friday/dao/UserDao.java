package com.sxbang.friday.dao;

import com.sxbang.friday.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @author kaneki
 */
@Mapper
public interface UserDao {
    // 分页查询用户
    List<SysUser> getAllUserByPage(@Param("startPosition") Integer startPosition, @Param("limit") Integer limit);

    // 查询用户总数
    @Select("select count(*) from sys_user")
    Integer countAllUsers();

    // 添加用户
    int saveUser(SysUser sysUser);

    // 根据名称查询用户
    SysUser getUserByUserName(String username);

    // 根据电话查询用户
    @Select("select u.id, u.phone from sys_user u where u.phone = #{phone}")
    SysUser getUserByPhone(String phone);

    // 根据邮箱查询用户
    @Select("select u.id, u.email from sys_user u where u.email = #{email}")
    SysUser getUserByEmail(String email);

    // 根据id查询用户
    SysUser getUserById(Integer id);

    // 更新用户
    int updateUser(SysUser sysUser);

    // 删除用户
    @Delete("delete from sys_user where id = #{id}")
    int deleteUser(Integer id);

    // 批量用户
    int batchDeleteUserById(Integer[] ids);

    // 根据用户名模糊查询获取数量
    Integer getUserByFuzzyUserName(@Param("username") String username);

    // 根据用户名模糊查询用户信息
    List<SysUser> getUserByFuzzyUserNameByPage(@Param("username") String username,
                                               @Param("startPosition") Integer startPosition,
                                               @Param("limit") Integer limit);

    //修改密码
    @Update("update sys_user set password = #{password} where id = #{id}")
    int changePassword(@Param("id") Integer id, @Param("password") String password);
}
