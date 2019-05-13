package com.xinguan.workresult.controller;

import com.alibaba.fastjson.JSON;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workresult.model.DocumentFolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api("文档文件夹相关接口")
@RestController
@RequestMapping("/documentFolder")
public class DocumentFolderController extends WorkResultBaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DocumentFolderController.class);

    @ApiOperation(value = "获取文档文件夹列表", notes = "返回文档文件夹列表。支持通过文件夹名称模糊查询。")
    @PostMapping("/listDocumentFolderPage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<DocumentFolder> listDocumentFolderPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                 @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                 @ApiParam(name = "documentFolderName", value = "角色名称，支持模糊查询") String documentFolderName) {
        Page<DocumentFolder> documentFolders = documentFolderService.listFolderByPage(pageSize, pageNo, documentFolderName);
        Map<String, Object> param = Maps.newHashMap("documentFolderName", documentFolderName);
        return new PageInfo<>(documentFolders, param);
    }
    @PostMapping(value = "/saveDocumentFolder")
    @ApiOperation(value = "文档文件夹新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "documentFolder", required = true, value = "待保存的对象") @RequestBody DocumentFolder documentFolder) {
        try {
            DocumentFolder result = documentFolderService.saveOrUpdate(documentFolder);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save DocumentFolder data:" + JSON.toJSONString(result));
            }
            return new ResultInfo(true, "保存成功");
        } catch (Exception e) {
            LOGGER.error("保存文档文件夹失败：" + e);
            return new ResultInfo(false, "保存失败");
        }

    }

    @PostMapping("/delete/documentFolder/{documentFolderId}")
    @ApiOperation(value = "根据documentFolder ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "documentFolderId", required = true, value = "需要删除的documentFolder ID") @PathVariable String documentFolderId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            documentFolderService.removeDocumentFolder(Long.parseLong(documentFolderId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除documentFolder失败：id:" + documentFolderId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
