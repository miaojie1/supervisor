package com.xinguan.controller;

import com.xinguan.model.Employee;
import com.xinguan.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangzhan
 * @date 2019-01-02 18:27
 */
@RestController
@RequestMapping("/login")
@Api(value = "登录控制器")
public class LoginController {

    @Autowired
    private EmployeeService employeeService;
    private final static Logger LOG = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = "/username/{name}",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录的用户",notes = "测试使用")
    public Employee getUser(@ApiParam(name = "username",required = true,value = "用户名") @PathVariable("name") String username, HttpServletRequest request) {
        LOG.info("获取的参数：" + username);
        Employee user = employeeService.getCurrentUser();
        return user;
    }

}
