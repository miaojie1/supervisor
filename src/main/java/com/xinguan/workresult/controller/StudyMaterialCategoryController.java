package com.xinguan.workresult.controller;

import com.xinguan.workresult.model.StudyMaterialCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("学习资料类别相关接口")
@RestController
@RequestMapping("/studyMaterialCategory")
public class StudyMaterialCategoryController extends WorkResultBaseController{

    @PostMapping(value = "/listAllStudyMaterialCategories")
    @ApiOperation("获取所有的学习资料类型列表不分页")
    public List<StudyMaterialCategory> listAllStudyMaterialCategories(){
        return studyMaterialCategoryService.listAllStudyMaterialCategories();
    }
}
