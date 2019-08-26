package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.ProjectSupervisionDepartment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectSupervisionDepartmentService {
    Page<ProjectSupervisionDepartment> listProjectSupervisionDbByPage(int pageSize, int pageNo, String projectName);
    ProjectSupervisionDepartment saveOrUpdate(ProjectSupervisionDepartment projectSupervisionDepartment);
    void removeProjectSupervisionDepartment(Long id);
    void removeProjectSupervisionDepartmentBatch(String ids);
    List<ProjectSupervisionDepartment> listAllProSuperDeparts();
}
