package com.xinguan.workresult.service;

import com.xinguan.workresult.model.StudyMaterial;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface StudyMaterialService {
    Page<StudyMaterial> listStudyMaterialByPage(int pageSize, int pageNo, String departmentId, String studyMaterialName, String studyMaterialCategoryId);
    void downloadStudyMaterialAttachment(String studyMaterialAttachmentPath, HttpServletResponse response)throws ServletException, IOException;
    void uploadStudyMaterialAttachment(MultipartFile multipartFile, String studyMaterialId);
    StudyMaterial findById(Long id);
    void deleteStudyMaterialAttachment(String studyMaterialId);
    StudyMaterial saveOrUpdate(StudyMaterial studyMaterial);
    void deleteById (Long id);
}
