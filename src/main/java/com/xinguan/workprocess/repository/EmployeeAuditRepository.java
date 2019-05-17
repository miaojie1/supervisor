package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.EmployeeAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeAuditRepository extends JpaRepository<EmployeeAudit, Long>, JpaSpecificationExecutor<EmployeeAudit> {

}
