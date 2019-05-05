package com.xinguan.usermanage.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.xinguan.usermanage.model.Menu;
import com.xinguan.utils.CommonUtil;
import com.xinguan.utils.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Maps;
import com.xinguan.core.CustomResponse;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.MD5Util;
import com.xinguan.utils.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @ApiOperation(value = "获取用户列表", notes = "")
    @PostMapping("/listEmployeePage/pageSize/{pageSize}/pageNo/{pageNo}")
    public PageInfo<Employee> listDepartmentPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                 @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                 @ApiParam(name = "username", value = "用户名称，支持模糊查询") String username) {
        //Map<String, Object> param = CommonUtil.transforParamToMap(paramJson);
        Page<Employee> page = employeeService.listEmployeeByPage(pageSize, pageNo, username);
        Map<String, Object> param = Maps.newHashMap("username", username);
        return new PageInfo<>(page, param);
    }

    @GetMapping(value = "/addOrEditEmployee")
    @ApiOperation(value = "用户新增或修改GET方法")
    public Employee addOrEditEmployee(@ApiParam(name = "employeeId", value = "employee id,如果是修改，此值不能为空") String employeeId) {
        Employee employee;
        if (StringUtils.isEmpty(employeeId) || "{employeeId}".equals(employeeId)) {
            employee = new Employee();
            employee.setCreateDate(new Date());
        } else {
            employee = employeeService.getEmployeeById(Long.parseLong(employeeId));
        }
        return employee;
    }

    @PostMapping(value = "/saveEmployee")
    @ApiOperation(value = "用户新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "employee", required = true, value = "待保存的对象") @RequestBody Employee employee) {
        try {
            Employee result = employeeService.addOrEditEmployee(employee);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save employee data:" + JSON.toJSONString(result));
            }
            return new ResultInfo(true, "保存成功");
        } catch (Exception e) {
            LOGGER.error("保存用户失败：" + e);
            return new ResultInfo(false, "保存失败");
        }

    }

    @PostMapping("/delete/employeeId/{employeeId}")
    @ApiOperation(value = "根据Employee ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "employeeId", required = true, value = "需要删除的Employee ID") @PathVariable String employeeId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            employeeService.removeEmployee(Long.parseLong(employeeId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除Employee失败：id:" + employeeId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败，请检查是否有角色拥有此菜单");
        }
        return resultInfo;
    }

    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除Employee")
    public ResultInfo batchDeleteEmployee(@ApiParam(name = "employeeIds", required = true, value = "需要删除的EmployeeId，多个EmployeeId用英文逗号分隔") String employeeIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            String[] ids = employeeIds.split(",");
            Set<String> idStr = Sets.newHashSet(ids);
            Set<Long> idLong = idStr.stream().map(Long::parseLong).collect(Collectors.toSet());
            employeeService.batchRemoveEmployee(idLong);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

}
