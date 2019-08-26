package com.xinguan.workprocess.repository;

import com.xinguan.workprocess.model.ProjectSupervisionDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectSupervisionDepartmentRepository extends JpaRepository<ProjectSupervisionDepartment, Long>, JpaSpecificationExecutor<ProjectSupervisionDepartment> {
}
