package com.xinguan.workprocess.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Project;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping(value = "/listAllProjects")
    @ApiOperation(value = "获取所有项目信息")
    public List<Project> listProjects() {
        return projectService.listAllProjects();
    }

    @PostMapping(value = "/listProjectsSupervisionDpIsNull")
    @ApiOperation(value = "获取所有项目信息")
    public List<Project> listProjectsSupervisionDpIsNull() {
        return projectService.listProjectsSupervisionDpIsNull();
    }

    @PostMapping(value = "/saveProject")
    @ApiOperation(value = "项目新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "project", required = true, value = "待保存的对象") @RequestBody Project project) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            Project result = projectService.saveOrUpdate(project);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping("/delete/projectId/{projectId}")
    @ApiOperation(value = "根据project ID删除 项目")
    public ResultInfo deleteById(@ApiParam(name = "projectId", required = true, value = "需要删除的project ID") @PathVariable String projectId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            projectService.removeProject(Long.parseLong(projectId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除项目失败：id:" + projectId + ",errorMessage:" + e );
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

    @PostMapping("/delProjectBatch")
    @ApiOperation(value = "批量删除Project")
    public ResultInfo batchDeleteProject(@ApiParam(name = "projectIds", required = true, value = "需要删除的ProjectId，多个ProjectId用英文逗号分隔") String projectIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            projectService.removeProjectBatch(projectIds);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("删除项目失败，ids:[" + projectIds + "],error:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
