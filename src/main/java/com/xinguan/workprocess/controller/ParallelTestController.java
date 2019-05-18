package com.xinguan.workprocess.controller;

import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.ParallelTest;
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
@RequestMapping("/parallelTest")
@Api(value = "平行检验相关接口")
public class ParallelTestController extends WorkProcessBaseController{
    @Autowired
    AccountRecordService accountRecordService;
    @Autowired
    AccountCategoryService accountCategoryService;

    @PostMapping(value = "/listParallelTest/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取旁站列表")
    public Page<ParallelTest> listParallelTests(
            @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
            @ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
            @ApiParam(name = "partName", value = "部位名称") String partName) {
        return parallelTestService.listParallelTests(pageNo,pageSize,partName);
    }


    @PostMapping(value = "/saveParallelTest")
    @ApiOperation(value = "增加或修改 平行验收")
    public ResultInfo saveSideStation(
            @ApiParam(name = "parallelTest", required = true, value = "待保存的对象") @RequestBody ParallelTest parallelTest){
        ResultInfo resultInfo = new ResultInfo();
        try {
            if (parallelTest.getId() == null){
                parallelTest.setOriginRank(employeeService.getCurrentUser().getDepartmentPosition().getRank());
                parallelTest.setCheckStatus(checkStatusService.getOneById(1));
                resultInfo.setMessage("添加平行验收成功！");
            }else {
                resultInfo.setMessage("修改平行验收成功！");
            }
            ParallelTest haveSaveParallelTest =  parallelTestService.saveParallelTest(parallelTest);

            AccountRecord toSaveAcc = new AccountRecord();
            if (accountRecordService.getAccByRecordId(haveSaveParallelTest.getId())==null){
                toSaveAcc.setRecordId(haveSaveParallelTest.getId());
            } else {
                toSaveAcc = accountRecordService.getAccByRecordId(haveSaveParallelTest.getId());
            }
            toSaveAcc.setRecordName(haveSaveParallelTest.getPart());
            toSaveAcc.setDepartment(haveSaveParallelTest.getSponsor().getDepartment());
            toSaveAcc.setAccountCategory(accountCategoryService.getAccCategoryByName("平行验收"));
            accountRecordService.saveAccountRecord(toSaveAcc);

            resultInfo.setStatus(true);
        } catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setObject(e);
            resultInfo.setMessage("添加平行验收失败！");
        }
        return  resultInfo;
    }


    @PostMapping(value = "/deleteParallelTestById")
    @ApiOperation(value = "删除 平行验收 只有比自己职位高的才可以进行删除操作")
    public ResultInfo deleteParallelTestById(
            @ApiParam(name = "id", required = true, value = "id") @RequestParam int id
    ){
        ResultInfo resultInfo = new ResultInfo();
        try {
            parallelTestService.deleteParallelTestById(new Long((long)id));
            resultInfo.setMessage("删除成功！");
            resultInfo.setStatus(true);
        }catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败！");
        }
        return resultInfo;
    }

}