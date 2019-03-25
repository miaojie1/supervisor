package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.Menu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author zhangzhan
 * @date 2019-03-23 16:01
 */
@RestController
@RequestMapping("/menu")
@Api(value = "菜单相关接口 ")
public class MenuController extends BaseController{

    @Secured("")
    @RequestMapping(value = "/listMenu")
    @ApiOperation(value = "获取当前登录用户的菜单")
    public Set<Menu> listMenuByCurrentUser() {
        return menuService.listMenuByCurrentUser();
    }


}
