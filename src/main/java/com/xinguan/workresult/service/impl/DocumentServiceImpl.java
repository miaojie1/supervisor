package com.xinguan.workresult.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workresult.model.Document;
import com.xinguan.workresult.service.DocumentService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

@Service
public class DocumentServiceImpl extends BaseService<Document> implements DocumentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentService.class);

    @Value("${upload_location}")
    private String srcPath;
    @Autowired
    EmployeeService employeeService;
    @Override
    public Page<Document> listDocumentByFolderPage(int pageSize, int pageNo,String documentName, String documentFolderId, String documentCategoryId) {
        return documentRepository.findByDocumentFolderAndDocumentCategoryAndDocumentName(documentName,documentFolderId,documentCategoryId,PageRequest.of(pageNo,pageSize,Sort.Direction.ASC,"create_date"));
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

    @Override
    public Document uploadFile(MultipartFile multipartFile){
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
        Document document=new Document();
        document.setDocumentName(fileName);
        document.setDocumentUrl(targetFilePath);
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
        return document;
    }

    @Transactional
    @Override
    public Document saveOrUpdate(Document document){
        if(document.getId()==null){
            document.setUploader(employeeService.getCurrentUser());
            document.setCreateDate(new Date());
        }
       return documentRepository.saveAndFlush(document);
    }

    @Override
    public Document findDocuById(Long id){
        return documentRepository.findById(id).get();
    }

    @Override
    public void downloadDoc(String fileName, HttpServletResponse response){
        String orginFilePath = srcPath + '/' + fileName;
        File file = new File(orginFilePath);
        if (file.exists()) {
            InputStream inStream = null;
            BufferedOutputStream os = null;
            try {
                inStream = new FileInputStream(file);
                // 设置输出的格式，以附件的方式输出，不用用浏览器打开
                byte[] buffer = new byte[1024];
                int byteread;
                try {
                    response.reset();
                    response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
                    response.setContentType("application/octet-stream");
                    os = new BufferedOutputStream(response.getOutputStream());
                    while ((byteread = inStream.read(buffer)) != -1) {
                        os.write(buffer, 0, byteread);
                    }
                    inStream.close();
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inStream != null) {
                        inStream.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            response.reset();
            try {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("文件不存在", "UTF-8"));
                response.setContentType("application/octet-stream");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
