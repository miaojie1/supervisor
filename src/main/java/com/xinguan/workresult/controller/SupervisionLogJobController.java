package com.xinguan.workresult.controller;

import com.xinguan.job.SupervisionLogJob;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.SupervisionLog;
import com.xinguan.workresult.model.SupervisionLogRecord;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/superLogJob")
public class SupervisionLogJobController extends WorkResultBaseController {
    @Autowired
    SupervisionLogJob supervisionLogJob;

    @ApiOperation(value = "获取所有的监理日志 分页")
    @PostMapping(value = "/listSupervisionLogByPage")
    public Page<SupervisionLog> listSupervisionLogByEmpl(
            @ApiParam(name = "pageNo", required = true,value = "页码") @RequestParam int pageNo,
            @ApiParam(name = "pageSize",required = true, value = "一页数量") @RequestParam int pageSize,
            @ApiParam(name = "username", required = true, value = "用户名")@RequestParam String username
    ){
        Page<SupervisionLog> supervisionLogs = supervisionLogService.listSupervisionLogByEmp(pageNo, pageSize,username);
        return supervisionLogs;
    }

    @PostMapping(value = "/deleteSupLogById")
    public ResultInfo deleteSupLogById(
            @ApiParam(name = "LogId", required = true,value = "页码") @RequestParam int logId
    ){
        ResultInfo resultInfo = new ResultInfo();
        try {
            supervisionLogService.deleteSupLogById((long) logId);
            resultInfo.setMessage("监理日志删除成功！");
            resultInfo.setStatus(true);
        }catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setMessage("监理日志删除失败！");
            resultInfo.setObject(e);
        }
        return resultInfo;
    }

    @ApiOperation(value = "获取所有的监理工程师")
    @PostMapping(value = "/listAllSupEmployees")
    public Set<Employee> findAllSupEmployees(){
        return supervisionLogService.listAllSuperEmployees();
    }

    @ApiOperation(value = "测试监理日志生成 仅用于测试")
    @PostMapping(value = "/addSupLogForTest")
    public ResultInfo addLog(){
        ResultInfo resultInfo =new ResultInfo();
        try {
            supervisionLogJob.createSupervisionLog();
            resultInfo.setStatus(true);
            resultInfo.setMessage("监理日志生成成功！");
        }catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setMessage("监理日志生成失败！");
            resultInfo.setObject(e);
        }
        return resultInfo;
    }

    @ApiOperation(value = "获取所有的监理日志记录 分页")
    @PostMapping(value = "/listSuperLogRecordByPage")
    public Page<SupervisionLogRecord> listSuperLogRecordByPage(
            @ApiParam(name = "pageNo", required = true,value = "页码") @RequestParam int pageNo,
            @ApiParam(name = "pageSize",required = true, value = "一页数量") @RequestParam int pageSize
    ){
        Page<SupervisionLogRecord> supervisionLogRecords = superviLogRecordService.listSupervisionLogRecordByPage(pageNo,pageSize);
        return supervisionLogRecords;
    }

    @PostMapping(value = "/deleteSupLogRecordById")
    public ResultInfo deleteSupLogRecordById(
            @ApiParam(name = "LogRecordId", required = true,value = "页码") @RequestParam int logRecordId
    ){
        ResultInfo resultInfo = new ResultInfo();
        try {
            superviLogRecordService.deleteSupLogRecordById((long) logRecordId);
            resultInfo.setMessage("监理日志记录删除成功！");
            resultInfo.setStatus(true);
        }catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setMessage("监理日志记录删除失败！");
            resultInfo.setObject(e);
        }
        return resultInfo;
    }
}
