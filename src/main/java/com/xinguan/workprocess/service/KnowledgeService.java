package com.xinguan.workprocess.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.workprocess.model.FileCategory;
import com.xinguan.workprocess.model.FileFolder;
import com.xinguan.workprocess.model.Knowledge;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface KnowledgeService {

    List<Knowledge> listAllKnowledge();

    Page<Knowledge> listKnowledgeByPage(int pageSize, int pageNo, String fileName);

    List<Knowledge> findKnowledgeByFileFolder(String fileFolderId);

    List<Knowledge> findByFileNameAndFileFolder(String fileName,String fileFolderId);

    Knowledge getKnowledgeById(Long id);

    Knowledge saveOrUpdate(Knowledge knowledge, String fileFolderId, Employee employee);

    void removeKnowledge(Long id);

    void batchRemoveKnowledge(Set<Long> ids);

    Knowledge getKnowledgeByFileName(String fileName);

    Knowledge uploadFile(MultipartFile multipartFile);

    void downloadFile(String filePath, HttpServletResponse response)throws ServletException, IOException;
}
