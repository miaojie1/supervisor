package com.xinguan.workresult.controller;

import com.xinguan.job.SupervisionLogJob;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.SupervisionLog;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/superLogJob")
public class SupervisionLogJobController extends WorkResultBaseController {
    @Autowired
    SupervisionLogJob supervisionLogJob;

    @PostMapping(value = "/listSupervisionLogByPage")
    public Page<SupervisionLog> listSupervisionLogByPage(
            @ApiParam(name = "pageNo", required = true,value = "页码") @RequestParam int pageNo,
            @ApiParam(name = "pageSize",required = true, value = "一页数量") @RequestParam int pageSize
    ){
        Page<SupervisionLog> supervisionLogs = supervisionLogService.listSupervisionLogByPage(pageNo, pageSize);
        return supervisionLogs;
    }

    @ApiOperation(value = "测试监理日志生成")
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
}
