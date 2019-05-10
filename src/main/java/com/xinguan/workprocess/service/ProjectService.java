package com.xinguan.workprocess.service;

import com.xinguan.workprocess.model.Project;
import org.springframework.data.domain.Page;

public interface ProjectService {

    Page<Project> listProjectByPage(int pageSize, int pageNo, String projectName);
}
