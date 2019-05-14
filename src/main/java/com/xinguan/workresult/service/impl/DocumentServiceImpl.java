package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workresult.model.Document;
import com.xinguan.workresult.model.DocumentFolder;
import com.xinguan.workresult.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class DocumentServiceImpl extends BaseService<Document> implements DocumentService {
    @Value("${upload_location}")
    private String srcPath;

    @Override
    public Page<Document> listDocumentByFolderPage(int pageSize, int pageNo, DocumentFolder documentFolder) {
        return documentRepository.findByDocumentFolder(documentFolder,PageRequest.of(pageNo,pageSize,Sort.Direction.ASC,"createDate"));
    }
    @Override
    public void downloadDocument(String documentPath, HttpServletResponse response)
            throws ServletException, IOException{
        String path = srcPath + "/";
        File file = new File(path, documentPath);

        // 获取文件名
        String fileName = null;
        if(documentPath.contains("/")) {
            fileName = documentPath.substring(documentPath.lastIndexOf("/") + 1);
        } else {
            fileName = documentPath;
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
    public void removeDocument(Long id){
        Assert.notNull(id, "The given Id must not be null!");
        documentRepository.deleteById(id);
    }
}
