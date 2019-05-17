package com.xinguan.workresult.service;

import com.xinguan.workresult.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DocumentService {
    Page<Document> listDocumentByFolderPage(int pageSize, int pageNo,String documentName, String documentFolderId, String documentCategoryId);
    void downloadDocument(String documentPath, HttpServletResponse response)throws ServletException, IOException;
    void removeDocument(Long documentId);
    Document uploadFile(MultipartFile multipartFile);
    Document saveOrUpdate(Document document);
    Document findDocuById(Long id);
    void downloadDoc(String filePath, HttpServletResponse response);
}
