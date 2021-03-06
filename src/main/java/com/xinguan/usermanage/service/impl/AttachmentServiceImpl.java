package com.xinguan.usermanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Attachment;
import com.xinguan.usermanage.service.AttachmentService;
import com.xinguan.utils.PreviewDocument;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl extends BaseService<Attachment> implements AttachmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Value("${upload_location}")
    private String srcPath;

    @Override
    @Transactional
    public Attachment saveOrUpdate(Attachment attachment) {
        attachment.setUploadDate(new Date());
        return attachmentRepository.saveAndFlush(attachment);
    }

    @Override
    public void downloadFile(String filePath, HttpServletResponse response)
            throws ServletException, IOException{
        String path = srcPath + "/";
        File file = new File(path, filePath);

        // 获取文件名
        String fileName = null;
        if(filePath.contains("/")) {
            fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        } else {
            fileName = filePath;
        }

        // 设置以流的形式下载文件，这样可以实现任意格式的文件下载
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", " attachment;filename=" + fileName);
        response.setContentLength((int) file.length());

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[128];
            int count = 0;
            while ((count = fis.read(buffer)) > 0) {
                response.getOutputStream().write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().flush();
            response.getOutputStream().close();
            fis.close();
        }
    }

    @Override
    public Attachment uploadFile(MultipartFile multipartFile){
        String targetFilePath = srcPath;
        //1，获取原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        //2,截取源文件的文件名前缀,不带后缀
        String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        //3,加工处理文件名，原文件加上时间戳
        String newFileNamePrefix  = fileNamePrefix + System.currentTimeMillis();
        //4,得到新文件名
        String fileName = newFileNamePrefix + originalFilename.substring(originalFilename.lastIndexOf("."));
        File targetFile = new File(targetFilePath + File.separator + fileName);
        FileOutputStream fileOutputStream = null;
        Attachment attachment =new Attachment();
        attachment.setName(fileName);
        attachment.setUrl(targetFilePath);
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            IOUtils.copy(multipartFile.getInputStream(), fileOutputStream);
            LOGGER.info("------>>>>>>uploaded a file successfully!<<<<<<------");
        } catch (IOException e) {
            LOGGER.error("上传文件失败：" + e);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                LOGGER.error("失败" + e);
            }
        }
        return attachment;
    }

    @Transactional
    @Override
    public void removeAttachment(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        attachmentRepository.deletePostingSystemAttachmentById(id);
        attachmentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void batchRemoveAttachment(Set<Long> ids) {
        Set<Attachment> attachments = ids.stream().map(id -> getAttachmentById(id)).collect(Collectors.toSet());
        attachmentRepository.deleteInBatch(attachments);
    }

    @Override
    public Attachment getAttachmentById(Long id) {
        return attachmentRepository.getOne(id);
    }

    @Override
    public String previewFile(String fileName){
        //文件上传转换,获取返回数据
        String convertByFile = PreviewDocument.SubmitPost("http://dcs.yozosoft.com:80/upload", srcPath+"/"+fileName, "1");
        JSONObject obj = JSONObject.parseObject(convertByFile);
        String urlData = null;
        if ("0".equals(obj.getString("result"))) {// 转换成功
            urlData = obj.getString("data");
            urlData = urlData.replace("[\"", "");//去掉[
            urlData = urlData.replace("\"]", "");//去掉]
            //最后urlData是文件的浏览地址
            System.out.println(urlData);//打印网络文件预览地址
        } else {// 转换失败
            urlData="转换失败";
        }
        return urlData;
    }
}
