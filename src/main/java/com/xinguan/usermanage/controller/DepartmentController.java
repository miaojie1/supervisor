package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.Department;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation(value = "保存或更新部门Get")
    @GetMapping("/edit")
    public Department saveOrUpdate(@ApiParam(name = "departmentId", value = "部门ID,如果是修改此值不能为空") Long departmentId) {
        Department department = null;
        if (departmentId != null) {
            department = departmentService.getDepartmentById(departmentId);
        }
        if (department == null) {
            department = new Department();
        }
        return department;
    }

    @ApiOperation(value = "保存或更新部门Post")
    @PostMapping("/saveOrUpdate")
    public ResultInfo saveOrUpdate(@ApiParam(name = "department", required = true, value = "待保存或更新的部门") @RequestBody Department department,
                                   @ApiParam(name = "superiorDepartmentId", value = "上级部门ID") Long superiorDepartmentId) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            Department result = departmentService.saveOrUpdate(department, superiorDepartmentId);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("saveOrUpdate department failed:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }


    @ApiOperation(value = "批量删除部门")
    @PostMapping("/delDepartmentBatch")
    public ResultInfo deleteDepartmentById(@ApiParam(name = "departmentIds", required = true, value = "需要删除的部门ID，多个ID使用英文逗号分隔") String departmentIds) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            departmentService.removeDepartmentBatch(departmentIds);
            resultInfo.setMessage("删除成功");
            resultInfo.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("删除部门失败，ids:[" + departmentIds + "],error:" + e);
            resultInfo.setMessage("删除失败");
            resultInfo.setStatus(false);
        }

        return resultInfo;
    }


}
