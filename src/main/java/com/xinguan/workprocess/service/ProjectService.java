package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.Project;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {

    Page<Project> listProjectByPage(int pageSize, int pageNo, String projectName);
    List<Project> listAllProjects();
    Project saveOrUpdate(Project project);
    void removeProject(Long id);
    void removeProjectBatch(String ids);
}
