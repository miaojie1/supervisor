package com.xinguan.workprocess.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.Conference;
import com.xinguan.workprocess.model.Project;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Api("建立会议相关接口")
@RestController
@RequestMapping("/conference")
public class ConferenceController extends WorkProcessBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

    @ApiOperation(value = "获取监理会议列表", notes = "返回项目列表。支持通过项目名称模糊查询。")
    @PostMapping("/listConferencePage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<Conference> listConfrencePage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                             @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                             @ApiParam(name = "content", value = "名称，支持模糊查询") String content) {
        Page<Conference> conferences = conferenceService.listConferenceByPage(pageSize, pageNo, content, employeeService.getCurrentUser());
        Map<String, Object> param = Maps.newHashMap("content", content);
        return new PageInfo<>(conferences, param);
    }

    @PostMapping(value = "/saveConference")
    @ApiOperation(value = "项目新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "conference", required = true, value = "待保存的对象") @RequestBody Conference conference) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            Conference result = conferenceService.saveOrUpdate(conference);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping("/delete/conferenceId/{conferenceId}")
    @ApiOperation(value = "根据project ID删除 项目")
    public ResultInfo deleteById(@ApiParam(name = "conferenceId", required = true, value = "需要删除的conference ID") @PathVariable String conferenceId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            conferenceService.removeConference(Long.parseLong(conferenceId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除项目失败：id:" + conferenceId + ",errorMessage:" + e );
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
