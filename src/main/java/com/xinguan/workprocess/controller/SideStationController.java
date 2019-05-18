package com.xinguan.workprocess.controller;


import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.SideStation;
import com.xinguan.workresult.model.AccountRecord;
import com.xinguan.workresult.service.AccountCategoryService;
import com.xinguan.workresult.service.AccountRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/sideStation")
@Api(value = "旁站相关接口")
public class SideStationController extends WorkProcessBaseController {
    @Autowired
    AccountRecordService accountRecordService;
    @Autowired
    AccountCategoryService accountCategoryService;

    @PostMapping(value = "/listSideStation/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取旁站列表")
    public Page<SideStation> listSideStations(
            @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
            @ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
            @ApiParam(name = "partName", value = "部位名称") String partName) {
        return sideStationService.listSideStations(pageNo,pageSize,partName);
    }





    @PostMapping(value = "/saveSideStation")
    @ApiOperation(value = "增加或修改 旁站")
    public ResultInfo saveSideStation(
            @ApiParam(name = "sideStation", required = true, value = "待保存的对象") @RequestBody SideStation sideStation){
        ResultInfo resultInfo = new ResultInfo();
        try {
            if (sideStation.getId() == null){
                sideStation.setCreateDate(new Date());
                sideStation.setSponsor(employeeService.getCurrentUser());
                sideStation.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
                sideStation.setCheckStatus(checkStatusService.getOneById(1));
                resultInfo.setMessage("添加旁站成功！");
            }else {
                sideStation.setModifier(employeeService.getCurrentUser());
                resultInfo.setMessage("修改旁站成功！");
            }
            SideStation haveSaveSideStation =  sideStationService.saveSideStation(sideStation);

            AccountRecord toSaveAcc = new AccountRecord();
            if (accountRecordService.getAccByRecordId(haveSaveSideStation.getId())==null){
                toSaveAcc.setRecordId(haveSaveSideStation.getId());
            } else {
                toSaveAcc = accountRecordService.getAccByRecordId(haveSaveSideStation.getId());
            }
            toSaveAcc.setRecordName(haveSaveSideStation.getPart());
            toSaveAcc.setDepartment(haveSaveSideStation.getSponsor().getDepartment());
            toSaveAcc.setAccountCategory(accountCategoryService.getAccCategoryByName("旁站"));
            accountRecordService.saveAccountRecord(toSaveAcc);

            resultInfo.setStatus(true);
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加进场验收失败！");
        }
        return  resultInfo;
    }

    @PostMapping(value = "/deleteSideStationById")
    @ApiOperation(value = "删除 旁站 只有比自己职位高的才可以进行删除操作")
    public ResultInfo deleteSideStationById(
            @ApiParam(name = "id", required = true, value = "id") @RequestParam int id
    ){
        ResultInfo resultInfo = new ResultInfo();
        try {
            sideStationService.deleteSideStationById(new Long((long)id));
            resultInfo.setMessage("删除成功！");
            resultInfo.setStatus(true);
        }catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败！");
        }
        return resultInfo;
    }

}
