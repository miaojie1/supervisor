package com.xinguan.workprocess.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.xinguan.usermanage.controller.BaseController;
import com.xinguan.utils.ResultInfo;
import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api("知识库相关接口")
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController extends WorkProcessBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeController.class);

    @PostMapping(value = "/listAllKnowledge")
    @ApiOperation("获取所有的知识库文件夹不分页")
    public List<Knowledge> listAllEmployeeStatus(@ApiParam(name = "fileFolder", required = true, value = "") @RequestBody FileFolder fileFolder) {
        return knowledgeService.listAllKnowledge(fileFolder);
    }

    @GetMapping(value = "/addOrEditKnowledge")
    @ApiOperation("增加或修改文件夹")
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

    @PostMapping(value = "/saveKnowLedge")
    @ApiOperation(value = "用户新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "knowledge", required = true, value = "待保存的对象") @RequestBody Knowledge knowledge) {
        try {
            Knowledge result = knowledgeService.saveOrUpdate(knowledge);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("save fileFolder data:" + JSON.toJSONString(result));
            }
            return new ResultInfo(true, "保存成功");
        } catch (Exception e) {
            LOGGER.error("保存用户失败：" + e);
            return new ResultInfo(false, "保存失败");
        }
    }

    @PostMapping("/delete/fileFolder/{fileFolderId}")
    @ApiOperation(value = "根据FileFolder ID删除资源")
    public ResultInfo deleteById(@ApiParam(name = "fileFolderId", required = true, value = "需要删除的FileFolder ID") @PathVariable String fileFolderId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            fileFolderService.removeFileFolder(Long.parseLong(fileFolderId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除FileFolder失败：id:" + fileFolderId + ",errorMessage:" + e);
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
