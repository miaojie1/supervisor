package com.xinguan.usermanage.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.xinguan.usermanage.model.Attachment;
import com.xinguan.usermanage.model.PostingSystem;
import com.xinguan.utils.CommonUtil;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posting")
@Api(value = "公告相关接口 ")
public class PostingSystemController extends BaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger(PostingSystemController.class);

    @ApiOperation(value = "首页公告列表", notes = "返回有效期内的公告。")
    @PostMapping("/listExpPostingPage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<PostingSystem> listExpPostingPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                      @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                      @ApiParam(name = "paramJson", value = "查询条件用json拼接，格式：{\"key1\":\"value1\",\"key2\":value2}") String paramJson) {
        Map<String, Object> param = CommonUtil.transforParamToMap(paramJson);
        Page<PostingSystem> postingSystems = postingSystemService.listExpPostingByPage(pageSize, pageNo);
        return new PageInfo<>(postingSystems, param);
    }

    @ApiOperation(value = "获取公告列表", notes = "返回公告列表。支持通过公告名称模糊查询。")
    @PostMapping("/listPostingPage/pageNo/{pageNo}/pageSize/{pageSize}")
    public PageInfo<PostingSystem> listPostingPage(@ApiParam(name = "pageSize", required = true, value = "每页的条数") @PathVariable("pageSize") int pageSize,
                                                   @ApiParam(name = "pageNo", required = true, value = "当前页，页数从0开始") @PathVariable("pageNo") int pageNo,
                                                   @ApiParam(name = "postingName", value = "公告名称，支持模糊查询") String postingName) {
        Page<PostingSystem> postingSystems = postingSystemService.listPostingByPage(pageSize, pageNo, postingName);
        Map<String, Object> param = Maps.newHashMap("postingName", postingName);
        return new PageInfo<>(postingSystems, param);
    }

    @GetMapping(value = "/addOrEditPosting")
    @ApiOperation(value = "公告新增或修改GET方法")
    public PostingSystem addOrEditPosting(@ApiParam(name = "postingId", value = "posting id,如果是修改，此值不能为空") String postingId) {
        PostingSystem postingSystem;
        if (StringUtils.isEmpty(postingId) || "{postingId}".equals(postingId)) {
            postingSystem = new PostingSystem();
            postingSystem.setCreateDate(new Date());
        } else {
            postingSystem = postingSystemService.getPostingSystemById(Long.parseLong(postingId));
        }
        return postingSystem;
    }

    @PostMapping(value = "/savePosting")
    @ApiOperation(value = "公告新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "posting", required = true, value = "待保存的对象") @RequestPart("postingSystem") PostingSystem postingSystem,
                                @RequestPart("uploadFile") MultipartFile[] multipartFile) {
        try{
            List<Attachment> attachments =postingSystem.getAttachments();
            for (MultipartFile file : multipartFile){
                if (file.isEmpty())
                    continue;
                attachments.add(attachmentService.saveOrUpdate(attachmentService.uploadFile(file)));
            }
            postingSystem.setAttachments(attachments);
            PostingSystem result = postingSystemService.addOrEditPosting(postingSystem,employeeService.getCurrentUser());
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("save menu data:" + JSON.toJSONString(result));
            return new ResultInfo(true, "保存成功");
        }catch (Exception e) {
            LOGGER.error("保存公告失败：" + e);
            return new ResultInfo(false, "保存失败");
        }
    }

    @PostMapping("/delete/postingId/{postingId}")
    @ApiOperation(value = "根据Posting ID删除 公告")
    public ResultInfo deleteById(@ApiParam(name = "postingId", required = true, value = "需要删除的posting ID") @PathVariable String postingId) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            postingSystemService.removePosting(Long.parseLong(postingId));
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除Posting失败：id:" + postingId + ",errorMessage:" + e );
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除Posting")
    public ResultInfo batchDeletePosting(@ApiParam(name = "postingIds", required = true, value = "需要删除的PostingId，多个PostingId用英文逗号分隔") String postingIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            String[] ids = postingIds.split(",");
            Set<String> idStr = Sets.newHashSet(ids);
            Set<Long> idLong = idStr.stream().map(Long::parseLong).collect(Collectors.toSet());
            postingSystemService.batchRemovePosting(idLong);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

    @GetMapping(value = "/announcementDetail")
    @ApiOperation(value = "公告详情页")
    public PostingSystem getPostingById(@ApiParam(name = "postingId", value = "查看详情的posting id，此值不能为空") String postingId) {
        PostingSystem postingSystem = postingSystemService.getPostingSystemById(Long.parseLong(postingId));
        return postingSystem;
    }
}
