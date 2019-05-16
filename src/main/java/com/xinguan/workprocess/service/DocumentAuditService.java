package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.DocumentAudit;
import com.xinguan.workresult.model.DocumentCategory;
import com.xinguan.workresult.model.DocumentFolder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentAuditService {
    /**
     * 根据部门以及文件名称获取列表
     * @param pageNo
     * @param pageSize
     * @param name
     * @return
     */
    Page<DocumentAudit> listDocumentAuditByDocName(int pageNo, int pageSize, String name);

    DocumentAudit findById(long id);

    DocumentAudit saveDocumentAudit(DocumentAudit documentAudit);

    void deleteDocAuditById(long id);
}
