package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.service.DepartmentService;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.usermanage.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author zhangzhan
 * @date 2019-03-25 16:33
 */
@Controller
public abstract class BaseController {
    @Autowired
    protected MenuService menuService;
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected DepartmentService departmentService;

    protected Map<String, Object> transforParamToMap(String param) {

        return null;
    }
}
