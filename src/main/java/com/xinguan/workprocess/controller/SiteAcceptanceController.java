package com.xinguan.workprocess.controller;

import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Project;
import com.xinguan.workprocess.model.SiteAcceptance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public Page<SiteAcceptance> listSiteAccepts(
            @ApiParam(name = "pageSize", required = true, value = "每页的条数") @RequestParam("pageSize") int pageSize,
            @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @RequestParam("pageNo") int pageNo,
            @ApiParam(name = "materialName", required = true, value = "材料名称") @RequestParam(name = "materialName") String materialName
    ) {
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
                siteAcceptanceService.saveSiteAcceptance(siteAcceptance);
                resultInfo.setMessage("添加进场验收成功！");
                resultInfo.setStatus(true);
            }else {
                Long siteAccId = siteAcceptance.getId();
                if (siteAcceptanceService.findById(Integer.parseInt(String.valueOf(siteAccId))).getOriginRank()>
                employeeService.getCurrentUser().getDepartmentPosition().getRank()){
                    siteAcceptance.setModificationDate(new Date());
                    siteAcceptanceService.saveSiteAcceptance(siteAcceptance);
                    resultInfo.setMessage("修改进场验收成功！");
                    resultInfo.setStatus(true);
                }else {
                    resultInfo.setMessage("没有权限修改！");
                    resultInfo.setStatus(false);
                }
            }
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加进场验收失败！");
        }
        return  resultInfo;
    }

    @PostMapping(value = "/deleteSiteAcceptanceById")
    @ApiOperation(value = "删除 进场验收 只有比自己职位高的才可以进行删除操作")
    public ResultInfo deleteSiteAcceptById(
            @ApiParam(name = "id", required = true, value = "id") @RequestParam int id
    ){
        ResultInfo resultInfo = new ResultInfo();
        if(siteAcceptanceService.findById(id).getOriginRank() > employeeService.getCurrentUser().getDepartmentPosition().getRank()){
           try {
               siteAcceptanceService.deleteSiteAcceptById(new Long((long)id));
               resultInfo.setMessage("删除成功！");
               resultInfo.setStatus(true);
           }catch (Exception e){
               resultInfo.setStatus(false);
               resultInfo.setMessage("删除失败！");
           }
        }else {
            resultInfo.setMessage("没有权限删除");
            resultInfo.setStatus(true);
        }

        return resultInfo;
    }
}
