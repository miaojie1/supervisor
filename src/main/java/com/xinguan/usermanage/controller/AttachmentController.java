package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.Attachment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attachment")
@Api(value = "附件相关接口 ")
public class AttachmentController extends BaseController{

    @PostMapping(value = "/upload")
    @ApiOperation(value = "附件上传方法")
    public Long uploadFileTest(@RequestParam("uploadFile") MultipartFile multipartFile) {
        Attachment attachment=attachmentService.saveOrUpdate(attachmentService.uploadFile(multipartFile));
        return attachment.getId();
    }
}
