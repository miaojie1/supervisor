package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.Department;
import com.xinguan.utils.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhangzhan
 */
@Api("部门操作相关接口")
@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @ApiOperation(value = "获取部门列表", notes = "返回当前用户所在部门及下属部门，超级管理员不受限制。支持通过部门名称模糊查询。")
    @PostMapping("/listDepartmentPage/pageSize/{pageSize}/pageNo/{pageNo}")
    public PageInfo<Department> listDepartmentPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                   @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                   @ApiParam(name = "departmentName", value = "部门名称，支持模糊查询") String departmentName) {
        Page<Department> departments = departmentService.listDepartmentByPage(pageSize, pageNo, departmentName, employeeService.getCurrentUser());
        Map<String, Object> param = Maps.newHashMap("departmentName", departmentName);
        return new PageInfo<>(departments, param);
    }


}
