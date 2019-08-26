package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.PostingSystem;
import com.xinguan.utils.PageInfo;
import com.xinguan.utils.ResultInfo;
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

@RestController
@RequestMapping("/posting")
@Api(value = "公告相关接口 ")
public class PostingSystemController extends BaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger(PostingSystemController.class);

    @ApiOperation(value = "首页公告列表", notes = "返回有效期内的公告。")
    @PostMapping("/listExpPostingPage")
    public List<PostingSystem> listExpPostingPage() {
        List<PostingSystem> postingSystems = postingSystemService.listExpPostingByPage();
        return postingSystems;
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

    @PostMapping(value = "/savePosting")
    @ApiOperation(value = "公告新增或修改POST方法")
    public ResultInfo addOrEdit(@ApiParam(name = "posting", required = true, value = "待保存的对象") @RequestBody PostingSystem postingSystem,
                                @ApiParam(name = "attachmentId", value = "上传的附件Id") Long attachmentId) {
        ResultInfo resultInfo =new ResultInfo();
        try{
            PostingSystem result = postingSystemService.saveOrUpdate(postingSystem,employeeService.getCurrentUser(),attachmentId);
            resultInfo.setStatus(true);
            resultInfo.setMessage("保存成功");
            resultInfo.setObject(result);
        }catch (Exception e) {
            resultInfo.setStatus(false);
            resultInfo.setMessage("保存失败");
        }
        return resultInfo;
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

    @PostMapping("/delPostingBatch")
    @ApiOperation(value = "批量删除Posting")
    public ResultInfo batchDeletePosting(@ApiParam(name = "postingIds", required = true, value = "需要删除的PostingId，多个PostingId用英文逗号分隔") String postingIds) {
        final ResultInfo resultInfo = new ResultInfo();
        try {
            postingSystemService.removePostingBatch(postingIds);
            resultInfo.setStatus(true);
            resultInfo.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("删除公告失败，ids:[" + postingIds + "],error:" + e);
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }

    @PostMapping(value = "/announcementDetail/postingId/{postingId}")
    @ApiOperation(value = "公告详情页")
    public ResultInfo getPostingById(@ApiParam(name = "postingId", value = "查看详情的posting id，此值不能为空")@PathVariable String postingId) {
        ResultInfo resultInfo =new ResultInfo();
        PostingSystem result = postingSystemService.getPostingSystemById(Long.parseLong(postingId));
        resultInfo.setStatus(true);
        resultInfo.setObject(result);
        return resultInfo;
    }
}
