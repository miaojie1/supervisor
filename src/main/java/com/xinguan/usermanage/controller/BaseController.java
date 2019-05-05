package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

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
    @Autowired
    protected PostingSystemService postingSystemService;
    @Autowired
    protected AttachmentService attachmentService;
    @Autowired
    private RestTemplate restTemplate;



    protected Map<String, Object> transforParamToMap(String param) {

        return null;
    }
}
