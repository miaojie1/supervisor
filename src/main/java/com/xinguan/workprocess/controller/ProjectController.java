package com.xinguan.workprocess.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.Project;
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

@RestController
@RequestMapping("/project")
@Api(value = "项目管理相关接口 ")
public class ProjectController extends WorkProcessBaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @ApiOperation(value = "获取项目列表", notes = "返回项目列表。支持通过项目名称模糊查询。")
    @PostMapping("/listProjectPage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<Project> listProjectPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                             @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                             @ApiParam(name = "projectName", value = "项目名称，支持模糊查询") String projectName) {
        Page<Project> projects = projectService.listProjectByPage(pageSize, pageNo, projectName);
        Map<String, Object> param = Maps.newHashMap("projectName", projectName);
        return new PageInfo<>(projects, param);
    }
}
