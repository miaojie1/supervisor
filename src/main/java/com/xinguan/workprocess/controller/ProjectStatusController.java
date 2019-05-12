package com.xinguan.workprocess.controller;

import com.xinguan.workprocess.model.ProjectStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("项目状态相关接口")
@RestController
@RequestMapping("/projectStatus")
public class ProjectStatusController extends WorkProcessBaseController {

    @PostMapping(value = "/listAllProjectStatus")
    @ApiOperation("获取所有的在职状态列表不分页")
    public List<ProjectStatus> listAllProjectStatus(){
        return projectStatusService.listAllProjectStatus();
    }
}
