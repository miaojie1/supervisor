package com.xinguan.workresult.controller;

import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.service.DepartmentService;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.TestPaper;
import com.xinguan.workresult.model.TestPaperCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("考卷相关接口")
@RestController
@RequestMapping("/testPaper")
public class TestPaperController extends WorkResultBaseController{
    private final static Logger LOGGER = LoggerFactory.getLogger(TestPaperController.class);
    @Autowired
    protected DepartmentService departmentService;
    @PostMapping(value = "/listAllTestPaperCategories")
    @ApiOperation("获取所有的考卷类型列表不分页")
    public List<TestPaperCategory> listAllTestPaperCategories(){
        return testPaperCategoryService.listAllTestPaperCategories();
    }
    @PostMapping(value = "/listAllDepartment")
    @ApiOperation("获取所有的部门列表不分页")
    public List<Department> listAllDepartment(){
        return departmentService.listAllDepartment();
    }

    @PostMapping(value = "/getTestPaperById")
    @ApiOperation("获取指定考卷不分页")
    public TestPaper getTestPaperById(@ApiParam(name = "testPaperId", value = "根据Id查询")String testPaperId){
        return testPaperService.getById(Long.parseLong(testPaperId));
    }
    @PostMapping(value = "/listTestPaper/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取考卷")
    public PageInfo<TestPaper> listTestPaper(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                             @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                             @ApiParam(name = "departmentId", value = "根据部门查询")String departmentId,
                                             @ApiParam(name = "testPaperName", value = "考卷名称模糊查询") String testPaperName,
                                             @ApiParam(name = "testPaperCategoryId", value = "根据考卷类别查询") String testPaperCategoryId) {
        Page<TestPaper> testPapers = testPaperService.listTestPaperByPage(pageSize,pageNo,departmentId,testPaperName,testPaperCategoryId);
        Map<String, Object> param = Maps.newHashMap("param",departmentId+testPaperName+testPaperCategoryId);
        return new PageInfo<>(testPapers,param);
    }

    @PostMapping(value = "/saveTestPaper")
    @ApiOperation(value = "考卷新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "testPaper", required = true, value = "待保存的对象") @RequestBody TestPaper testPaper) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            TestPaper result = testPaperService.saveOrUpdate(testPaper);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
    }

    @PostMapping("/delete/testPaperId/{testPaperId}")
    @ApiOperation(value = "根据testPaper ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "testPaperId", required = true, value = "需要删除的testPaper ID") @PathVariable String testPaperId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            testPaperService.deleteById(Long.parseLong(testPaperId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除testPaper失败：id:" + testPaperId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
