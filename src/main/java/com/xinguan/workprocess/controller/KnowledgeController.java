package com.xinguan.workprocess.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.xinguan.usermanage.controller.BaseController;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.FileCategory;
import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Api("知识库相关接口")
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController extends WorkProcessBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeController.class);

    @PostMapping(value = "/listAllKnowledge")
    @ApiOperation("获取所有的知识库文件夹不分页")
    public List<Knowledge> listAllKnowledge() {
        return knowledgeService.listAllKnowledge();
    }

    @PostMapping(value = "/listAllFileCategory")
    @ApiOperation("获取所有的文件类型不分页")
    public List<FileCategory> listAllFileCategory() {
        return fileCategoryService.listAllCategory();
    }

    @PostMapping(value = "/findKnowledgeByFileFolder")
    @ApiOperation("通过文件夹中文件不分页")
    public List<Knowledge> findKnowledgeByFileFolder(@ApiParam(name = "fileFolderId", value = "")  String fileFolderId) {
        return knowledgeService.findKnowledgeByFileFolder(fileFolderId);
    }

    @PostMapping(value = "/findKnowledgeByFileName")
    @ApiOperation("通过文件夹中文件不分页")
    public List<Knowledge> findByFileName(@ApiParam(name = "fileName", value = "")  String fileName) {
        return knowledgeService.findByFileName(fileName);
    }

        @PostMapping(value = "/findKnowledgeByFileNameAndFolder")
    @ApiOperation("通过文件夹中文件不分页")
    public List<Knowledge> findByFileNameAndFolder(@ApiParam(name = "fileName", value = "")  String fileName,
                                             @ApiParam(name = "fileFolderId", value = "")  String fileFolderId) {
        return knowledgeService.findByFileNameAndFileFolder(fileName,fileFolderId);
    }

    @PostMapping(value = "/uploadKnowledge")
    @ApiOperation(value = "附件上传方法")
    public Long uploadFileTest(@RequestParam("file") MultipartFile multipartFile,
                               @RequestParam("fileFolderId") String fileFolderId) {
        Knowledge knowledge = knowledgeService.saveOrUpdate(knowledgeService.uploadFile(multipartFile), fileFolderId, employeeService.getCurrentUser());
        return knowledge.getId();
    }

    @ApiOperation(value = "下载附件")
    @GetMapping("/downloadKnowledge")
    public void downloadFile(@RequestParam("filePath") String filePath, HttpServletResponse response) throws ServletException, IOException {
        knowledgeService.downloadFile(filePath,response);
    }


    @GetMapping(value = "/addOrEditKnowledge")
    @ApiOperation("增加或修改文件")
    public Knowledge addOrEdit(@ApiParam(name = "knowledgeId", value = "knowledge id,如果是修改，此值不能为空") String knowledgeId){
        Knowledge knowledge;
        if (StringUtils.isEmpty(knowledgeId) || "{knowledgeId}".equals(knowledgeId)) {
            knowledge = new Knowledge();
            knowledge.setCreateDate(new Date());
        } else {
            knowledge = knowledgeService.getKnowledgeById(Long.parseLong(knowledgeId));
        }
        return knowledge;
    }

//    @PostMapping(value = "/saveKnowledge")
//    @ApiOperation(value = "用户新增或修改POST方法")
//    public ResultInfo addOrEdit(@ApiParam(name = "id", value = "待保存的对象")  Long id,
//                                @ApiParam(name = "fileFolder", value = "待保存的对象")  Long fileFolderId,
//                                @ApiParam(name = "fileCategory", value = "待保存的对象")  Long fileCategoryId) {
//        try {
//            Knowledge knowledge = knowledgeService.getKnowledgeById(id);
//            Knowledge result = knowledgeService.saveOrUpdate(knowledge,fileFolderId, fileCategoryId, employeeService.getCurrentUser());
//            if (LOGGER.isDebugEnabled()) {
//                LOGGER.debug("save fileFolder data:" + JSON.toJSONString(result));
//            }
//            return new ResultInfo(true, "保存成功");
//        } catch (Exception e) {
//            LOGGER.error("保存用户失败：" + e);
//            return new ResultInfo(false, "保存失败");
//        }
//    }

    @PostMapping("/delete/knowledge/{knowledgeId}")
    @ApiOperation(value = "根据Knowledge ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "knowledgeId", required = true, value = "需要删除的knowledge ID") @PathVariable String knowledgeId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            knowledgeService.removeKnowledge(Long.parseLong(knowledgeId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除FileFolder失败：id:" + knowledgeId + ",errorMessage:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败，请检查是否有角色拥有此菜单");
        }
        return resultInfo;
    }

    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除Knowledge")
    public ResultInfo batchDeleteKnowledge(@ApiParam(name = "knowledgeIds", required = true, value = "需要删除的knowledgeId，多个knowledgeIds用英文逗号分隔") String knowledgeIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            String[] ids = knowledgeIds.split(",");
            Set<String> idStr = Sets.newHashSet(ids);
            Set<Long> idLong = idStr.stream().map(Long::parseLong).collect(Collectors.toSet());
            knowledgeService.batchRemoveKnowledge(idLong);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
