package com.sxbang.friday.controller;

import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.model.SysRoleUser;
import com.sxbang.friday.service.RoleUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author kaneki
 */
@RestController
@RequestMapping("roleUser")
@Slf4j
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    @PostMapping("/getRoleUserByUserId")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @ApiOperation(value = "获取用户角色信息", notes = "根据用户编号获取用户角色信息")
    @ApiImplicitParam(name = "userId", value = "用户编号", dataType = "Integer")
    public Results<SysRoleUser> getRoleUserByUserId(Integer userId) {
        return roleUserService.getRoleUserByUserId(userId);
    }
}
