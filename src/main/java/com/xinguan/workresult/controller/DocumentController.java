package com.xinguan.workresult.controller;

import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.Document;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Api("文档相关接口")
@RestController
@RequestMapping("/document")
public class DocumentController extends WorkResultBaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);
    @PostMapping(value = "/listDocumentByFolderPage/documentFolderId/{documentFolderId}/pageNo/{pageNo}/pageSize/{pageSize}")
    @ApiOperation(value = "获取指定文档库的文件")
    public PageInfo<Document> listDocumentByFolder(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                   @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                   @ApiParam(name = "documentFolderId", value = "查看详情的documentFolderId，此值不能为空")@PathVariable String documentFolderId,
                                                   @ApiParam(name = "documentName", value = "文件名称模糊查询") String documentName,
                                                   @ApiParam(name = "documentCategoryId", value = "根据文件类别查询") String documentCategoryId) {
        Page<Document> documents = documentService.listDocumentByFolderPage(pageSize,pageNo,documentName,documentFolderId,documentCategoryId);
        Map<String, Object> param = Maps.newHashMap("param", documentName+","+documentCategoryId+documentFolderId);
        return new PageInfo<>(documents,param);
    }

    @ApiOperation(value = "下载文档")
    @GetMapping("/download")
    public void downloadDocument(@RequestParam("filePath")String filePath, HttpServletResponse response) throws ServletException, IOException {
        documentService.downloadDocument(filePath,response);
    }

    @PostMapping("/delete/documentId/{documentId}")
    @ApiOperation(value = "根据document ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "documentId", required = true, value = "需要删除的document ID") @PathVariable String documentId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            documentService.removeDocument(Long.parseLong(documentId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除document失败：id:" + documentId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
