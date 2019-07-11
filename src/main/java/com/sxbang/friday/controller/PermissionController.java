package com.sxbang.friday.controller;

import com.alibaba.fastjson.JSONArray;
import com.sxbang.friday.base.result.ResponseCode;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dto.RoleDto;
import com.sxbang.friday.dto.SysPermissionDto;
import com.sxbang.friday.model.SysPermission;
import com.sxbang.friday.service.PermissionService;
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
@RequestMapping("permission")
@Slf4j
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/listAllPermission")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:query')")
    @ApiOperation(value = "获取菜单信息", notes = "id可以为null,当id不为空时不会获取当前id的菜单和子菜单")
    @ApiImplicitParam(name = "id", value = "修改菜单信息时调用", dataType = "Integer")
    public Results<JSONArray> listAllPermission(Integer id) {
        log.info("id:"+id);
        return permissionService.listAllPermission(id);
    }

    @GetMapping("/listAllPermissionByRoleId")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:query')")
    @ApiOperation(value = "根据角色编号获取菜单信息", notes = "根据角色编号获取菜单信息")
    @ApiImplicitParam(name = "roleDto", value = "角色实体类", dataType = "RoleDto")
    public Results<SysPermission> listAllPermissionByRoleId(RoleDto roleDto) {
        return permissionService.listByRoleId(roleDto.getId());
    }

    @GetMapping("/menuAll")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:query')")
    @ApiOperation(value = "获取所有菜单信息", notes = "获取所有菜单信息")
    public Results<SysPermission> getMenuAll(){
        return permissionService.getMenuAll();
    }

    @GetMapping("/menu")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:query')")
    @ApiOperation(value = "根据用户编号获取菜单信息", notes = "根据用户编号获取菜单信息")
    @ApiImplicitParam(name = "userId", value = "用户编号", dataType = "Integer")
    public Results<SysPermission> getMenu(Integer userId){
        log.info("userId:"+userId);
        return permissionService.getMenu(userId);
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @ApiOperation(value = "菜单新增页面", notes = "跳转到新增菜单信息页面")
    public String addPermission(Model model) {
        model.addAttribute(new SysPermission());
        return "permission/permission-add";
    }

    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @ApiOperation(value = "保存新增的菜单信息", notes = "保存新增的菜单信息")
    @ApiImplicitParam(name = "sysPermission", value = "菜单实例类", dataType = "SysPermission")
    public Results<SysPermission> savePermission(@RequestBody SysPermission sysPermission) {
        return permissionService.savePermission(sysPermission);
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    @ApiOperation(value = "菜单编辑页面", notes = "跳转到编辑菜单信息页面")
    public String editPermission(Model model, SysPermission sysPermission) {
        model.addAttribute(permissionService.getPermissionById(sysPermission.getId()));
        return "permission/permission-add";
    }

    // 修改权限信息
    @PostMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    @ApiOperation(value = "保存编辑的菜单信息", notes = "保存编辑的菜单信息")
    @ApiImplicitParam(name = "sysPermission", value = "菜单实例类", dataType = "SysPermission")
    public Results<SysPermission> updatePermission(@RequestBody SysPermission sysPermission) {
        return permissionService.updatePermission(sysPermission);
    }

    // 删除菜单
    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:del')")
    @ApiOperation(value = "删除菜单信息", notes = "删除菜单信息")
    @ApiImplicitParam(name = "id", value = "删除的菜单编号", dataType = "Integer")
    public Results deletePermission(Integer id) {
        return permissionService.deletePermission(id);
    }
}
