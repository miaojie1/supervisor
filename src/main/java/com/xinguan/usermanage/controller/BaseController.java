package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.repository.RoleRepository;
import com.xinguan.usermanage.service.*;
import com.xinguan.workprocess.service.DocumentAuditService;
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
    protected EmployeeStatusService employeeStatusService;
    @Autowired
    protected DepartmentPositionService departmentPositionService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    protected RoleService roleService;

    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected OperationService operationService;

    @Autowired
    protected BackupDataService backupDataService;
    @Autowired
    protected DocumentAuditService documentAuditService;
    protected Map<String, Object> transforParamToMap(String param) {

        return null;
    }
}
