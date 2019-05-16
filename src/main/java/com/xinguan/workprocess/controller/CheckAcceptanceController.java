package com.xinguan.workprocess.controller;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.CheckAcceptance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("建立检查验收相关接口")
@RestController
@RequestMapping("/checkAcceptance")
public class CheckAcceptanceController extends WorkProcessBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckAcceptanceController.class);

    @PostMapping(value = "/listAllEmployees")
    @ApiOperation(value = "获取所有人员信息")
    public List<Employee> listAllEmployees() {
        return employeeService.listAllEmployees();
    }

    @ApiOperation(value = "获取监理会议列表", notes = "返回项目列表。支持通过项目名称模糊查询。")
    @PostMapping("/listCheckAcceptancePage/pageSize/{pageSize}/pageNo/{pageNo}")
    public PageInfo<CheckAcceptance> listCheckAcceptancePage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                       @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                       @ApiParam(name = "acceptanceName", value = "名称，支持模糊查询") String acceptanceName) {
        Page<CheckAcceptance> checkAcceptances = checkAcceptanceService.listCheckAcceptanceByPage(pageSize, pageNo,acceptanceName);
        Map<String, Object> param = Maps.newHashMap("acceptanceName", acceptanceName);
        return new PageInfo<>(checkAcceptances, param);
    }

    @PostMapping(value = "/saveCheckAcceptance")
    @ApiOperation(value = "检查验收新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "checkAcceptance", required = true, value = "待保存的对象") @RequestBody CheckAcceptance checkAcceptance) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            CheckAcceptance result = checkAcceptanceService.saveOrUpdate(checkAcceptance);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping("/delete/checkAcceptanceId/{checkAcceptanceId}")
    @ApiOperation(value = "根据checkAcceptance ID删除 项目")
    public ResultInfo deleteById(@ApiParam(name = "checkAcceptanceId", required = true, value = "需要删除的checkAcceptance ID") @PathVariable String checkAcceptanceId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            checkAcceptanceService.removeCheckAcceptance(Long.parseLong(checkAcceptanceId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除项目失败：id:" + checkAcceptanceId + ",errorMessage:" + e );
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
