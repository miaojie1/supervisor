package com.xinguan.workresult.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.workresult.model.Document;
import com.xinguan.workresult.model.DocumentFolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api("文档相关接口")
@RestController
@RequestMapping("/document")
public class DocumentController extends WorkResultBaseController {
    @PostMapping(value = "/listDocumentByFolderPage/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取指定文档库的文件")
    public PageInfo<Document> listDocumentByFolder(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                   @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                   @ApiParam(name = "documentFolder", value = "指定文档库")@RequestBody DocumentFolder documentFolder) {
        Page<Document> documents = documentService.listDocumentByFolderPage(pageSize,pageNo,documentFolder);
        return new PageInfo<>(documents,null);
    }

    @ApiOperation(value = "下载文档")
    @GetMapping("/download")
    public void downloadDocument(@RequestParam("documentPath")String documentPath, HttpServletResponse response) throws ServletException, IOException {
        documentService.downloadDocument(documentPath,response);
    }
}
