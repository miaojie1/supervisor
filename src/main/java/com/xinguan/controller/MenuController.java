package com.xinguan.controller;

import com.xinguan.model.Menu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangzhan
 * @date 2019-03-23 16:01
 */
@RestController
@RequestMapping("/menu")
@Api(value = "菜单相关接口 ")
public class MenuController {

    @RequestMapping(value = "/listMenu")
    @ApiOperation(value = "获取当前登录用户的菜单")
    public List<Menu> listMenuByCurrentUser() {

        return null;
    }
}
