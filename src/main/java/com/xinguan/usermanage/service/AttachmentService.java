package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface AttachmentService {
    Attachment saveOrUpdate(Attachment attachment);
    Attachment uploadFile(MultipartFile multipartFile);
    void removeAttachment(Long id);
    void batchRemoveAttachment(Set<Long> ids);
    Attachment getAttachmentById(Long id);
}
