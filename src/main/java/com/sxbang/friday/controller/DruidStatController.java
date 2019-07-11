package com.sxbang.friday.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kaneki
 */
@RestController
public class DruidStatController {
    @GetMapping("/druid/stat")
    @PreAuthorize("hasAuthority('druid:eye')")
    @ApiOperation(value = "获取druid数据源", notes = "获取druid数据源")
    public Object druidStat(){
        // DruidStatManagerFacade#getDataSourceStatDataLis 该方法可以获取所有数据源的监控数据
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}