package com.xinguan.workprocess.controller;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Project;
import com.xinguan.workprocess.model.WitnessSampling;
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
@RequestMapping("/witnessSampling")
@Api(value = "见证取样相关接口")
public class WitnessSamplingController extends WorkProcessBaseController{

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

    @PostMapping(value = "/listWitnessSamplingByDepartment")
    @ApiOperation(value = "获取当前部门见证取样列表")
    public PageInfo<WitnessSampling> listWitnessSamplings(
            @ApiParam(name = "pageSize", required = true, value = "每页的条数") @RequestParam("pageSize") int pageSize,
            @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @RequestParam("pageNo") int pageNo,
            @ApiParam(name = "samplingName", required = true, value = "取样名称") @RequestParam(name = "samplingName") String samplingName) {
        return witnessSamplingService.listWitnessSamplingByDepartment(pageNo,pageSize,samplingName);
    }

    @PostMapping(value = "/saveWitnessSampling")
    @ApiOperation(value = "增加或修改 见证取样")
    public ResultInfo saveWitnessSampling(
            @ApiParam(name = "witnessSampling", required = true, value = "待保存的对象") @RequestBody WitnessSampling witnessSampling){
        ResultInfo resultInfo = new ResultInfo();
        try {
            witnessSampling.setModificationDate(new Date());
            if (witnessSampling.getId() == null){
                witnessSampling.setCreateDate(new Date());
                witnessSampling.setCreator(employeeService.getCurrentUser());
                witnessSampling.setDepartment(employeeService.getCurrentUser().getDepartment());
                witnessSampling.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
                resultInfo.setMessage("添加见证取样成功！");
            }else {
                resultInfo.setMessage("修改见证取样成功！");
            }
            WitnessSampling haveSaveWitnessSampling =witnessSamplingService.saveWitnessSampling(witnessSampling);
            AccountRecord toSaveAcc = new AccountRecord();
            Long id = haveSaveWitnessSampling.getId();
            if (accountRecordService.getAccByRecordId(id)==null){
                toSaveAcc.setRecordId(id);
            } else {
                toSaveAcc = accountRecordService.getAccByRecordId(id);
            }
            toSaveAcc.setRecordName(haveSaveWitnessSampling.getSamplingName());
            toSaveAcc.setDepartment(haveSaveWitnessSampling.getDepartment());
            toSaveAcc.setAccountCategory(accountCategoryService.getAccCategoryByName("见证取样"));
            accountRecordService.saveAccountRecord(toSaveAcc);
            resultInfo.setStatus(true);
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加见证取样失败！");
        }
        return  resultInfo;
    }
    @PostMapping(value = "/checkWitnessSampling")
    @ApiOperation(value = "审核人审核见证取样")
    public ResultInfo allotUserAuditWitnessSampling(@ApiParam(name = "witnessSamplingId", required = true, value = "文档审核对象ID") @RequestParam Long witnessSamplingId,
                                           @ApiParam(name = "taskId", required = true, value = "待办任务ID") @RequestParam String taskId,
                                           @ApiParam(name = "approved", required = true, value = "是否通过") @RequestParam Boolean approved,
                                           @ApiParam(name = "auditOpinion", required = true, value = "审核意见") @RequestParam String auditOpinion) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            witnessSamplingService.checkWitnessSampling(witnessSamplingId,taskId,approved,auditOpinion);
            resultInfo.setStatus(true);
            resultInfo.setMessage("审核成功");
        } catch (Exception e) {
            resultInfo.setMessage("审核失败");
            resultInfo.setStatus(false);
            e.printStackTrace();
        }

        return resultInfo;
    }
    @PostMapping(value = "/deleteWitnessSamplingById")
    @ApiOperation(value = "删除 见证取样 只有比自己职位高的才可以进行删除操作")
    public ResultInfo deleteWitnessSamplingById(@ApiParam(name = "id", required = true, value = "id") @RequestParam int id){
        ResultInfo resultInfo = new ResultInfo();
        try {
            witnessSamplingService.deleteWitnessSamplingById(new Long((long)id));
            resultInfo.setMessage("删除成功！");
            resultInfo.setStatus(true);
        }catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败！");
        }
        return resultInfo;
    }
}
