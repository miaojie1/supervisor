package com.xinguan.usermanage.controller;

import com.xinguan.usermanage.model.Attachment;
import com.xinguan.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/attachment")
@Api(value = "附件相关接口 ")
public class AttachmentController extends BaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger(AttachmentController.class);

    @PostMapping(value = "/upload")
    @ApiOperation(value = "附件上传方法")
    public Long uploadFile(@RequestParam("file") MultipartFile multipartFile) {
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

    @ApiOperation(value = "下载附件")
    @GetMapping("/download")
    public void downloadFile(@RequestParam("filePath")String filePath, HttpServletResponse response) throws ServletException, IOException {
        attachmentService.downloadFile(filePath,response);
    }

    @PostMapping(value = "/preview")
    @ApiOperation(value = "附件预览地址转换方法")
    public ResultInfo previewFile(@RequestParam("fileName") String fileName) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(true);
        resultInfo.setMessage(attachmentService.previewFile(fileName));
        return resultInfo;
    }
}
