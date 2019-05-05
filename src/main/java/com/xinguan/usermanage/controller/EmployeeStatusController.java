package com.xinguan.usermanage.controller;


import com.xinguan.usermanage.model.EmployeeStatus;
import com.xinguan.utils.CommonUtil;
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

@Api("员工在职状态相关接口")
@RestController
@RequestMapping("/employeeStatus")
public class EmployeeStatusController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeStatusController.class);

    @ApiOperation(value = "获取在职状态列表", notes = "返回当前用户在职状态，超级管理员不受限制。支持通过状态名称模糊查询。")
    @PostMapping("/listEmployeeStatusPage/pageSize/{pageSize}/pageNo/{pageNo}")
    public PageInfo<EmployeeStatus> listDepartmentPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                       @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                       @ApiParam(name = "paramJson", value = "查询条件用json拼接，格式：{\"key1\":\"value1\",\"key2\":value2}") String paramJson) {
        Map<String, Object> param = CommonUtil.transforParamToMap(paramJson);
        Page<EmployeeStatus> employeeStatuses = employeeStatusService.listEmployeeStatusByPage(pageSize, pageNo);
        return new PageInfo<>(employeeStatuses, param);
    }
}
