package com.sxbang.friday.service.Impl;

import com.sxbang.friday.dao.PermissionDao;
import com.sxbang.friday.dao.UserDao;
import com.sxbang.friday.dto.LoginUser;
import com.sxbang.friday.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * @author kaneki
 */

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userDao.getUserByUserName(username);
        if (null == sysUser) {
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        }else if (sysUser.getStatus() == SysUser.Status.LOCKED){
            throw new LockedException("用户被锁定，请联系管理员");
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);
        loginUser.setPermissions(permissionDao.listByUserId(sysUser.getId()));
        return loginUser;
    }
}
