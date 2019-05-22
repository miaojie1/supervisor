package com.xinguan.workresult.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.TestPaper;
import com.xinguan.workresult.model.TestPaperDetail;
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

@Api("试题相关接口")
@RestController
@RequestMapping("/testPaperDetail")
public class TestPaperDetailController extends WorkResultBaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestPaperDetailController.class);
    @PostMapping(value = "/listTestPaperDetailByTestPaperPage/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取试卷的试题")
    public PageInfo<TestPaperDetail> listTestPaperDetailByTestPaperPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                          @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                          @ApiParam(name = "testPaperId", value = "查看指定试卷的试题") String testPaperId,
                                                          @ApiParam(name = "testPaperDetailName", value = "试题题目模糊查询") String testPaperDetailName) {
        Page<TestPaperDetail> testPaperDetails = testPaperDetailService.listTestPaperDetailByTestPaperPage(pageSize,pageNo,testPaperDetailName,testPaperId);
        Map<String, Object> param = Maps.newHashMap("param",testPaperDetailName+","+testPaperId);
        return new PageInfo<>(testPaperDetails,param);
    }
    @PostMapping(value = "/listAllDetailByTest")
    @ApiOperation("获取指定试卷试题不分页")
    public List<TestPaperDetail> listAllDetailByTest(@ApiParam(name = "testPaperId", value = "查看指定试卷的试题") String testPaperId){
        return testPaperDetailService.listAllDetailByTest(testPaperId);
    }

    @PostMapping(value = "/saveTestPaperDetail")
    @ApiOperation(value = "试题新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "testPaperDetail", required = true, value = "待保存的对象") @RequestBody TestPaperDetail testPaperDetail,
                                @ApiParam(name = "testPaperId", value = "指定试卷的试题") String testPaperId) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            TestPaper testPaper = testPaperService.getById(Long.parseLong(testPaperId));
            testPaperDetail.setTestPaper(testPaper);
            TestPaperDetail result = testPaperDetailService.saveOrUpdate(testPaperDetail);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping("/delete/testPaperDetailId/{testPaperDetailId}")
    @ApiOperation(value = "根据testPaperDetail ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "testPaperDetailId", required = true, value = "需要删除的testPaperDetail ID") @PathVariable String testPaperDetailId) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            testPaperDetailService.deleteById(Long.parseLong(testPaperDetailId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除testPaperDetail失败：id:" + testPaperDetailId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
