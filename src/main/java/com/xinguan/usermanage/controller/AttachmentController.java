package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.Attachment;
import com.xinguan.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.ErrorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attachment")
@Api(value = "附件相关接口 ")
public class AttachmentController extends BaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger(AttachmentController.class);

    @PostMapping(value = "/upload")
    @ApiOperation(value = "附件上传方法")
    public Long uploadFileTest(@RequestParam("file") MultipartFile multipartFile) {
        Attachment attachment=attachmentService.saveOrUpdate(attachmentService.uploadFile(multipartFile));
        return attachment.getId();
    }

    @PostMapping(value = "/deleteAttachment")
    @ApiOperation(value = "附件删除方法")
    public ResultInfo deleteAttachment(@RequestParam(value = "attachmentId", required = true) Long attachmentId){
        ResultInfo resultInfo = new ResultInfo();
        try{
            attachmentService.removeAttachment(attachmentId);
            resultInfo.setMessage("删除成功");
            resultInfo.setStatus(true);
        } catch (Exception e) {
            LOGGER.error("删除附件失败：id:" + attachmentId + ",errorMessage:" + e );
            resultInfo.setStatus(false);
            resultInfo.setMessage("删除失败");
        }
        return resultInfo;
    }
}
