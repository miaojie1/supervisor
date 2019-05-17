package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.usermanage.model.Department;
import com.xinguan.usermanage.model.Employee;
import com.xinguan.usermanage.service.EmployeeService;
import com.xinguan.workprocess.model.DocumentAudit;
import com.xinguan.workprocess.service.DocumentAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DocumentAuditServiceImpl extends BaseService<DocumentAudit> implements DocumentAuditService {

    @Autowired
    EmployeeService employeeService;

    @Override
    public Page<DocumentAudit> listDocumentAuditByDocName(int pageNo, int pageSize, String name){

        Employee currentUser=employeeService.getCurrentUser();
        Department currentdepart=currentUser.getDepartment();
        DocumentAudit documentAudit=new DocumentAudit();
        if (name != "") {
            documentAudit.setTitle("%" + name + "%");
        }
        documentAudit.setDepartment(currentdepart);
        documentAudit.setOriginRank(null);
        Example<DocumentAudit> documentAuditExample = getSimpleExample(documentAudit);
        return documentAuditRepository.findAll(documentAuditExample, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public DocumentAudit findById(long id){
        return documentAuditRepository.findById(id).get();
    }

    @Transactional
    @Override
    public DocumentAudit saveDocumentAudit(DocumentAudit documentAudit){
        return documentAuditRepository.saveAndFlush(documentAudit);
    }

    @Override
    public void deleteDocAuditById(long id){
        documentAuditRepository.deleteById(id);
    }

}
