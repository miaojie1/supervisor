package com.xinguan.workprocess.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.ProjectSupervisionDepartment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/projectSupervisionDepartment")
@Api(value = "监理部相关接口")
public class ProjectSupervisionDpController extends WorkProcessBaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger(ProjectSupervisionDpController.class);
    // 分页
    @ApiOperation(value = "获取监理部信息列表", notes = "返回监理部信息列表。支持通过监理部名称模糊查询。")
    @PostMapping("/listProjectSupervisionDepartmentPage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<ProjectSupervisionDepartment> listProjectSupervisionDpPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                                  @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                                  @ApiParam(name = "projectSupervisionDpName", value = "监理部名称，支持模糊查询") String projectSupervisionDpName) {
        Page<ProjectSupervisionDepartment> supervisionDepartments = projectSupervisionDepartmentService.listProjectSupervisionDbByPage(pageSize, pageNo, projectSupervisionDpName);
        Map<String, Object> param = Maps.newHashMap("projectSupervisionDpName", projectSupervisionDpName);
        return new PageInfo<>(supervisionDepartments, param);
    }




    @PostMapping(value = "/saveProjectSupervisionDepartment")
    @ApiOperation(value = "监理部新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "project", required = true, value = "待保存的对象") @RequestBody ProjectSupervisionDepartment projectSupervisionDepartment) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            ProjectSupervisionDepartment result = projectSupervisionDepartmentService.saveOrUpdate(projectSupervisionDepartment);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }


    @PostMapping("/delete/projectSupervisionDpId/{projectSupervisionDpId}")
    @ApiOperation(value = "根据project ID删除 项目")
    public ResultInfo deleteById(@ApiParam(name = "projectSupervisionDpId", required = true, value = "projectSupervisionDepartment ID") @PathVariable String projectSupervisionDpId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            projectSupervisionDepartmentService.removeProjectSupervisionDepartment(Long.parseLong(projectSupervisionDpId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除监理部信息失败：id:" + projectSupervisionDpId + ",errorMessage:" + e );
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

    @PostMapping("/delProjectSupervisionDpBatch")
    @ApiOperation(value = "批量删除ProjectSupervisionDepartment")
    public ResultInfo batchDeleteProjectSupervisionDepartment(@ApiParam(name = "projectSupervisionDpIds", required = true, value = "projectSupervisionDpId，多个projectSupervisionDpId用英文逗号分隔") String projectSupervisionDpIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            projectSupervisionDepartmentService.removeProjectSupervisionDepartmentBatch(projectSupervisionDpIds);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("删除项目失败，ids:[" + projectSupervisionDpIds + "],error:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

}
