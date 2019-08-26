package com.xinguan.workresult.controller;

import com.xinguan.workresult.model.AccountRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accountRecord")
@Api(value = "监理台账相关接口")
public class AccountRecordController extends WorkResultBaseController {

    @PostMapping(value = "/listAccRecordsByTitle")
    @ApiOperation(value = "列出 监理台账列表")
    public Page<AccountRecord> listDocumentAuditsByDepart(
            @ApiParam(name = "pageSize", required = true, value = "每页的条数") @RequestParam("pageSize") int pageSize,
            @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @RequestParam("pageNo") int pageNo,
            @ApiParam(name = "title", required = true, value = "记录名称") @RequestParam(name = "title") String title){
        return accountRecordService.listAccountRecordByPage(pageNo,pageSize,title);
    }
}
