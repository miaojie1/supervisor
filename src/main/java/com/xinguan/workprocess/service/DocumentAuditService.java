package com.xinguan.workprocess.service;

import com.xinguan.usermanage.model.Employee;
import com.xinguan.utils.PageInfo;
import com.xinguan.workprocess.model.DocumentAudit;

import java.util.List;

public interface DocumentAuditService {
    /**
     * 根据部门以及文件名称获取列表
     * @param pageNo
     * @param pageSize
     * @param name
     * @return
     */
    PageInfo<DocumentAudit> listDocumentAuditByDocName(int pageNo, int pageSize, String name);

    DocumentAudit findById(long id);

    DocumentAudit saveDocumentAudit(DocumentAudit documentAudit);

    void deleteDocAuditById(long id);

    DocumentAudit allotAuditTask(long id, List<Employee> employees);
}
