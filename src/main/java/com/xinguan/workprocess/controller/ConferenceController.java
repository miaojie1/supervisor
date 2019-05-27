package com.xinguan.workprocess.controller;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Conference;
import com.xinguan.workprocess.model.ConferenceSummary;
import com.xinguan.workprocess.model.Project;
import com.xinguan.workresult.model.AccountRecord;
import com.xinguan.workresult.service.AccountCategoryService;
import com.xinguan.workresult.service.AccountRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api("建立会议相关接口")
@RestController
@RequestMapping("/conference")
public class ConferenceController extends WorkProcessBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

    @Autowired
    AccountRecordService accountRecordService;
    @Autowired
    AccountCategoryService accountCategoryService;

    @PostMapping(value = "/listAllEmployees")
    @ApiOperation(value = "获取所有人员信息")
    public List<Employee> listAllEmployees() {
        return employeeService.listAllEmployees();
    }

    @PostMapping(value = "/getCurrentUser")
    @ApiOperation(value = "获取当前用户信息")
    public Employee getCurrentUser() {
        return employeeService.getCurrentUser();
    }

    @PostMapping(value = "/listAllConferenceSummary")
    @ApiOperation(value = "获取所有会议总结信息")
    public List<ConferenceSummary> listAllConferenceSummary() {
        return conferenceSummaryService.listAllConferenceSummary();
    }

    @PostMapping(value = "/listAllProjects")
    @ApiOperation(value = "获取所有项目信息")
    public List<Project> listAllProjects() {
        return projectService.listAllProjects();
    }

    @ApiOperation(value = "获取监理会议列表", notes = "返回项目列表。支持通过项目名称模糊查询。")
    @PostMapping("/listConferencePage/pageSize/{pageSize}/pageNo/{pageNo}")
    public PageInfo<Conference> listConfrencePage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                  @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                  @ApiParam(name = "content", value = "名称，支持模糊查询") String content) {
        return conferenceService.listConferenceByPage(pageSize, pageNo,content);
    }

    @PostMapping(value = "/saveConference")
    @ApiOperation(value = "项目新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "conference", required = true, value = "待保存的对象") @RequestBody Conference conference) {
        ResultInfo resultInfo =new ResultInfo();
        try{

            Conference havaConference = conferenceService.saveOrUpdate(conference);
            AccountRecord toSaveAcc = new AccountRecord();
            if (accountRecordService.getAccByRecordId(havaConference.getId())==null){
                toSaveAcc.setRecordId(havaConference.getId());
            } else {
                toSaveAcc = accountRecordService.getAccByRecordId(havaConference.getId());
            }
            toSaveAcc.setRecordName(havaConference.getContent());
            toSaveAcc.setDepartment(havaConference.getDepartment());
            toSaveAcc.setAccountCategory(accountCategoryService.getAccCategoryByName("监理会议"));
            accountRecordService.saveAccountRecord(toSaveAcc);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(havaConference);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping("/delete/conferenceId/{conferenceId}")
    @ApiOperation(value = "根据project ID删除 项目")
    public ResultInfo deleteById(@ApiParam(name = "conferenceId", required = true, value = "需要删除的conference ID") @PathVariable String conferenceId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            conferenceService.removeConference(Long.parseLong(conferenceId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除项目失败：id:" + conferenceId + ",errorMessage:" + e );
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

    @PostMapping(value = "/allotWriteTask")
    @ApiOperation(value = "总监分配填写会议纪要任务")
    public ResultInfo allotWriteTask(@ApiParam(name = "conferenceId",required = true,value = "会议任务ID") @RequestParam Long conferenceId,
                                     @ApiParam(name = "employees",required = true,value = "需要填写的人员列表") @RequestBody List<Employee> employees) {

        ResultInfo resultInfo = new ResultInfo();
        try {
            conferenceService.allotWriter(conferenceId,employees);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
        } catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping(value = "/allotUserWrite")
    @ApiOperation(value = "分配的人填写会议纪要")
    public ResultInfo allotUserWrite(@ApiParam(name = "conferenceId", required = true, value = "文档审核对象ID") @RequestParam Long conferenceId,
                                     @ApiParam(name = "taskId", required = true, value = "待办任务ID") @RequestParam String taskId,
                                     @ApiParam(name = "content", required = true, value = "审核意见") String content) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            conferenceService.writeSummary(conferenceId, taskId, content);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
        } catch (Exception e) {
            resultInfo.setMessage("保存失败");
            resultInfo.setStatus(false);
            e.printStackTrace();
        }

        return resultInfo;
    }

    @PostMapping(value = "/checkConference")
    @ApiOperation(value = "审核人审核会议")
    public ResultInfo allotUserAuditPatrol(@ApiParam(name = "conferenceId", required = true, value = "文档审核对象ID") @RequestParam Long conferenceId,
                                           @ApiParam(name = "taskId", required = true, value = "待办任务ID") @RequestParam String taskId,
                                           @ApiParam(name = "approved", required = true, value = "是否通过") @RequestParam Boolean approved,
                                           @ApiParam(name = "auditOpinion", required = true, value = "审核意见") @RequestParam String auditOpinion) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            conferenceService.checkConference(conferenceId,taskId,approved,auditOpinion);
            resultInfo.setStatus(true);
            resultInfo.setMessage("审核成功");
        } catch (Exception e) {
            resultInfo.setMessage("审核失败");
            resultInfo.setStatus(false);
            e.printStackTrace();
        }

        return resultInfo;
    }
}
