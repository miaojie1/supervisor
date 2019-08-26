package com.xinguan.workprocess.service.impl;

import com.xinguan.core.service.BaseService;
import com.xinguan.workprocess.model.ProjectStatus;
import com.xinguan.workprocess.service.ProjectStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectStatusServiceImpl extends BaseService<ProjectStatus> implements ProjectStatusService {
    @Override
    public List<ProjectStatus> listAllProjectStatus() {
        return projectStatusRepository.findAll();
    }
}
