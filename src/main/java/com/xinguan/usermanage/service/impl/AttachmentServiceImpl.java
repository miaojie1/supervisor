package com.xinguan.usermanage.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Attachment;
import com.xinguan.usermanage.service.AttachmentService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl extends BaseService<Attachment> implements AttachmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Value("${upload_location}")
    private String filePath;

    @Override
    @Transactional
    public Attachment saveOrUpdate(Attachment attachment) {
        attachment.setUploadDate(new Date());
        return attachmentRepository.saveAndFlush(attachment);
    }

    @Override
    public Attachment uploadFile(MultipartFile multipartFile){
        String targetFilePath = filePath;
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

}
