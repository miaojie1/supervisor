package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.DepartmentPosition;
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

import java.util.List;
import java.util.Map;

@Api("部门职位相关接口")
@RestController
@RequestMapping("/departmentPosition")
public class DepartmentPositionController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentPositionController.class);

    @PostMapping(value = "/listAllDepartmentPositions")
    @ApiOperation("获取所有的职位列表不分页")
    public List<DepartmentPosition> listAllDepartmentPositions(){
        return departmentPositionService.listAllDepartmentPositions();
    }

    @ApiOperation(value = "获取部门职位列表", notes = "返回当前用户所在部门职位。支持通过部门名称模糊查询。")
    @PostMapping("/listDepartmentPositionPage/pageSize/{pageSize}/pageNo/{pageNo}")
    public PageInfo<DepartmentPosition> listDepartmentPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                           @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                           @ApiParam(name = "paramJson", value = "查询条件用json拼接，格式：{\"key1\":\"value1\",\"key2\":value2}") String paramJson) {
        Map<String, Object> param = CommonUtil.transforParamToMap(paramJson);
        Page<DepartmentPosition> departmentPositions = departmentPositionService.listDepartmentPositionByPage(pageSize, pageNo);
        //Map<String, Object> param = Maps.newHashMap("departmentName", departmentName);
        return new PageInfo<>(departmentPositions,param);
    }
}
