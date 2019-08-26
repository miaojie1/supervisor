package com.xinguan.workprocess.controller;

import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Project;
import com.xinguan.workprocess.model.SiteAcceptance;
import com.xinguan.workresult.model.AccountRecord;
import com.xinguan.workresult.service.AccountCategoryService;
import com.xinguan.workresult.service.AccountRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/siteAcceptance")
@Api(value = "进场验收相关接口")
public class SiteAcceptanceController extends WorkProcessBaseController {

    @Autowired
    AccountRecordService accountRecordService;
    @Autowired
    AccountCategoryService accountCategoryService;
    @PostMapping(value = "/listAllProjects")
    @ApiOperation(value = "获取所有项目信息")
    public List<Project> listAllProjects() {
        return projectService.listAllProjects();
    }

    @PostMapping(value = "/listAllEmployees")
    @ApiOperation(value = "获取所有人员信息")
    public List<Employee> listAllEmployees() {
        return employeeService.listAllEmployees();
    }

    @PostMapping(value = "/listSiteAcceptsByDepart")
    @ApiOperation(value = "获取当前部门进场验收列表")
    public PageInfo<SiteAcceptance> listSiteAccepts(
            @ApiParam(name = "pageSize", required = true, value = "每页的条数") @RequestParam("pageSize") int pageSize,
            @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @RequestParam("pageNo") int pageNo,
            @ApiParam(name = "materialName", required = true, value = "材料名称") @RequestParam(name = "materialName") String materialName) {
        return siteAcceptanceService.listSiteAcceptancesByDepart(pageNo,pageSize,materialName);
    }

    @PostMapping(value = "/saveSiteAcceptance")
    @ApiOperation(value = "增加或修改 进场验收")
    public ResultInfo saveSiteAcceptance(
            @ApiParam(name = "siteAcceptance", required = true, value = "待保存的对象") @RequestBody SiteAcceptance siteAcceptance){
        ResultInfo resultInfo = new ResultInfo();
        try {
            if (siteAcceptance.getId() == null){
                siteAcceptance.setCreateDate(new Date());
                siteAcceptance.setSponsor(employeeService.getCurrentUser());
                siteAcceptance.setDepartment(employeeService.getCurrentUser().getDepartment());
                siteAcceptance.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
                resultInfo.setMessage("添加进场验收成功！");
            }else {
                siteAcceptance.setModificationDate(new Date());
                resultInfo.setMessage("修改进场验收成功！");
            }

            SiteAcceptance haveSaveSiteAcc = siteAcceptanceService.saveSiteAcceptance(siteAcceptance);
            AccountRecord toSaveAcc = new AccountRecord();
            if (accountRecordService.getAccByRecordId(haveSaveSiteAcc.getId())==null){
                toSaveAcc.setRecordId(haveSaveSiteAcc.getId());
            } else {
                toSaveAcc = accountRecordService.getAccByRecordId(haveSaveSiteAcc.getId());
            }
            toSaveAcc.setRecordName(haveSaveSiteAcc.getMaterialName());
            toSaveAcc.setDepartment(haveSaveSiteAcc.getDepartment());
            toSaveAcc.setAccountCategory(accountCategoryService.getAccCategoryByName("进场验收"));
            accountRecordService.saveAccountRecord(toSaveAcc);
            resultInfo.setStatus(true);
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加进场验收失败！");
        }
        return  resultInfo;
    }

    @PostMapping(value = "/checkSiteAcceptance")
    @ApiOperation(value = "审核人审核进场验收")
    public ResultInfo allotUserAuditSiteAccept(@ApiParam(name = "siteAcceptanceId", required = true, value = "文档审核对象ID") @RequestParam Long siteAcceptanceId,
                                     @ApiParam(name = "taskId", required = true, value = "待办任务ID") @RequestParam String taskId,
                                     @ApiParam(name = "approved", required = true, value = "是否通过") @RequestParam Boolean approved,
                                     @ApiParam(name = "auditOpinion", required = true, value = "审核意见") @RequestParam String auditOpinion) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            siteAcceptanceService.checkSiteAccept(siteAcceptanceId,taskId,approved,auditOpinion);
            resultInfo.setStatus(true);
            resultInfo.setMessage("审核成功");
        } catch (Exception e) {
            resultInfo.setMessage("审核失败");
            resultInfo.setStatus(false);
            e.printStackTrace();
        }

        return resultInfo;
    }

    @PostMapping(value = "/deleteSiteAcceptanceById")
    @ApiOperation(value = "删除 进场验收 只有比自己职位高的才可以进行删除操作")
    public ResultInfo deleteSiteAcceptById(
            @ApiParam(name = "id", required = true, value = "id") @RequestParam int id){
        ResultInfo resultInfo = new ResultInfo();
        try {
           siteAcceptanceService.deleteSiteAcceptById(new Long((long)id));
           resultInfo.setMessage("删除成功！");
           resultInfo.setStatus(true);
        }catch (Exception e){
           resultInfo.setStatus(false);
           resultInfo.setMessage("删除失败！");
        }
        return resultInfo;
    }
}
