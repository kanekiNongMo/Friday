package com.sxbang.friday.service;


import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.model.SysUser;
/**
 * @author kaneki
 */
public interface UserService {
    // 分页获取用户信息
    Results<SysUser> getAllUserByPage(Integer offset, Integer limit);

    // 保存用户信息
    Results<SysUser> saveUser(SysUser sysUser, Integer roleId);

    // 根据用户名称获取用户信息
    SysUser getUserByUserName(String username);

    // 根据手机号获取用户信息
    SysUser getUserByPhone(String phone);

    // 根据邮箱获取用户信息
    SysUser getUserByEmail(String email);

    // 根据id获取用户信息
    SysUser getUserById(Integer id);

    // 修改用户信息
    Results<SysUser> updateUser(SysUser sysUser, Integer roleId);

    // 删除用户信息
    int deleteUser(Integer id);

    // 根据用户名模糊查询用户信息
    Results<SysUser> getUserByFuzzyUserNameByPage(String username, Integer offset, Integer limit);

    // 批量删除用户信息
    int batchDeleteUserById(Integer[] ids);

    // 修改密码
    Results<SysUser> changePassword(String username, String oldPassword, String newPassword);
}
