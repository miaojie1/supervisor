package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.workprocess.model.FileCategory;
import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;
import com.xinguan.workprocess.service.KnowledgeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KnowledgeServiceImpl extends BaseService<Knowledge> implements KnowledgeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeServiceImpl.class);

    @Override
    public List<Knowledge> listAllKnowledge() {
        return knowledgeRepository.findAll();
    }

    @Override
    public Page<Knowledge> listKnowledgeByPage(int pageSize, int pageNo, String fileName) {
        Knowledge knowledge = new Knowledge();
        Example<Knowledge> example;

        if (fileName != null) {
            //transforObject(employee, params);
            knowledge.setFileName("%" + fileName + "%");
            //example = Example.of(employee);
        }
        example = getSimpleExample(knowledge);
        return knowledgeRepository.findAll(example, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public List<Knowledge> findKnowledgeByFileFolder(String fileFolderId) {
        Assert.notNull(fileFolderId, "The given Id must not be null!");
        FileFolder fileFolder = fileFolderRepository.getOne(Long.parseLong(fileFolderId));
        return knowledgeRepository.findAllByFileFolder(fileFolder);
    }

    @Override
    public List<Knowledge> findByFileNameAndFileFolder(String fileName, String fileFolderId) {
        FileFolder fileFolder = null;
        if(fileFolderId!=""){
            fileFolder = fileFolderRepository.getOne(Long.parseLong(fileFolderId));
        }
        return knowledgeRepository.findByFileNameAndFileFolderLike(fileName,fileFolderId);
    }

    @Override
    public Knowledge getKnowledgeById(Long id) {
        return knowledgeRepository.getOne(id);
    }

    @Override
    public Knowledge saveOrUpdate(Knowledge knowledge, String fileFolderId, Employee employee) {
        FileFolder fileFolder = null;
        FileCategory fileCategory = null;
        if(fileFolderId!=""){
            fileFolder = fileFolderRepository.getOne(Long.parseLong(fileFolderId));
        }
        Example<Knowledge> knowledgeExample = getSimpleExample(knowledge);
        if (knowledgeRepository.exists(knowledgeExample)) {
            Assert.notNull(knowledge.getFileName(), "文件已存在!");
        } else {
            knowledge.setCreateDate(new Date());
            knowledge.setFileFolder(fileFolder);
            knowledge.setFileCategory(fileCategory);
            knowledge.setRepublisher(employee);
        }
        return knowledgeRepository.saveAndFlush(knowledge);
    }

    @Override
    public void removeKnowledge(Long id) {
        Assert.notNull(id, "The given Id must not be null!");
        knowledgeRepository.deleteById(id);
    }

    @Override
    public void batchRemoveKnowledge(Set<Long> ids) {
        Set<Knowledge> knowledges = ids.stream().map(id -> getKnowledgeById(id)).collect(Collectors.toSet());
        knowledgeRepository.deleteInBatch(knowledges);
    }

    @Override
    public Knowledge getKnowledgeByFileName(String fileName) {
        return knowledgeRepository.findByFileNameLike("%" + fileName + "%");
    }

    @Value("${upload_location}")
    private String filePath;

    @Override
    public Knowledge uploadFile(MultipartFile multipartFile) {
        String targetFilePath = filePath;
        //1，获取原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        //2,截取源文件的文件名前缀,不带后缀
        String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        //后缀
        String fileNameUnder = originalFilename.substring(1,originalFilename.lastIndexOf("."));
        //3,加工处理文件名，原文件加上时间戳
        String newFileNamePrefix  = fileNamePrefix + System.currentTimeMillis();
        //4,得到新文件名
        String fileName = newFileNamePrefix + originalFilename.substring(originalFilename.lastIndexOf("."));
        File targetFile = new File(targetFilePath + File.separator + fileName);
        String fileUrl = targetFilePath + "." + fileName;
        FileCategory fileCategory = fileCategoryRepository.findAllByName(fileNameUnder);
        FileOutputStream fileOutputStream = null;
        Knowledge knowledge =new Knowledge();
        knowledge.setFileName(fileName);
        knowledge.setFileUrl(fileUrl);
        knowledge.setFileCategory(fileCategory);
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
        return knowledge;
    }

    @Value("${upload_location}")
    private String srcPath;

    @Override
    public void downloadFile(String filePath, HttpServletResponse response) throws ServletException, IOException {
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
        response.addHeader("Content-Disposition", " knowledge;filename=" + fileName);
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
}
