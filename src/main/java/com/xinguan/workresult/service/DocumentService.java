package com.xinguan.workresult.service;

import com.xinguan.workresult.model.Document;
import com.xinguan.workresult.model.DocumentFolder;
import org.springframework.data.domain.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DocumentService {
    Page<Document> listDocumentByFolderPage(int pageSize, int pageNo,DocumentFolder documentFolder);
    void downloadDocument(String documentPath, HttpServletResponse response)throws ServletException, IOException;
}
