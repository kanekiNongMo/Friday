package com.sxbang.friday.controller;

import com.sxbang.friday.base.result.PageTableRequest;
import com.sxbang.friday.base.result.ResponseCode;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dto.RoleDto;
import com.sxbang.friday.model.SysRole;
import com.sxbang.friday.service.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 * @author kaneki
 */
@Controller
@RequestMapping("role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/all")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:query')")
    @ApiOperation(value = "获取角色信息", notes = "获取角色信息")
    public Results<SysRole> getRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:query')")
    @ApiOperation(value = "分页获取角色信息", notes = "分页获取角色信息")
    @ApiImplicitParam(name = "pageRequest", value = "分页查询实体类")
    public Results<SysRole> getAllRoleByPage(PageTableRequest pageRequest) {
        pageRequest.countOffset();
        return roleService.getAllRoleByPage(pageRequest.getOffset(), pageRequest.getLimit());
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @ApiOperation(value = "角色新增页面", notes = "跳转到新增角色信息页面")
    public String addRole(Model model) {
        model.addAttribute(new SysRole());
        return "role/role-add";
    }

    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:add')")
    @ApiOperation(value = "保存新增的角色信息", notes = "角色唯一")
    @ApiImplicitParam(name = "roleDto", value = "角色实例类", dataType = "RoleDto")
    public Results<SysRole> saveRole(@RequestBody RoleDto roleDto) {
        SysRole sysRole = null;
        sysRole = roleService.getUserByRoleName(roleDto.getName());
        if (sysRole != null && !sysRole.getId().equals(roleDto.getId())) {
            return Results.failure(ResponseCode.ROLENAME_REPEAT.getCode(), ResponseCode.ROLENAME_REPEAT.getMessage());
        }
        return roleService.saveRole(roleDto);
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @ApiOperation(value = "角色编辑页面", notes = "跳转到编辑角色信息页面")
    @ApiImplicitParam(name = "sysRole", value = "角色实例类", dataType = "SysRole")
    public String editRole(Model model, SysRole sysRole) {
        model.addAttribute(roleService.getRoleById(sysRole.getId()));
        return "role/role-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @ApiOperation(value = "保存编辑的角色信息", notes = "角色名唯一")
    @ApiImplicitParam(name = "roleDto", value = "角色实例类", dataType = "RoleDto")
    public Results<SysRole> updateRole(@RequestBody RoleDto roleDto) {
        SysRole sysRole = null;
        sysRole = roleService.getUserByRoleName(roleDto.getName());
        if (sysRole != null && !sysRole.getId().equals(roleDto.getId())) {
            return Results.failure(ResponseCode.ROLENAME_REPEAT.getCode(), ResponseCode.ROLENAME_REPEAT.getMessage());
        }
        return roleService.updateRole(roleDto);
    }

    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:del')")
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @ApiImplicitParam(name = "roleDto", value = "角色实体类", required = true, dataType = "RoleDto")
    public Results<SysRole> deleteUser(RoleDto roleDto) {
        return roleService.deleteRole(roleDto.getId());
    }

    // 根据id批量删除角色信息
    @PostMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:del')")
    @ApiOperation(value = "批量删除角色信息", notes = "批量删除角色信息")
    @ApiImplicitParam(name = "ids", value = "角色编号", required = true, dataType = "Integer[]")
    public Results<SysRole> batchDeleteUserById(Integer[] ids) {
        return roleService.batchDeleteRoleById(ids);

    }

    // 根据角色名称实现模糊查询
    @GetMapping("/findRoleByFuzzyRoleName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:query')")
    @ApiOperation(value = "模糊查询角色信息", notes = "模糊搜索查询角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "模糊搜索的角色名", required = true),
            @ApiImplicitParam(name = "pageRequest", value = "分页查询实体类")
    })
    public Results<SysRole> findRoleByFuzzyRoleName(PageTableRequest tableRequest, String name) {
        tableRequest.countOffset();
        return roleService.getRoleByFuzzyRoleNameByPage(name, tableRequest.getOffset(), tableRequest.getLimit());
    }
}
