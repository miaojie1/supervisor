package com.xinguan.workresult.controller;

import com.alibaba.fastjson.JSON;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.ExaminationRecord;
import com.xinguan.workresult.model.TestPaper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api("考试记录相关接口")
@RestController
@RequestMapping("/examinationRecord")
public class ExaminationRecordController extends WorkResultBaseController{
    private final static Logger LOGGER = LoggerFactory.getLogger(ExaminationRecordController.class);
    @PostMapping(value = "/addExaminationRecord")
    @ApiOperation(value = "考试记录新增POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "testPaperId", value = "指定试卷") String testPaperId) {
        try {
            TestPaper testPaper = testPaperService.getById(Long.parseLong(testPaperId));
            ExaminationRecord result = examinationRecordService.saveOrUpdate(testPaper,employeeService.getCurrentUser());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("增加考试记录" + JSON.toJSONString(result));
            }
            return new ResultInfo(true, "保存成功",result);
        } catch (Exception e) {
            LOGGER.error("增加考试记录失败：" + e);
            return new ResultInfo(false, "保存失败");
        }
    }

    @PostMapping(value = "/updateExaminationRecord")
    @ApiOperation(value = "提交考试记录修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "examinationRecordId", value = "考试记录Id") String examinationRecordId,
                                @ApiParam(name = "score", value = "分数") int score) {
        try {
            examinationRecordService.update(examinationRecordId,score);
            ExaminationRecord result = examinationRecordService.findById(Long.parseLong(examinationRecordId));
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("提交考试记录" + JSON.toJSONString(result));
            }
            return new ResultInfo(true, "保存成功",result);
        } catch (Exception e) {
            LOGGER.error("提交考试记录失败：" + e );
            return new ResultInfo(false, "保存失败");
        }
    }

    @PostMapping(value = "/listExaminationRecord/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取考试记录")
    public PageInfo<ExaminationRecord> listExaminationRecord(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                     @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                     @ApiParam(name = "testPaperId", value = "根据试卷查询")String testPaperId,
                                                     @ApiParam(name = "candidateId", value = "根据参加考试的人查询") String candidateId) {
        Page<ExaminationRecord> examinationRecords = examinationRecordService.listExaminationRecordByPage(pageSize,pageNo,testPaperId,candidateId);
        Map<String, Object> param = Maps.newHashMap("param",testPaperId+candidateId );
        return new PageInfo<>(examinationRecords,param);
    }
    @PostMapping(value = "/listAllEmployees")
    @ApiOperation(value = "获取所有人员信息")
    public List<Employee> listAllEmployees() {
        return employeeService.listAllEmployees();
    }

    @PostMapping(value = "/listAllTestPapers")
    @ApiOperation(value = "获取所有试卷信息")
    public List<TestPaper> listAllTestPapers() {
        return testPaperService.listAllTestPapers();
    }
}
