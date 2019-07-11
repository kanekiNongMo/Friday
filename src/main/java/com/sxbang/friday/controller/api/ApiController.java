package com.sxbang.friday.controller.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
/**
 * @author kaneki
 */

@Controller
@RequestMapping("${api-url}")
public class ApiController {

    @RequestMapping("/getPage")
    @ApiOperation(value = "跳转页面", notes = "根据用户请求跳转到不同的页面")
    @ApiImplicitParam(name = "pageName", value = "页面名称", dataType = "String")
    public ModelAndView getPage(ModelAndView modelAndView, String pageName){
        modelAndView.setViewName(pageName);
        return modelAndView;
    }
}
