package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.model.Menu;
import com.xinguan.utils.CommonUtil;
import com.xinguan.utils.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @author zhangzhan
 * @date 2019-03-23 16:01
 */
@RestController
@RequestMapping("/menu")
@Api(value = "菜单相关接口 ")
public class MenuController extends BaseController {

    @PostMapping(value = "/listMenu")
    @ApiOperation(value = "获取当前登录用户的菜单")
    public Set<Menu> listMenuByCurrentUser() {
        Employee employee = employeeService.getCurrentUser();
        return menuService.listMenuByEmployee(employee);
    }

    @PostMapping(value = "/listMenuPage/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取资源列表")
    public PageInfo<Menu> listMenuPage(@ApiParam(name = "pageNo", required = true, value = "当前页，页数从1开始") @PathVariable("pageNo") int pageNo,
                                       @ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                       @ApiParam(name = "paramJson", value = "查询条件用json拼接，格式：{\"key1\":\"value1\",\"key2\":value2}") @RequestBody String paramJson) {
        Map<String, Object> param = CommonUtil.transforParamToMap(paramJson);
        Page<Menu> page = menuService.listMenuByPage(pageSize, pageNo, param);
        return new PageInfo<>(page, param);
    }


    public static void main(String[] args) {
        String str = "/menu/listMenuPage/pageNo/1/pageSize/10";
        String regex = "/menu/listMenuPage/pageNo/{pageNo}/pageSize/{pageSize}";
        System.out.println(CommonUtil.verificationStr(regex, str));
    }
}
