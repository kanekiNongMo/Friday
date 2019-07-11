package com.sxbang.friday.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * @author kaneki
 */
@Controller
public class SecurityController {

    @GetMapping("/login.html")
    @ApiOperation(value = "用户登录页面", notes = "跳转到用户登录页面")
    public  String login(){
        return "login";
    }

    @GetMapping("/403.html")
    @ApiOperation(value = "403报错页面", notes = "跳转到403页面")
    public String noPermission() {
        return "403";
    }

}
