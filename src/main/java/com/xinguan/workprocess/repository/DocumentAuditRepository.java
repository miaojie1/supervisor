package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.DocumentAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DocumentAuditRepository extends JpaRepository<DocumentAudit, Long>, JpaSpecificationExecutor<DocumentAudit> {
}
