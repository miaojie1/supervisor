package com.xinguan.usermanage.controller;

import com.xinguan.core.CustomResponse;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author zhangzhan
 * @date 2019-03-25 16:35
 */
@Api("用户操作相关接口")
@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseController {


    @ApiOperation(value = "保存临时用户",notes = "临时接口")
    @GetMapping("/saveEmployeeTemp")
    public CustomResponse saveEmployeeTemp() {
        Employee employee = employeeService.findEmployeeByUsername("admin");
        if (employee == null) {
            employee = new Employee();
            employee.setCreateDate(new Date());
        }
        employee.setModificationDate(new Date());
        employee.setName("admin");
        employee.setUsername("admin");
        employee.setPassword(MD5Util.encode("12345"));
        employeeService.saveEmployee(employee);
        return new CustomResponse(true, "保存成功", null);
    }
}
