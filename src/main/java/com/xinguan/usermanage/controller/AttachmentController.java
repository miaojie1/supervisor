package com.xinguan.usermanage.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attachment")
@Api(value = "附件相关接口 ")
public class AttachmentController extends BaseController{

//    @PostMapping(value = "/upload")
//    @ApiOperation(value = "附件上传方法")
//    public ResultInfo uploadFileTest(@RequestParam("uploadFile") MultipartFile multipartFile) {
//        return attachmentService.uploadFile(multipartFile);
//    }

}
