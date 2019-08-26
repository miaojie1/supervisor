package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.StudyMaterial;
import com.xinguan.workresult.service.StudyMaterialService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@Service
public class StudyMaterialServiceImpl extends BaseService<StudyMaterial> implements StudyMaterialService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudyMaterialService.class);

    @Value("${upload_location}")
    private String srcPath;
    @Override
    public Page<StudyMaterial> listStudyMaterialByPage(int pageSize, int pageNo,String departmentId,String studyMaterialName,String studyMaterialCategoryId) {
        return studyMaterialRepository.listStudyMaterial(studyMaterialName,departmentId,studyMaterialCategoryId,PageRequest.of(pageNo,pageSize,Sort.Direction.ASC,"create_date"));
    }

    @Override
    public void downloadStudyMaterialAttachment(String studyMaterialAttachmentPath, HttpServletResponse response)throws ServletException, IOException{
        String path = srcPath + "/";
        File file = new File(path, studyMaterialAttachmentPath);

        // 获取文件名
        String fileName = null;
        if(studyMaterialAttachmentPath.contains("/")) {
            fileName = studyMaterialAttachmentPath.substring(studyMaterialAttachmentPath.lastIndexOf("/") + 1);
        } else {
            fileName = studyMaterialAttachmentPath;
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

    @Transactional
    @Override
    public void uploadStudyMaterialAttachment(MultipartFile multipartFile,String studyMaterialId){
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
        StudyMaterial studyMaterial = findById(Long.parseLong(studyMaterialId));
        studyMaterial.setStudyMaterialAttachmentName(fileName);
        studyMaterial.setStudyMaterialAttachmentUrl(targetFilePath);
        try {
            fileOutputStream = new FileOutputStream(targetFile);
            IOUtils.copy(multipartFile.getInputStream(), fileOutputStream);
            saveOrUpdate(studyMaterial);
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
    }

    @Transactional
    @Override
    public void deleteStudyMaterialAttachment(String studyMaterialId){
        StudyMaterial studyMaterial = findById(Long.parseLong(studyMaterialId));
        studyMaterial.setStudyMaterialAttachmentName(null);
        studyMaterial.setStudyMaterialAttachmentUrl(null);
        saveOrUpdate(studyMaterial);
    }

    @Transactional
    @Override
    public StudyMaterial saveOrUpdate(StudyMaterial studyMaterial){
        if(studyMaterial.getId()==null){
            studyMaterial.setCreateDate(new Date());
        }
        return studyMaterialRepository.saveAndFlush(studyMaterial);
    }

    @Transactional
    @Override
    public void deleteById(Long id){
        Assert.notNull(id, "The given Id must not be null!");
        studyMaterialRepository.deleteById(id);
    }

    @Override
    public StudyMaterial findById(Long id){
        return studyMaterialRepository.findById(id).get();
    }
}
