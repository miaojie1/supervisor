package com.xinguan.workprocess.controller;

import com.xinguan.workprocess.model.CheckStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("审核状态相关接口")
@RestController
@RequestMapping("/checkStatus")
public class CheckStatusController extends WorkProcessBaseController{
    @PostMapping(value = "/listAllCheckStatus")
    @ApiOperation(value = "获取所有审核信息")
    public List<CheckStatus> listAllCheckStatus() {
        return checkStatusService.listAllCheckStatus();
    }
}
