package com.xinguan.usermanage.service;

import com.xinguan.usermanage.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public interface AttachmentService {
    Attachment saveOrUpdate(Attachment attachment);
    Attachment uploadFile(MultipartFile multipartFile);
    void removeAttachment(Long id);
    void batchRemoveAttachment(Set<Long> ids);
    Attachment getAttachmentById(Long id);
    void downloadFile(String filePath, HttpServletResponse response)throws ServletException, IOException;
    String previewFile(String fileName);
}
