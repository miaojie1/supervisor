package com.xinguan.workprocess.controller;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Patrol;
import com.xinguan.workprocess.model.Project;
import com.xinguan.workresult.model.AccountRecord;
import com.xinguan.workresult.service.AccountCategoryService;
import com.xinguan.workresult.service.AccountRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/patrol")
@Api(value = "巡视相关接口")
public class PatrolController extends WorkProcessBaseController{

    @Autowired
    AccountRecordService accountRecordService;
    @Autowired
    AccountCategoryService accountCategoryService;

    @PostMapping(value = "/listAllProjects")
    @ApiOperation(value = "获取所有项目信息")
    public List<Project> listProjects() {
        return projectService.listAllProjects();
    }

    @PostMapping(value = "/listAllEmployees")
    @ApiOperation(value = "获取所有人员信息")
    public List<Employee> listAllEmployees() {
        return employeeService.listAllEmployees();
    }

    @PostMapping(value = "/listPatrolByDepartment")
    @ApiOperation(value = "获取当前部门巡视列表")
    public PageInfo<Patrol> listPatrols(
            @ApiParam(name = "pageSize", required = true, value = "每页的条数") @RequestParam("pageSize") int pageSize,
            @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @RequestParam("pageNo") int pageNo,
            @ApiParam(name = "location", required = true, value = "巡视部位") @RequestParam(name = "location") String location
    ) {
        return patrolService.listPatrolByDepartment(pageNo,pageSize,location);
    }

    @PostMapping(value = "/savePatrol")
    @ApiOperation(value = "增加或修改 巡视")
    public ResultInfo savePatrol(
            @ApiParam(name = "patrol", required = true, value = "待保存的对象") @RequestBody Patrol patrol){
        ResultInfo resultInfo = new ResultInfo();
        try {
            patrol.setModificationDate(new Date());
            if (patrol.getId() == null){
                patrol.setCreateDate(new Date());
                patrol.setInspector(employeeService.getCurrentUser());
                patrol.setDepartment(employeeService.getCurrentUser().getDepartment());
                patrol.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
                resultInfo.setMessage("添加巡视成功！");
            }else {
                resultInfo.setMessage("修改巡视成功！");
            }
            Patrol havaSavePatrol = patrolService.savePatrol(patrol);
            AccountRecord toSaveAcc = new AccountRecord();
            if (accountRecordService.getAccByRecordId(havaSavePatrol.getId())==null){
                toSaveAcc.setRecordId(havaSavePatrol.getId());
            } else {
                toSaveAcc = accountRecordService.getAccByRecordId(havaSavePatrol.getId());
            }
            toSaveAcc.setRecordName(havaSavePatrol.getLocation());
            toSaveAcc.setDepartment(havaSavePatrol.getDepartment());
            toSaveAcc.setAccountCategory(accountCategoryService.getAccCategoryByName("巡视"));
            accountRecordService.saveAccountRecord(toSaveAcc);
            resultInfo.setStatus(true);
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加巡视失败！");
        }
        return  resultInfo;
    }

    @PostMapping(value = "/checkPatrol")
    @ApiOperation(value = "审核人审核巡视")
    public ResultInfo allotUserAuditPatrol(@ApiParam(name = "patrolId", required = true, value = "文档审核对象ID") @RequestParam Long patrolId,
                                               @ApiParam(name = "taskId", required = true, value = "待办任务ID") @RequestParam String taskId,
                                               @ApiParam(name = "approved", required = true, value = "是否通过") @RequestParam Boolean approved,
                                               @ApiParam(name = "auditOpinion", required = true, value = "审核意见") @RequestParam String auditOpinion) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            patrolService.checkPatrol(patrolId,taskId,approved,auditOpinion);
            resultInfo.setStatus(true);
            resultInfo.setMessage("审核成功");
        } catch (Exception e) {
            resultInfo.setMessage("审核失败");
            resultInfo.setStatus(false);
            e.printStackTrace();
        }

        return resultInfo;
    }

    @PostMapping(value = "/deletePatrolById")
    @ApiOperation(value = "删除 巡视 只有比自己职位高的才可以进行删除操作")
    public ResultInfo deletePatrolById(
            @ApiParam(name = "id", required = true, value = "id") @RequestParam int id){
        ResultInfo resultInfo = new ResultInfo();
        try {
            patrolService.deletePatrolById(new Long((long)id));
            resultInfo.setMessage("删除成功！");
            resultInfo.setStatus(true);
        }catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败！");
        }
        return resultInfo;
    }
}
