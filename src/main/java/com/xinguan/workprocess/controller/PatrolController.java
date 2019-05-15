package com.xinguan.workprocess.controller;

import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Patrol;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/patrol")
@Api(value = "巡视相关接口")
public class PatrolController extends WorkProcessBaseController{

    @PostMapping(value = "/listPatrolByDepartment")
    @ApiOperation(value = "获取当前部门巡视列表")
    public Page<Patrol> listPatrols(
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
            patrolService.savePatrol(patrol);
            resultInfo.setStatus(true);
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加巡视失败！");
        }
        return  resultInfo;
    }

    @PostMapping(value = "/deletePatrolById")
    @ApiOperation(value = "删除 巡视 只有比自己职位高的才可以进行删除操作")
    public ResultInfo deletePatrolById(
            @ApiParam(name = "id", required = true, value = "id") @RequestParam int id){
        ResultInfo resultInfo = new ResultInfo();
        if(patrolService.findById(id).getOriginRank() > employeeService.getCurrentUser().getDepartmentPosition().getRank()){
            try {
                patrolService.deletePatrolById(new Long((long)id));
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
