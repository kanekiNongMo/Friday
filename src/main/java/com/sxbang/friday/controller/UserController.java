package com.sxbang.friday.controller;

import com.sxbang.friday.base.result.PageTableRequest;
import com.sxbang.friday.base.result.ResponseCode;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dto.UserDto;
import com.sxbang.friday.model.SysUser;
import com.sxbang.friday.service.UserService;
import com.sxbang.friday.util.MD5;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author kaneki
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    @ApiOperation(value = "分页获取用户信息", notes = "分页获取用户信息")
    @ApiImplicitParam(name = "pageRequest", value = "分页查询实体类")
    public Results<SysUser> getUsers(PageTableRequest pageRequest) {
        pageRequest.countOffset();
        return userService.getAllUserByPage(pageRequest.getOffset(), pageRequest.getLimit());
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @ApiOperation(value = "用户新增页面", notes = "跳转到新增用户信息页面")
    public String addUser(Model model) {
        model.addAttribute(new SysUser());
        return "user/user-add";
    }

    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:add')")
    @ApiOperation(value = "保存新增的用户信息", notes = "用户名，邮箱，手机号是唯一的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto", value = "用户实例类", dataType = "UserDto"),
            @ApiImplicitParam(name = "roleId", value = "角色编号", dataType = "Integer")
    })
    public Results<SysUser> saveUser(UserDto userDto, Integer roleId) {
        SysUser sysUser = null;
        sysUser = userService.getUserByUserName(userDto.getUsername());
        if (sysUser != null && !sysUser.getId().equals(userDto.getId())) {
            return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(), ResponseCode.USERNAME_REPEAT.getMessage());
        }
        sysUser = userService.getUserByPhone(userDto.getPhone());
        if (sysUser != null && !sysUser.getId().equals(userDto.getId())) {
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(), ResponseCode.PHONE_REPEAT.getMessage());
        }
        sysUser = userService.getUserByEmail(userDto.getEmail());
        if (sysUser != null && !sysUser.getId().equals(userDto.getId())) {
            return Results.failure(ResponseCode.EMAIL_REPEAT.getCode(), ResponseCode.EMAIL_REPEAT.getMessage());
        }
        userDto.setStatus(SysUser.Status.VALID);
        // 密码加密
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        return userService.saveUser(userDto, roleId);
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "用户编辑页面", notes = "跳转到用户信息编辑页面")
    @ApiImplicitParam(name = "user", value = "用户实体类", dataType = "SysUser")
    public String editUser(Model model, SysUser sysUser) {
        model.addAttribute(userService.getUserById(sysUser.getId()));
        return "user/user-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "保存修改的用户信息", notes = "用户名，邮箱，手机号是唯一的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto", value = "用户实例类", dataType = "UserDto"),
            @ApiImplicitParam(name = "roleId", value = "角色编号", dataType = "Integer")
    })
    public Results<SysUser> updateUser(UserDto userDto, Integer roleId) {
        SysUser sysUser = null;
        sysUser = userService.getUserByEmail(userDto.getEmail());
        if (sysUser != null && !sysUser.getId().equals(userDto.getId())) {
            return Results.failure(ResponseCode.EMAIL_REPEAT.getCode(), ResponseCode.EMAIL_REPEAT.getMessage());
        }
        sysUser = userService.getUserByPhone(userDto.getPhone());
        if (sysUser != null && !sysUser.getId().equals(userDto.getId())) {
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(), ResponseCode.PHONE_REPEAT.getMessage());
        }
        sysUser = userService.getUserByUserName(userDto.getUsername());
        if (sysUser != null && !sysUser.getId().equals(userDto.getId())) {
            return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(), ResponseCode.USERNAME_REPEAT.getMessage());
        }
        return userService.updateUser(userDto, roleId);
    }

    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")
    @ApiImplicitParam(name = "userDto", value = "用户实体类", required = true, dataType = "UserDto")
    public Results<SysUser> deleteUser(UserDto userDto) {
        int count = userService.deleteUser(userDto.getId());
        if (count > 0) {
            return Results.success();
        } else {
            return Results.failure();
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    @ApiOperation(value = "批量删除用户信息", notes = "批量删除用户信息")
    @ApiImplicitParam(name = "ids", value = "删除用户的编号", dataType = "Integer[]")
    public Results<SysUser> batchDeleteUserById(Integer[] ids) {
        int count = userService.batchDeleteUserById(ids);
        if (count > 0) {
            return Results.success();
        } else {
            return Results.failure();
        }
    }

    @GetMapping("/findUserByFuzzyUserName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    @ApiOperation(value = "模糊查询用户信息", notes = "模糊搜索查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "模糊搜索的用户名", required = true),
            @ApiImplicitParam(name = "pageRequest", value = "分页查询实体类")
    })
    public Results<SysUser> findUserByFuzzyUserName(PageTableRequest tableRequest, String username) {
        tableRequest.countOffset();
        return userService.getUserByFuzzyUserNameByPage(username, tableRequest.getOffset(), tableRequest.getLimit());
    }


    @PostMapping("/changePassword")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:password')")
    @ApiOperation(value = "修改密码", notes = "修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "oldPassword", value = "旧密码"),
            @ApiImplicitParam(name = "newPassword", value = "新密码")
    })
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        return userService.changePassword(username, oldPassword, newPassword);
    }

    String pattern = "yyyy-MM-dd";

    // 日期格式转换
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));
    }
}
