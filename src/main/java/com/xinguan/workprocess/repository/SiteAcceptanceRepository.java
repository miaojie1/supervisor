package com.xinguan.workprocess.repository;

import com.xinguan.usermanage.model.Department;
import com.xinguan.workprocess.model.SiteAcceptance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SiteAcceptanceRepository extends JpaRepository<SiteAcceptance, Long>, JpaSpecificationExecutor<SiteAcceptance> {
    List<SiteAcceptance> findAllByDepartment(Department department);
}
