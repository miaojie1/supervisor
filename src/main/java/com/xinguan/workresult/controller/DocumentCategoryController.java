package com.xinguan.workresult.controller;

import com.xinguan.workresult.model.DocumentCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("文件类型相关接口")
@RestController
@RequestMapping("/documentCategory")
public class DocumentCategoryController extends WorkResultBaseController {
    @PostMapping(value = "/listAllDocumentCategories")
    @ApiOperation("获取所有的文件类型列表不分页")
    public List<DocumentCategory> listAllDocumentCategories(){
        return documentCategoryService.listAllDocumentCategories();
    }
}
