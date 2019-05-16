package com.xinguan.workprocess.controller;

import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.WitnessSampling;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/witnessSampling")
@Api(value = "见证取样相关接口")
public class WitnessSamplingController extends WorkProcessBaseController{

    @PostMapping(value = "/listWitnessSamplingByDepartment")
    @ApiOperation(value = "获取当前部门见证取样列表")
    public Page<WitnessSampling> listWitnessSamplings(
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
            witnessSamplingService.saveWitnessSampling(witnessSampling);
            resultInfo.setStatus(true);
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加见证取样失败！");
        }
        return  resultInfo;
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
