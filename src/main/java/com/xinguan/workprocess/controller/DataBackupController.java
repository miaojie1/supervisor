package com.xinguan.workprocess.controller;

import com.xinguan.job.BackUpDataJob;
import com.xinguan.usermanage.controller.BaseController;
import com.xinguan.usermanage.model.BackUpData;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class DataBackupController extends BaseController {
    @Autowired
    BackUpDataJob backUpDataJob;

    @PostMapping(value = "/dataBackup")
    @ResponseBody
    public ResultInfo toBackup(HttpServletResponse response) throws Exception{
        ResultInfo resultInfo = new ResultInfo();
        try {
            backUpDataJob.execute();
            resultInfo.setMessage("数据备份成功！");
            resultInfo.setStatus(true);
        }catch (Exception e){
            resultInfo.setStatus(false);
            resultInfo.setMessage("数据备份失败！");
            resultInfo.setObject(e);
        }
        return  resultInfo;
    }

    @PostMapping(value = "/getDataBackupList")
    public PageInfo<BackUpData> listBackupData(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @RequestParam("pageSize") int pageSize,
                                           @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @RequestParam("pageNo") int pageNo,
                                           @ApiParam(name = "name", required = true, value = "查询名称") @RequestParam("name") String name) {
        Page<BackUpData> backUpDatas= backupDataService.listBackupDataByPage(pageSize,pageNo,name);
        Map<String, Object> param = Maps.newHashMap("name", name);
        return new PageInfo<>(backUpDatas, param);
    }
}