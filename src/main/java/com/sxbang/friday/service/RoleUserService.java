package com.sxbang.friday.service;

import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.model.SysRoleUser;
/**
 * @author kaneki
 */
public interface RoleUserService {
    // 根据用户id获取用户角色数据
    Results<SysRoleUser> getRoleUserByUserId(Integer userId);
}
