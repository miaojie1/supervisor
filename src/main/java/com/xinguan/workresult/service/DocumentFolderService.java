package com.xinguan.workresult.service;

import com.xinguan.workresult.model.DocumentFolder;
import org.springframework.data.domain.Page;

public interface DocumentFolderService {
    Page<DocumentFolder> listFolderByPage(int pageSize, int pageNo, String folderName);
    DocumentFolder saveOrUpdate(DocumentFolder documentFolder);
    void removeDocumentFolder(Long id);
    DocumentFolder getDocumentFolderById(Long id);
}
