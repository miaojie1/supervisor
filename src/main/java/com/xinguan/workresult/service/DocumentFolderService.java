package com.xinguan.workresult.service;

import com.xinguan.workresult.model.DocumentFolder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentFolderService {
    Page<DocumentFolder> listFolderByPage(int pageSize, int pageNo, String folderName);
    DocumentFolder saveOrUpdate(DocumentFolder documentFolder);
    void removeDocumentFolder(Long id);
    List<DocumentFolder> listAllDocuFolder();
    DocumentFolder findDocuFolderById(Long id);
}
