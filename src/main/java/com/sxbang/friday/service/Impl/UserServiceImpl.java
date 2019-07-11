package com.sxbang.friday.service.Impl;

import com.sxbang.friday.base.result.ResponseCode;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dao.RoleUserDao;
import com.sxbang.friday.dao.UserDao;
import com.sxbang.friday.model.SysRoleUser;
import com.sxbang.friday.model.SysUser;
import com.sxbang.friday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public Results<SysUser> getAllUserByPage(Integer offset, Integer limit) {
        return Results.success(userDao.countAllUsers(), userDao.getAllUserByPage(offset, limit));
    }

    @Override
    public Results<SysUser> saveUser(SysUser sysUser, Integer roleId) {
        if (roleId != null) {
            userDao.saveUser(sysUser);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUserId(sysUser.getId());
            roleUserDao.saveRoleUser(sysRoleUser);
            return Results.success();
        }
        return Results.failure();
    }

    @Override
    public SysUser getUserByUserName(String username) {
        return userDao.getUserByUserName(username);
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    @Override
    public SysUser getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public SysUser getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public Results<SysUser> updateUser(SysUser sysUser, Integer roleId) {
        if (roleId != null) {
            userDao.updateUser(sysUser);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(sysUser.getId());
            sysRoleUser.setRoleId(roleId);
            if (roleUserDao.getRoleUserByUserId(sysUser.getId()) != null) {
                roleUserDao.updateSysRoleUser(sysRoleUser);
            } else {
                roleUserDao.saveRoleUser(sysRoleUser);
            }
            return Results.success();
        } else {
            return Results.failure();
        }
    }

    @Override
    public int deleteUser(Integer id) {
        roleUserDao.deleteRoleUserByUserId(id);
        return userDao.deleteUser(id);
    }

    @Override
    public Results<SysUser> getUserByFuzzyUserNameByPage(String username, Integer offset, Integer limit) {
        return Results.success(userDao.getUserByFuzzyUserName(username), userDao.getUserByFuzzyUserNameByPage(username, offset, limit));
    }

    @Override
    public int batchDeleteUserById(Integer[] ids) {
        roleUserDao.batchDeleteRoleUserByUserId(ids);
        return userDao.batchDeleteUserById(ids);
    }

    @Override
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        SysUser sysUser = userDao.getUserByUserName(username);
        if (sysUser == null) {
            return Results.failure(ResponseCode.USERNAME_NO_EXIST.getCode(), ResponseCode.USERNAME_NO_EXIST.getMessage());
        }
        if (!new BCryptPasswordEncoder().matches(oldPassword, sysUser.getPassword())) {
            return Results.failure(ResponseCode.OLDPASSWORD_FAILURE.getCode(), ResponseCode.OLDPASSWORD_FAILURE.getMessage());
        }
        userDao.changePassword(sysUser.getId(), new BCryptPasswordEncoder().encode(newPassword));
        return Results.success();
    }
}
