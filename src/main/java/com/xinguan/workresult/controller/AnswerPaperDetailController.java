package com.xinguan.workresult.controller;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.AnswerPaperDetail;
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

@Api("答题相关接口")
@RestController
@RequestMapping("/answerPaperDetail")
public class AnswerPaperDetailController extends WorkResultBaseController{
    private final static Logger LOGGER = LoggerFactory.getLogger(AnswerPaperDetailController.class);

    @PostMapping(value = "/listAllEmployees")
    @ApiOperation(value = "获取所有人员信息")
    public List<Employee> listAllEmployees() {
        return employeeService.listAllEmployees();
    }

    @PostMapping(value = "/listAnswerPaperDetailByTestPaperDetailPage/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取试题的答卷")
    public PageInfo<AnswerPaperDetail> listAnswerPaperDetailByTestPaperDetailPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                                          @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                                          @ApiParam(name = "testPaperDetailId", value = "查看指定试题的答卷") String testPaperDetailId,
                                                                          @ApiParam(name = "respondentId", value = "查看指定答题人的答卷") String respondentId) {
        Page<AnswerPaperDetail> answerPaperDetails = answerPaperDetailService.listAnswerPaperDetailByTestPaperDetailPage(pageSize,pageNo,testPaperDetailId,respondentId);
        Map<String, Object> param = Maps.newHashMap("param",testPaperDetailId+","+respondentId);
        return new PageInfo<>(answerPaperDetails,param);
    }
    @PostMapping(value = "/saveAnswerPaperDetail")
    @ApiOperation(value = "答卷新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "answerPaperDetail", required = true, value = "待保存的对象") @RequestBody AnswerPaperDetail answerPaperDetail,
                                @ApiParam(name = "testPaperDetailId", value = "指定的试题") String testPaperDetailId) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            TestPaperDetail testPaperDetail = testPaperDetailService.getById(Long.parseLong(testPaperDetailId));
            if (answerPaperDetailService.getAnswerDetailByDetailAndRespondent(employeeService.getCurrentUser(),testPaperDetail)!=null) {
                resultInfo.setStatus(false);
                resultInfo.setMessage("保存失败，已经提交过答案");
            }else{
                answerPaperDetail.setTestPaperDetail(testPaperDetail);
                answerPaperDetail.setRespondent(employeeService.getCurrentUser());
                if (answerPaperDetail.getAnswer().equals(testPaperDetail.getAnswer()))
                    answerPaperDetail.setGradeOfAnswer(testPaperDetail.getFullScore());
                else
                    answerPaperDetail.setGradeOfAnswer(0);
                AnswerPaperDetail result = answerPaperDetailService.saveOrUpdate(answerPaperDetail);
                resultInfo.setStatus(true);
                resultInfo.setMessage("保存成功");
                resultInfo.setObject(result);
            }
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping("/delete/answerPaperDetailId/{answerPaperDetailId}")
    @ApiOperation(value = "根据answerPaperDetail ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "answerPaperDetailId", required = true, value = "需要删除的answerPaperDetail ID") @PathVariable String answerPaperDetailId) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            answerPaperDetailService.deleteById(Long.parseLong(answerPaperDetailId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除answerPaperDetail失败：id:" + answerPaperDetailId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
