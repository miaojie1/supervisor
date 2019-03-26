package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.usermanage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhangzhan
 * @date 2019-03-25 16:33
 */
@Controller
public class BaseController {
    @Autowired
    protected MenuService menuService;
    @Autowired
    protected EmployeeService employeeService;

}
